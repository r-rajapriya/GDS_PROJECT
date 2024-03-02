package com.gds.session;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Date;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;

public class PickerSessionDTOTest {
	
	private static final Logger log = LogManager.getLogger(PickerSessionDTOTest.class);
	
	@Test 
	public void testEmptyConstructor() {
		log.info("\nTEST testEmptyConstructor");
		PickerSessionDTO pkSession = new PickerSessionDTO();
		assertNotNull(pkSession);
	}
	
	@Test 
	public void testPrimaryConstructor() {
		log.info("\nTEST testPrimaryConstructor");
		PickerSessionDTO pkSession = new PickerSessionDTO(101, "Team lunch for Go Live", "Open", new Date(), "JOHN", new Date(), "McD");
		assertNotNull(pkSession);
	}
	
	@Test 
	public void testSetters() {
		log.info("\nTEST testSetters");
		PickerSessionDTO pkSession = new PickerSessionDTO();
		pkSession.setSessionId(101);
		pkSession.setSessionName("Team lunch for Go Live");
		pkSession.setEventDate(new Date());
		pkSession.setSessionStatus("Open");
		pkSession.setCreatedBy("JOHN");
		pkSession.setUserJoinDate(new Date());
		pkSession.setSelectedRestaurant("Sub Way");
		
		assertNotNull(pkSession);
		assertEquals(101, pkSession.getSessionId());
		assertEquals("JOHN", pkSession.getCreatedBy());
	}
	
	@Test 
	public void testGetters() {
		log.info("\nTEST testGetters");
		PickerSessionDTO pkSession = new PickerSessionDTO(101, "Team lunch for Go Live", "Open", new Date(), "JOHN", new Date(), "McD");
		pkSession.setUserJoinDate(new Date());
		pkSession.setSelectedRestaurant("Sub Way");
				
		log.info("session id = "+pkSession.getSessionId());
		log.info("session name = "+pkSession.getSessionName());
		log.info("event on = "+pkSession.getEventDate());
		log.info("session status = "+pkSession.getSessionStatus());
		log.info("created by = "+pkSession.getCreatedBy());
		log.info("user join date = "+pkSession.getUserJoinDate());
		log.info("Selected Restaurant = "+pkSession.getSelectedRestaurant());
	
		assertNotNull(pkSession);
		assertEquals(101, pkSession.getSessionId());
		assertEquals("Open", pkSession.getSessionStatus());
		assertEquals("JOHN", pkSession.getCreatedBy());
	}	
}
