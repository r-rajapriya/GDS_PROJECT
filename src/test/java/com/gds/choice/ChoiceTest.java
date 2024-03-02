package com.gds.choice;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Date;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;

public class ChoiceTest {
	
	private static final Logger log = LogManager.getLogger(ChoiceTest.class);
	
	@Test 
	public void testEmptyConstructor() {
		log.info("\nTEST testEmptyConstructor");
		Choice choice = new Choice();
		assertNotNull(choice);
	}
	
	@Test 
	public void testPrimaryConstructor() {
		log.info("\nTEST testPrimaryConstructor");
		Choice choice = new Choice("101-JOHN", "JOHN", 101, new Date());
		assertNotNull(choice);
	}
	
	@Test 
	public void testSetters() {
		log.info("\nTEST testSetters");
		Choice choice = new Choice();
		choice.setChoiceId("101-JOHN");
		choice.setUserId("JOHN");
		choice.setSessionId(101);
		choice.setJoinDate(new Date());
		choice.setSubmitDate(new Date());
		choice.setRestaurantChoice("McD");

		assertNotNull(choice);
		assertEquals(101, choice.getSessionId());
		assertEquals("JOHN", choice.getUserId());
	}
	
	@Test 
	public void testGetters() {
		log.info("\nTEST testGetters");
		Choice choice = new Choice("101-JOHN", "JOHN", 101, new Date());
		choice.setSubmitDate(new Date());
		choice.setRestaurantChoice("McD");

		log.info("choice id = "+choice.getChoiceId());
		log.info("session id = "+choice.getSessionId());
		log.info("user id = "+choice.getUserId());
		log.info("join date = "+choice.getJoinDate());
		log.info("submit on = "+choice.getSubmitDate());
		log.info("Restaurant name = "+choice.getRestaurantChoice());
		
		assertNotNull(choice);
		assertEquals(101, choice.getSessionId());
		assertEquals("JOHN", choice.getUserId());
	}
	
	@Test 
	public void testToString() {
		log.info("\nTEST testToString");
		Choice choice = new Choice("101-JOHN", "JOHN", 101, new Date());
		choice.setSubmitDate(new Date());
		choice.setRestaurantChoice("McD");
		log.info("Choice = "+choice);
		assertNotNull(choice);
		assertEquals(101, choice.getSessionId());
	}
}
