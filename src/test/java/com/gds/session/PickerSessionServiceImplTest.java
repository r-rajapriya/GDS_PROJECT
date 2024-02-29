package com.gds.session;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

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
public class PickerSessionServiceImplTest {
	
	private static final Logger log = LogManager.getLogger(PickerSessionServiceImplTest.class);

	@InjectMocks 
	PickerSessionServiceImpl sessServImpl;
	
	@Mock
	IPickerSessionRepository sessRepo;
	
	PickerSession pkSession = null;
	PickerSessionDTO pkSessionDto = null;
	
	@BeforeEach
	  public void init() {
	    MockitoAnnotations.openMocks(this);
	    
		pkSession = new PickerSession(101, "Team lunch for Go Live success", new Date(), "Open", "JOHN", new Date());	
		pkSessionDto = new PickerSessionDTO(101, "Team lunch for Go Live success", "Open", new Date(), "JOHN", new Date(),"Sub way");		
	  }
	
	@AfterEach
    void teardown() {        
		pkSession = null;
    }
	
	@Test
	public void testFindAllUserSessions()
	{
		log.info("\nTEST testFindAllUserSessions");

		List<PickerSessionDTO> list = new ArrayList<PickerSessionDTO>();
		list.add(pkSessionDto); list.add(pkSessionDto); list.add(pkSessionDto);
		
		when(sessRepo.findDTOByUserId("JOHN")).thenReturn(list);
		
		List<PickerSessionDTO> pkList = sessServImpl.findAllUserSessions("JOHN");
		
		assertEquals(3, pkList.size());
	    verify(sessRepo, times(1)).findDTOByUserId("JOHN");
	}
	
	@Test
	public void testSavePickerSession()
	{
		log.info("\nTEST testSavePickerSession");
		
		when(sessRepo.save(pkSession)).thenReturn(pkSession);
		PickerSession pkSess = sessServImpl.savePickerSession(pkSession);
		
		assertEquals(pkSess, pkSession);
	    verify(sessRepo, times(1)).save(pkSession);
	}
	
	@Test
	public void findBySessionId()
	{
		log.info("\nTEST findBySessionId");		
		
		Optional<PickerSession> pkOpt = Optional.of(pkSession);
		when(sessRepo.findById(Long.valueOf(101))).thenReturn(pkOpt);
		
		PickerSession pkSess = sessServImpl.findBySessionId(Long.valueOf(101));
		
		assertEquals(pkSess, pkSession);
	    verify(sessRepo, times(1)).findById(Long.valueOf(101));
	}
}
