package com.kakaopay.bank.bank.service;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.kakaopay.bank.bank.entity.Bank;
import com.kakaopay.bank.bank.service.BankMoneyService;

@RunWith(SpringRunner.class)
@DataJpaTest
public class BankMoneyServiceTest {
	
	//test list
	//1. 가장 높은 지원금 은행명과 년도 조회는 결과값이 하나인지
    //2. 외환은행 지원금 최대, 최소값과 년도 결과값은 두개인지(최대값이 최소값보다 큰지 데이터 비교)
	
	private BankMoneyService bankMoneyService; 
	
	@Before
    public void setUp() throws Exception {
		
		mockingRepository();
		System.out.println("테스트 시작");
    }
	
	private void mockingRepository() {
	
		/*List<Bank> banks = new ArrayList<>();
        banks.add(Bank.builder().instituteCode(1).instituteName("카카오페이").build());
        banks.add(Bank.builder().instituteCode(2).instituteName("신한은행").build());
        when(bankJpaRepo.findAll()).thenReturn(banks);

        when(bankJpaRepo.findByInstituteCode(1)).thenReturn(Optional.of(
                Bank.builder().instituteCode(1).instituteName("카카오페이").build()));

        when(bankJpaRepo.findByInstituteName("카카오페이")).thenReturn(Optional.of(
                Bank.builder().instituteCode(1).instituteName("카카오페이").build()));

        List<Bank> bankFinances = new ArrayList<>();
        for (int i = 1; i < 13; i++) {
            bankFinances.add(BankFinance.builder().instituteCode(1L).money(2000 * i).year(2015).month(i).build());
            bankFinances.add(BankFinance.builder().instituteCode(2L).money(1500 * i).year(2015).month(i).build());
            bankFinances.add(BankFinance.builder().instituteCode(1L).money(1000 * i).year(2016).month(i).build());
            bankFinances.add(BankFinance.builder().instituteCode(2L).money(500 * i).year(2016).month(i).build());
        }
        when(bankFinanceRepository.findAll()).thenReturn(bankFinances);
        when(bankFinanceRepository.findAllByBankInstituteCode(1L)).thenReturn(
                bankFinances.stream()
                        .filter(bankFinance -> bankFinance.getBankId() == 1L)
                        .collect(Collectors.toList()));
        
		}*/
		
		

    }
	
	@Test
    public void testFindTotalAmount() {
		System.out.println("테스트 수행 testFindTotalAmount");
		/*em.flush();
        assertThat(bank.getInstituteCode()).isEqualTo(4);
        assertNotNull(bank);*/
		
		/*assertThat(bankService.saveBank(), is(nullValue()));
		assertFalse(!bankService.saveBank());
		assertEquals(true, bankService.saveBank());
		assertTrue(bankService.saveBank());
		assertNull(!bankMoneyService.findTotalAmount());
		assertEquals(true, bankMoneyService.findTotalAmount());
		assertFalse(!bankService.saveBank());*/
		
		assertThat(bankMoneyService.findTotalAmount(), is(notNullValue()));
		
		
    }
	
	@Test
	public void testFindGodBank() {
		System.out.println("테스트 수행 testFindGodBank");
		//assertEquals(true, bankMoneyService.findGodBank());
		
		assertThat(bankMoneyService.findGodBank(), is(notNullValue()));
	}
	
	@Test
	public void testFindKebMaxAndMin() {
		System.out.println("테스트 수행 testFindKebMaxAndMin");
		//assertEquals(true, bankMoneyService.findKebMaxAndMin());
		
		assertThat(bankMoneyService.findKebMaxAndMin(), is(notNullValue()));
	}
	
	@Test
	public void testFindPredictionAmount() {
		System.out.println("테스트 수행 testFindPredictionAmount");
		//assertEquals(true, bankMoneyService.findPredictionAmount());
		
		//assertThat(bankMoneyService.findPredictionAmount(), is(notNullValue()));
	}
	
}
