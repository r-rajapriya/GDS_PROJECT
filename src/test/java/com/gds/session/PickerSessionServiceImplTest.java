package com.gds.session;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
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

import com.gds.choice.Choice;
import com.gds.choice.IChoiceRepository;
import com.gds.user.IUserService;
import com.gds.user.User;

@ExtendWith(MockitoExtension.class)
public class PickerSessionServiceImplTest { 
	
	private static final Logger log = LogManager.getLogger(PickerSessionServiceImplTest.class);

	@InjectMocks 
	PickerSessionServiceImpl sessServImpl;
	
	@Mock
	IPickerSessionRepository sessRepo;
	
	@Mock
	IChoiceRepository choiceRepo;
	
	@Mock
	IUserService userServ;
	
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
		pkSessionDto = null;
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
	public void testCreatePickerSessionForInitiatorOnly()
	{
		log.info("\nTEST testCreatePickerSessionForInitiatorOnly");
		
		pkSession.setInviteAll("");
		when(sessRepo.save(pkSession)).thenReturn(pkSession);

		Choice choice = new Choice("101-JOHN", "JOHN", 101, new Date());		
		when(choiceRepo.save(any(Choice.class))).thenReturn(choice);
		
		PickerSession pkSess = sessServImpl.createPickerSession(pkSession, "JOHN");
		
		assertEquals(pkSess, pkSession);
	    verify(sessRepo, times(1)).save(pkSession);
	    verify(choiceRepo, times(1)).save(any(Choice.class));
	}
	
	@Test
	public void testCreatePickerSessionInviteAll()
	{
		log.info("\nTEST testCreatePickerSessionInviteAll");
		
		pkSession.setInviteAll("invited");
		when(sessRepo.save(pkSession)).thenReturn(pkSession);

		List<User> userList = new ArrayList<User>();
		userList.add(new User("JOHN", "JOHN MATHEW", "test")); 
		userList.add(new User("MARY", "MARY DIANA", "test")); 
		userList.add(new User("ALEX", "ALEX PETERSON", "test")); 		
		when(userServ.findAllUsers()).thenReturn(userList);

		List<Choice> chList = new ArrayList<Choice>();
		chList.add(new Choice("101-JOHN", "JOHN", 101, new Date())); 
		chList.add(new Choice("101-MARY", "MARY", 101, new Date())); 
		chList.add(new Choice("101-ALEX", "ALEX", 101, new Date()));		
		when(choiceRepo.saveAll(anyList())).thenReturn(chList);
		
		Optional<PickerSession> pkOpt = Optional.of(pkSession);
		when(sessRepo.findById(Long.valueOf(101))).thenReturn(pkOpt);
		
		PickerSession pkSess = sessServImpl.createPickerSession(pkSession, "JOHN");
		
		assertEquals(pkSess, pkSession);
	    verify(sessRepo, times(2)).save(pkSession);
	    verify(choiceRepo, times(1)).saveAll(anyList());
	    verify(userServ, times(1)).findAllUsers();
	}
	
	@Test
	public void testInviteUsersWithInitiator()
	{
		log.info("\nTEST testInviteUsersWithInitiator");

		List<User> userList = new ArrayList<User>();
		userList.add(new User("JOHN", "JOHN MATHEW", "test")); 
		userList.add(new User("MARY", "MARY DIANA", "test")); 
		userList.add(new User("ALEX", "ALEX PETERSON", "test")); 		
		when(userServ.findAllUsers()).thenReturn(userList);

		List<Choice> chList = new ArrayList<Choice>();
		chList.add(new Choice("101-JOHN", "JOHN", 101, new Date())); 
		chList.add(new Choice("101-MARY", "MARY", 101, new Date())); 
		chList.add(new Choice("101-ALEX", "ALEX", 101, new Date()));		
		when(choiceRepo.saveAll(anyList())).thenReturn(chList);
		
		Optional<PickerSession> pkOpt = Optional.of(pkSession);
		when(sessRepo.findById(Long.valueOf(101))).thenReturn(pkOpt);
				
		//pkSession.setInviteAll("invited");
		when(sessRepo.save(pkSession)).thenReturn(pkSession);
		
		List<Choice> resultList = sessServImpl.inviteUsers(101, "JOHN", true);
		
		assertEquals(chList, resultList);
		assertEquals(3, chList.size());
	    verify(sessRepo, times(1)).save(pkSession);
	    verify(choiceRepo, times(1)).saveAll(anyList());
	    verify(userServ, times(1)).findAllUsers();
	}
	
	@Test
	public void testInviteUsersWithoutInitiator()
	{
		log.info("\nTEST testInviteUsersWithoutInitiator");

		List<User> userList = new ArrayList<User>();
		userList.add(new User("JOHN", "JOHN MATHEW", "test")); 
		userList.add(new User("MARY", "MARY DIANA", "test")); 
		userList.add(new User("ALEX", "ALEX PETERSON", "test")); 		
		when(userServ.findAllUsers()).thenReturn(userList);

		List<Choice> chList = new ArrayList<Choice>();
		chList.add(new Choice("101-MARY", "MARY", 101, new Date())); 
		chList.add(new Choice("101-ALEX", "ALEX", 101, new Date()));		
		when(choiceRepo.saveAll(anyList())).thenReturn(chList);
		
		Optional<PickerSession> pkOpt = Optional.of(pkSession);
		when(sessRepo.findById(Long.valueOf(101))).thenReturn(pkOpt);
				
		//pkSession.setInviteAll("invited");
		when(sessRepo.save(pkSession)).thenReturn(pkSession);
		
		List<Choice> resultList = sessServImpl.inviteUsers(101, "JOHN", false);
		
		assertEquals(chList, resultList);
		assertEquals(2, chList.size());
	    verify(sessRepo, times(1)).save(pkSession);
	    verify(choiceRepo, times(1)).saveAll(anyList());
	    verify(userServ, times(1)).findAllUsers();
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
	
	@Test
	public void testEndPickerSession()
	{
		log.info("\nTEST testEndPickerSession");
		
		List<Choice> chList = new ArrayList<Choice>();
		Choice ch = new Choice("101-JOHN", "JOHN", 101, new Date()); ch.setRestaurantChoice("McD");
		chList.add(ch); 
		ch = new Choice("101-MARY", "MARY", 101, new Date()); ch.setRestaurantChoice("KFC");
		chList.add(ch); 
		ch = new Choice("101-ROSY", "ROSY", 101, new Date()); ch.setRestaurantChoice(null);
		chList.add(ch);	
		ch = new Choice("101-ALEX", "ALEX", 101, new Date()); ch.setRestaurantChoice("PizzaHut");
		chList.add(ch);	
		pkSession.setUserChoices(chList);
		
		Optional<PickerSession> pkOpt = Optional.of(pkSession);
		when(sessRepo.findById(Long.valueOf(101))).thenReturn(pkOpt);

		when(sessRepo.save(pkSession)).thenReturn(pkSession);
		
		PickerSession pkSess = new PickerSession();
		try {
			pkSess = sessServImpl.endPickerSession(101);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		assertEquals(pkSess, pkSession);
	    verify(sessRepo, times(1)).findById(Long.valueOf(101));
	    verify(sessRepo, times(1)).save(pkSession);
	}
}
