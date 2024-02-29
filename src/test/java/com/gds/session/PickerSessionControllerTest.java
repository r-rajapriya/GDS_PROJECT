package com.gds.session;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@WebMvcTest(PickerSessionController.class)
public class PickerSessionControllerTest 
{
	
	private static final Logger log = LogManager.getLogger(PickerSessionControllerTest.class);
	
	//@Autowired
	//private MockMvc mvc;
	
	@LocalServerPort
	private int port;

	@Autowired
	private TestRestTemplate restTemplate;
	
	@Test
	void testWelcomePage() throws Exception {
		log.info("\nTEST testWelcomePage");
		assertEquals("login", this.restTemplate.getForObject("http://localhost:" + port + "/gds/welcome",
				String.class));
	}
	
	/*@Test
	public void testGetAllUserSessions() throws Exception {

	  mvc.perform(MockMvcRequestBuilders
	  			.get("/session/listSessions")
	  			.accept(MediaType.APPLICATION_JSON))
	      .andDo(print())
	      .andExpect(status().isOk())
	      .andExpect(MockMvcResultMatchers.jsonPath("$.employees").exists())
	      .andExpect(MockMvcResultMatchers.jsonPath("$.employees[*].employeeId").isNotEmpty());
	}*/
}
