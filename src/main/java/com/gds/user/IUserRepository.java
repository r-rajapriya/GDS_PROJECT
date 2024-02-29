package com.gds.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface IUserRepository extends JpaRepository<User, Integer> {
	@Query(value = "SELECT * FROM user_master WHERE user_id = ?1 and password = ?2", nativeQuery = true)
	User findByUserId(String userId, String password);
}
