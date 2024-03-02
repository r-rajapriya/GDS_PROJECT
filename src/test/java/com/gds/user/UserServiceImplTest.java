package com.gds.user;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import com.gds.util.EncryptionUtil;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {
	
	private static final Logger log = LogManager.getLogger(UserServiceImplTest.class);

	@InjectMocks 
	UserServiceImpl userServImpl;
	
	@Mock
	IUserRepository userRepo;
	
	User user = null;
	
	@BeforeEach
	  public void init() {
	    MockitoAnnotations.openMocks(this);	    
	    user = new User("JOHN", "JOHN MATHEW", "pwd");	
	  }
	
	@AfterEach
    void teardown() {        
		user = null;
    }
	
	@Test
	public void TestValidateUserLoginSuccess()
	{
		log.info("\nTEST TestValidateUserLoginSuccess");

		when(userRepo.findByUserId("JOHN", "pwd")).thenReturn(user);
		
		try {
			user = userServImpl.validateUserLogin(user);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		assertEquals("JOHN", user.getUserId());
	    verify(userRepo, times(1)).findByUserId("JOHN", EncryptionUtil.hashPassword("pwd"));
	}
	
	@Test
	public void TestValidateUserLoginInvalid()
	{
		log.info("\nTEST TestValidateUserLoginInvalid");
		
		user.setUserId(null);
		user.setPassword("");
		
		try {
			userServImpl.validateUserLogin(user);
		} catch (Exception e) {
			assertEquals("Invalid User Id / Password", e.getMessage());
		}
	}
		
	@Test
	public void TestValidateUserLoginFailure()
	{
		log.info("\nTEST TestValidateUserLoginFailure");

		when(userRepo.findByUserId(any(String.class), any(String.class))).thenReturn(null);
		
		try {
			userServImpl.validateUserLogin(user);
		} catch (Exception e) {
			assertEquals("Invalid Login credentials", e.getMessage());
		}
		
	    verify(userRepo, times(1)).findByUserId(any(String.class), any(String.class));
	}
	
	@Test
	public void TestFindAllUsers()
	{
		log.info("\nTEST TestFindAllUsers");

		List<User> userList = new ArrayList<User>();
		userList.add(new User("JOHN", "JOHN MATHEW", "test")); 
		userList.add(new User("MARY", "MARY DIANA", "test")); 
		userList.add(new User("ALEX", "ALEX PETERSON", "test")); 		
		when(userRepo.findAll()).thenReturn(userList);

		List<User> uList = userServImpl.findAllUsers();
		
		assertEquals(3, uList.size());
	    verify(userRepo, times(1)).findAll();
	}
	
}
