package com.kakaopay.bank.bank.web;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.kakaopay.bank.bank.controller.BankController;
import com.kakaopay.bank.bank.entity.Bank;
import com.kakaopay.bank.bank.service.BankService;

@RunWith(SpringJUnit4ClassRunner.class)
public class BankControllerTest {
	
	//@InjectMocks
	private BankController bankController;
	
	@Mock
	BankService bankService;
	
	private MockMvc mockMvc;
	
	private List<Bank> params;
	
	@Before
	public void setUp() throws Exception {
	   //ankController = new BankController(bankService);
       params = new ArrayList<Bank>();

       MockitoAnnotations.initMocks(this);
	   mockMvc = MockMvcBuilders.standaloneSetup(bankController).build();
	}
	
	@Test
	public void testFindBankList() throws Exception{
		when(bankService.findBankList()).thenReturn(params);
		
		mockMvc.perform(get("/findBankList")).andExpect(status().isOk()); // 상태 검증 (성공:200)
									         //.andExpect(model().attributeExists()));
		
		//assertThat(bankService.findBankList()).as("HTTP Status Code").isEqualTo(200);						         
		
		verify(bankService).findBankList();
        verifyNoMoreInteractions(bankService);
        verify(bankService, times(0)).findBankNameList(); 
        
        //assertEquals("?!", expected, actual);
	}
	
	@Test
	public void testSaveBank() throws Exception{
		
		mockMvc.perform(post("/saveBank")).andExpect(status().isOk()); // 상태 검증 (성공:200)

		verify(bankService).saveBank();
        verifyNoMoreInteractions(bankService);
        verify(bankService, times(0)).findBankNameList(); 

        //fail("Not yet implemented");
	}
	
	@Test
	public void testFindBankNameList() throws Exception{
		when(bankService.findBankNameList()).thenReturn(params);
		
		mockMvc.perform(get("/findBankNameList")).andExpect(status().isOk()); // 상태 검증 (성공:200)

		verify(bankService).findBankNameList();
        verifyNoMoreInteractions(bankService);
        verify(bankService, times(1)).findBankNameList(); 
	}
	
}
