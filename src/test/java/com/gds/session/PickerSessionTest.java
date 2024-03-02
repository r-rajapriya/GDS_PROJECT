package com.gds.session;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;

import com.gds.choice.Choice;

public class PickerSessionTest {
	
	private static final Logger log = LogManager.getLogger(PickerSessionTest.class);
	
	@Test 
	public void testEmptyConstructor() {
		log.info("\nTEST testEmptyConstructor");
		PickerSession pkSession = new PickerSession();
		assertNotNull(pkSession);
	}
	
	@Test 
	public void testPrimaryConstructor() {
		log.info("\nTEST testPrimaryConstructor");
		PickerSession pkSession = new PickerSession(101, "Team lunch for Go Live", new Date(), "Open", "JOHN", new Date());
		assertNotNull(pkSession);
	}
	
	@Test 
	public void testSetters() {
		log.info("\nTEST testSetters");
		PickerSession pkSession = new PickerSession();
		pkSession.setSessionId(101);
		pkSession.setSessionName("Team lunch for Go Live");
		pkSession.setEventDate(new Date());
		pkSession.setSessionStatus("Open");
		pkSession.setCreatedBy("JOHN");
		pkSession.setStartDate(new Date());
		pkSession.setEndDate(new Date());
		pkSession.setSelectedRestaurant("Sub Way");
		pkSession.setInviteAll("invited");
		List<Choice> choiceList = new ArrayList<Choice>();
		choiceList.add(new Choice());choiceList.add(new Choice());choiceList.add(new Choice());
		pkSession.setUserChoices(choiceList);
		assertNotNull(pkSession);
		assertEquals(101, pkSession.getSessionId());
		assertEquals("JOHN", pkSession.getCreatedBy());
		assertEquals(3, pkSession.getUserChoices().size());
	}
	
	@Test 
	public void testGetters() {
		log.info("\nTEST testGetters");
		PickerSession pkSession = new PickerSession(101, "Team lunch for Go Live", new Date(), "Open", "JOHN", new Date());
		pkSession.setEndDate(new Date());
		pkSession.setSelectedRestaurant("Sub Way");
		pkSession.setInviteAll("invited");
		List<Choice> choiceList = new ArrayList<Choice>();
		choiceList.add(new Choice());
		pkSession.setUserChoices(choiceList);
		
		log.info("session id = "+pkSession.getSessionId());
		log.info("session name = "+pkSession.getSessionName());
		log.info("event on = "+pkSession.getEventDate());
		log.info("session status = "+pkSession.getSessionStatus());
		log.info("created by = "+pkSession.getCreatedBy());
		log.info("start date = "+pkSession.getStartDate());
		log.info("end date = "+pkSession.getEndDate());
		log.info("Selected Restaurant = "+pkSession.getSelectedRestaurant());
		log.info("invite all = "+pkSession.getInviteAll());
		log.info("choice list = "+pkSession.getUserChoices());

		assertNotNull(pkSession);
		assertEquals(101, pkSession.getSessionId());
		assertEquals("Open", pkSession.getSessionStatus());
		assertEquals("JOHN", pkSession.getCreatedBy());
		assertTrue((pkSession.getUserChoices().get(0)) instanceof Choice);
	}
	
	@Test 
	public void testToString() {
		log.info("\nTEST testToString");
		PickerSession pkSession = new PickerSession(101, "Team lunch for Go Live", new Date(), "Open", "JOHN", new Date());
		log.info("PickerSession = "+pkSession);
		assertNotNull(pkSession);
		assertEquals(101, pkSession.getSessionId());
		assertEquals("Open", pkSession.getSessionStatus());
		assertEquals("JOHN", pkSession.getCreatedBy());
	}
}
