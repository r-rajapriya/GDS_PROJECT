package com.gds.user;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gds.util.EncryptionUtil;

/**
 * Service class for User related functions
 * 
 * @author Rajapriya
 *
 */
@Service
public class UserServiceImpl implements IUserService {
	
	private static final Logger log = LogManager.getLogger(UserServiceImpl.class);
	
	@Autowired 
	private IUserRepository userRepository;	
	
	/**
	 * Method to validate the login user credentials 
	 */
	@Override  
	public User validateUserLogin(User user) throws Exception  
	{ 
		log.info("inside UserServiceImpl.validateUserLogin method");		
		User loginUser = null;
		
		String usrId = user.getUserId();
		String pwd = user.getPassword();		

		if (usrId == null || pwd == null || usrId.length() == 0 || pwd.length() == 0) {
			throw new Exception("Invalid User Id / Password");
		}
		
		//Hashing the user entered password
		String hashedPwd = EncryptionUtil.hashPassword(pwd);
		if(hashedPwd.length() == 0)
			throw new Exception("Error while hashing the password");
	    
	    log.info("Retrieving user details for : " + usrId);		    
		loginUser = userRepository.findByUserId(usrId.toUpperCase(), hashedPwd); //User id is stored in Upper case in DB
		if (loginUser != null) {
			log.info("User login is SUCCESS for " + loginUser);				
			return loginUser;
		}
		else {
			log.error("User login failed for " + usrId);
			throw new Exception("Invalid Login credentials");			
		}		
	}	
	
	/**
	 * Method to fetch all the users in the application
	 */
	@Override  
	public List<User> findAllUsers()
	{
		log.info("inside UserServiceImpl.findAllUsers method");
		List<User> listUsers = userRepository.findAll();
	    return listUsers;
	}
}
