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
	//1. ���� ���� ������ ������ �⵵ ��ȸ�� ������� �ϳ�����
    //2. ��ȯ���� ������ �ִ�, �ּҰ��� �⵵ ������� �ΰ�����(�ִ밪�� �ּҰ����� ū�� ������ ��)
	
	private BankMoneyService bankMoneyService; 
	
	@Before
    public void setUp() throws Exception {
		
		mockingRepository();
		System.out.println("�׽�Ʈ ����");
    }
	
	private void mockingRepository() {
	
		/*List<Bank> banks = new ArrayList<>();
        banks.add(Bank.builder().instituteCode(1).instituteName("īī������").build());
        banks.add(Bank.builder().instituteCode(2).instituteName("��������").build());
        when(bankJpaRepo.findAll()).thenReturn(banks);

        when(bankJpaRepo.findByInstituteCode(1)).thenReturn(Optional.of(
                Bank.builder().instituteCode(1).instituteName("īī������").build()));

        when(bankJpaRepo.findByInstituteName("īī������")).thenReturn(Optional.of(
                Bank.builder().instituteCode(1).instituteName("īī������").build()));

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
		System.out.println("�׽�Ʈ ���� testFindTotalAmount");
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
		System.out.println("�׽�Ʈ ���� testFindGodBank");
		//assertEquals(true, bankMoneyService.findGodBank());
		
		assertThat(bankMoneyService.findGodBank(), is(notNullValue()));
	}
	
	@Test
	public void testFindKebMaxAndMin() {
		System.out.println("�׽�Ʈ ���� testFindKebMaxAndMin");
		//assertEquals(true, bankMoneyService.findKebMaxAndMin());
		
		assertThat(bankMoneyService.findKebMaxAndMin(), is(notNullValue()));
	}
	
	@Test
	public void testFindPredictionAmount() {
		System.out.println("�׽�Ʈ ���� testFindPredictionAmount");
		//assertEquals(true, bankMoneyService.findPredictionAmount());
		
		//assertThat(bankMoneyService.findPredictionAmount(), is(notNullValue()));
	}
	
}
