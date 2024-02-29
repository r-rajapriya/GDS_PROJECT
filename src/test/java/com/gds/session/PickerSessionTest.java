package com.gds.session;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.gds.choice.Choice;

public class PickerSessionTest {
	
	@Test 
	public void testEmptyConstructor() {
		System.out.println("\nTEST testEmptyConstructor");
		PickerSession pkSession = new PickerSession();
		assertNotNull(pkSession);
	}
	
	@Test 
	public void testPrimaryConstructor() {
		System.out.println("\nTEST testPrimaryConstructor");
		PickerSession pkSession = new PickerSession(101, "Team lunch for Go Live", new Date(), "Open", "JOHN", new Date());
		assertNotNull(pkSession);
	}
	
	@Test 
	public void testSetters() {
		System.out.println("\nTEST testSetters");
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
		System.out.println("\nTEST testGetters");
		PickerSession pkSession = new PickerSession(101, "Team lunch for Go Live", new Date(), "Open", "JOHN", new Date());
		pkSession.setEndDate(new Date());
		pkSession.setSelectedRestaurant("Sub Way");
		pkSession.setInviteAll("invited");
		List<Choice> choiceList = new ArrayList<Choice>();
		choiceList.add(new Choice());
		pkSession.setUserChoices(choiceList);
		
		System.out.println("session id = "+pkSession.getSessionId());
		System.out.println("session name = "+pkSession.getSessionName());
		System.out.println("event on = "+pkSession.getEventDate());
		System.out.println("session status = "+pkSession.getSessionStatus());
		System.out.println("created by = "+pkSession.getCreatedBy());
		System.out.println("start date = "+pkSession.getStartDate());
		System.out.println("end date = "+pkSession.getEndDate());
		System.out.println("Selected Restaurant = "+pkSession.getSelectedRestaurant());
		System.out.println("invite all = "+pkSession.getInviteAll());
		System.out.println("choice list = "+pkSession.getUserChoices());

		assertNotNull(pkSession);
		assertEquals(101, pkSession.getSessionId());
		assertEquals("Open", pkSession.getSessionStatus());
		assertEquals("JOHN", pkSession.getCreatedBy());
		assertTrue((pkSession.getUserChoices().get(0)) instanceof Choice);
	}
	
	@Test 
	public void testToString() {
		System.out.println("\nTEST testToString");
		PickerSession pkSession = new PickerSession(101, "Team lunch for Go Live", new Date(), "Open", "JOHN", new Date());
		System.out.println("PickerSession = "+pkSession);
		assertNotNull(pkSession);
		assertEquals(101, pkSession.getSessionId());
		assertEquals("Open", pkSession.getSessionStatus());
		assertEquals("JOHN", pkSession.getCreatedBy());
	}
}
