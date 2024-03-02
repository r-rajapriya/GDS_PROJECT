package com.gds.choice;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Date;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class ChoiceServiceImplTest {
	
	private static final Logger log = LogManager.getLogger(ChoiceServiceImplTest.class);

	@InjectMocks 
	ChoiceServiceImpl choiceServImpl;
	
	@Mock
	IChoiceRepository choiceRepo;
	
	Choice choice = null;
	
	@BeforeEach
	  public void init() {
	    MockitoAnnotations.openMocks(this);	    
	    choice = new Choice("101-JOHN", "JOHN", 101, new Date());	
	  }
	
	@AfterEach
    void teardown() {        
		choice = null;
    }
	
	@Test
	public void testSubmitChoice()
	{
		log.info("\nTEST testSubmitChoice");
		
		choice.setRestaurantChoice("Subway");
		when(choiceRepo.findByChoiceId("101-JOHN")).thenReturn(choice);
		
		when(choiceRepo.save(any(Choice.class))).thenReturn(choice);
		
		Choice ch = choiceServImpl.submitChoice("JOHN", 101, "Subway");
		
		assertEquals("Subway", ch.getRestaurantChoice());
	    verify(choiceRepo, times(1)).findByChoiceId("101-JOHN");
	    verify(choiceRepo, times(1)).save(any(Choice.class));
	}
	
	@Test
	public void testSubmitChoiceJoinFlow()
	{
		log.info("\nTEST testSubmitChoiceJoinFlow");
		
		when(choiceRepo.findByChoiceId("101-JOHN")).thenReturn(choice);
		
		when(choiceRepo.save(any(Choice.class))).thenReturn(choice);
		
		Choice ch = choiceServImpl.submitChoice("JOHN", 101, null);
		
		assertEquals(ch, choice);
		assertNull(ch.getRestaurantChoice());
	    verify(choiceRepo, times(1)).findByChoiceId("101-JOHN");
	    verify(choiceRepo, times(1)).save(any(Choice.class));
	}
	
}
