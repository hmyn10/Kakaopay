package com.kakaopay.bank.bank.service;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

import java.util.*;

import javax.persistence.EntityManager;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;

import com.kakaopay.bank.bank.entity.Bank;
import com.kakaopay.bank.bank.repository.BankJpaRepo;
import com.kakaopay.bank.bank.service.BankService;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
//@DataJpaTest
public class BankServiceTest {
	
	@InjectMocks
    BankService bankService;

    @Mock
    BankJpaRepo bankJpaRepo;
    
    private EntityManager entityManager;
	@Before
    public void setUp() throws Exception {
		
		mockingRepository();
		System.out.println("테스트 시작");
    }
	@Test
    public void testFindBankNameList(){
        List<Bank> bankList = new ArrayList<>();
      
        //given
        bankList.add(Bank.builder().instituteName("카카오페이").build());
        bankList.add(Bank.builder().instituteName("우리은행").build());
        bankList.add(Bank.builder().instituteName("테스트은행").build());

        Map<String,Object> expectedMap = new HashMap<>();
        expectedMap.put("bank",bankList);
        
        //when
        List<Bank> resultMap = findBankNameList();
        System.out.println("테스트 3 수행");
        
        //then
        assertNotNull(resultMap);
        
        
        //assertThat(bank.getInstituteCode()).isEqualTo(4);
        //assertTrue(bankService.saveBank());
        //assertThat(resultMap,is(notNullValue()));
        //assertThat(resultMap,is(expectedMap));
    }
	private void mockingRepository() {
		
        List<Bank> banks = new ArrayList<>();
        banks.add(Bank.builder().instituteCode(1).instituteName("카카오페이").build());
        banks.add(Bank.builder().instituteCode(2).instituteName("신한은행").build());
        
        when(bankJpaRepo.findAll()).thenReturn(banks);
		
		

    }
	
	/*@Test
    public void testSaveBank() throws Exception {
		
        //em.flush();
        assertTrue(bankService.saveBank());
    }*/


	/*@Test
	public void testzFindBankNameList() throws Exception {
		//assertNotNull(bankService.findBankNameList());
		//bankService.findBankNameList();
		assertNotNull(bank);
		System.out.println("테스트 2 수행");
	}*/
	
	public List<Bank> findBankNameList() {
		
		String query = "select b.instituteName from Bank b";
		
		List<Bank> q = (List<Bank>) entityManager.createQuery(query).getResultList();
		System.out.println("q :" + q);
		return q;
	}
	
	
	
	
}
