package com.gds.user;

import java.util.List;

/**
 * Service interface for User related functions
 * 
 * @author Rajapriya
 *
 */
public interface IUserService {
	User validateUserLogin(User user) throws Exception;
	List<User> findAllUsers(); 
}
