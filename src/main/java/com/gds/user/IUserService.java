package com.gds.user;

import java.util.List;

public interface IUserService {
	User findByUserId(String userId, String password);
	List<User> findAllUsers(); 
}
