package com.gds;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.gds.choice.ChoiceController;
import com.gds.session.PickerSessionController;
import com.gds.user.UserController;

@SpringBootTest
class RestaurantPickerApplicationTest {
	
	@Autowired
	private PickerSessionController sessionController;	
	@Autowired
	private ChoiceController choiceController;
	@Autowired
	private UserController userController;
	
	@Test
	void contextLoads() throws Exception {
		assertNotNull(sessionController);
		assertNotNull(choiceController);
		assertNotNull(userController);
	}	
}
