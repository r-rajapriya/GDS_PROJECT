package com.gds.user;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;

public class UserTest {
	
	private static final Logger log = LogManager.getLogger(UserTest.class);
	
	@Test 
	public void testEmptyConstructor() {
		log.info("\nTEST testEmptyConstructor");
		User user = new User();
		assertNotNull(user);
	}
	
	@Test 
	public void testPrimaryConstructor() {
		log.info("\nTEST testPrimaryConstructor");
		User user = new User("JOHN", "JOHN MATHEW", "pwd");
		assertNotNull(user);
	}
	
	@Test 
	public void testSetters() {
		log.info("\nTEST testSetters");
		User user = new User();
		user.setUserId("JOHN");
		user.setUserName("JOHN MATHEW");
		user.setPassword("pwd");
		
		assertNotNull(user);
		assertEquals("JOHN", user.getUserId());
	}
	
	@Test 
	public void testGetters() {
		log.info("\nTEST testGetters");
		User user = new User("JOHN", "JOHN MATHEW", "pwd");
		
		log.info("user id = "+user.getUserId());
		log.info("user name = "+user.getUserName());
		log.info("password = "+user.getPassword());
				
		assertNotNull(user);
		assertEquals("JOHN", user.getUserId());
	}
	
	@Test 
	public void testToString() {
		log.info("\nTEST testToString");
		User user = new User("JOHN", "JOHN MATHEW", "pwd");		
		log.info("User = "+user);
		assertNotNull(user);
	}
}
