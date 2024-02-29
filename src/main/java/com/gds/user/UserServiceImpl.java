package com.gds.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gds.session.PickerSession;

@Service
public class UserServiceImpl implements IUserService {
	
	@Autowired 
	private IUserRepository userRepository;
	
	@Override  
	public User findByUserId(String userId, String password)  
	{ 
		System.out.println("inside findByUserId method");
		User user = userRepository.findByUserId(userId, password);
	    return user;
	}	
	
	@Override  
	public List<User> findAllUsers()
	{
		System.out.println("inside UserServiceImpl findAll method");
		List<User> listUsers = userRepository.findAll();
	    return listUsers;
	}
	
 }
