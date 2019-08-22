package com.kakaopay.bank.bank.service;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.Query;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.kakaopay.bank.bank.dto.AmountForm;
import com.kakaopay.bank.bank.entity.Bank;
import com.kakaopay.bank.bank.entity.BankMoney;
import com.kakaopay.bank.bank.predict.PredictFinance;
import com.kakaopay.bank.bank.repository.BankJpaRepo;
import com.kakaopay.bank.bank.repository.BankMoneyJpaRepo;
import com.kakaopay.bank.bank.service.BankMoneyService;

@RunWith(SpringRunner.class)
@DataJpaTest
public class BankMoneyServiceTest {
	
	@InjectMocks
    BankMoneyService bankMoneyService;

    @Mock
    BankJpaRepo bankJpaRepo;
    @Mock
    BankMoneyJpaRepo bankMoneyJpaRepo;

    private PredictFinance predictFinance;

    @Autowired
    private EntityManager entityManager;

    @Before
    public void setUp() {
    	
    	System.out.println("테스트 시작");
        mockingRepository();
    }
    
    @Test
    public void testFindTotalAmount() {
		System.out.println("테스트 수행 testFindTotalAmount");
		
		assertThat(findTotalAmount(), is(notNullValue()));
    }
	
	@Test
	public void testFindGodBank() {
		System.out.println("테스트 수행 testFindGodBank");
		
		assertThat(findGodBank(), is(notNullValue()));
	}
	
	@Test
	public void testFindKebMaxAndMin() {
		System.out.println("테스트 수행 testFindKebMaxAndMin");
		
		assertThat(findKebMaxAndMin(), is(notNullValue()));
	}
	
	@Test
	public void testFindPredictionAmount() {
		System.out.println("테스트 수행 testFindPredictionAmount");
		//assertEquals(true, bankMoneyService.findPredictionAmount());
		
		assertThat(findPredictionAmount("국민은행", 2), is(notNullValue()));
	}
	
	
	private void mockingRepository() {
	
		List<Bank> banks = new ArrayList<>();
        banks.add(Bank.builder().instituteCode(1).instituteName("카카오페이").build());
        banks.add(Bank.builder().instituteCode(2).instituteName("신한은행").build());
        
        when(bankJpaRepo.findAll()).thenReturn(banks);

        List<BankMoney> bankMoneys = new ArrayList<>();
        for (int i = 1; i < 13; i++) {
        	bankMoneys.add(BankMoney.builder().instituteCode(1).money(2000 * i).year(2015).month(i).build());
        	bankMoneys.add(BankMoney.builder().instituteCode(2).money(1500 * i).year(2015).month(i).build());
        	bankMoneys.add(BankMoney.builder().instituteCode(1).money(1000 * i).year(2016).month(i).build());
        	bankMoneys.add(BankMoney.builder().instituteCode(2).money(500 * i).year(2016).month(i).build());
        }
        
        when(bankMoneyJpaRepo.findAll()).thenReturn(bankMoneys);
        when(bankMoneyJpaRepo.findAllByInstituteCode(1)).thenReturn(
        		bankMoneys.stream().filter(bankMoney -> bankMoney.getInstituteCode() == 1)
                        		   .collect(Collectors.toList()));
    }
	
	
	@SuppressWarnings("unchecked")
	public LinkedHashMap<String, Object> findTotalAmount(){
		
		int totAmout = 0;
		AmountForm bd = new AmountForm();
		HashMap<String, Integer> detail = new HashMap<String, Integer>();
		List<AmountForm> rl = new ArrayList<AmountForm>();
		LinkedHashMap<String, Object> rr = new LinkedHashMap<String, Object>();
		//저장된 년도 순서대로 조회
		String query1 = "select m1.year " 
				     +  "from BankMoney m1 "
				     +  "group by m1.year "
				     +  "order by m1.year asc"; 
		Query q1 = entityManager.createQuery(query1);
		List<Integer> yearList = q1.getResultList();
		
		//년도 별 은행&지원 금액 
		String query2 = "select   m.year, b.instituteName" + 
				 "          ,     SUM(m.money) as totMoney" +  
				 "       from     Bank b" +
				 "       inner join fetch BankMoney m  " +
				 "       on       b.instituteCode = m.instituteCode" +
				 "       group by m.year, b.instituteName" +
				 "       order by m.year asc"; 
		Query q2 = entityManager.createQuery(query2);
		List<Object[]> dataList = q2.getResultList();
		
		for(int year : yearList) { // 2005, 2006, 2007,...
			totAmout = 0;
			//System.out.println("year :" + year);
			detail = new HashMap<String, Integer>();
			
			for(Object[] dataOb : dataList) {
				if(year == Integer.parseInt(dataOb[0].toString())) {//data의 날짜와 조회 날짜가 같다면
					bd = new AmountForm();
					detail.put(dataOb[1].toString(), Integer.parseInt(dataOb[2].toString())); // {"국민은행":1233, "": ,...}

					//년도 은행 금액 총합
					totAmout += (Integer.parseInt(dataOb[2].toString()));
					
				}
			}	
			bd.setYear(year);
			bd.setTotal(totAmout);
			bd.setDetail(detail);

			rl.add(bd);
		}
		
		rr.put("name", "주택금융 공급현황");
		rr.put("data", rl);
		return rr;
	}
	
	@SuppressWarnings("unchecked")
	public HashMap<String, String> findGodBank() {
		HashMap<String, String> map = new HashMap<String, String>();
		String query = "select     m.year, b.instituteName" + 
				"          ,       SUM(m.money)" + 
				"       from       Bank b" +
				"       inner join fetch BankMoney m  " + 
				"       on         b.instituteCode = m.instituteCode" +
				"       group by   m.year, b.instituteName" + 
				"       order by   SUM(m.money) asc"; 
		Query q = entityManager.createQuery(query); 
		List<Object[]> list = q.getResultList();

		//금액 기준으로 정렬된 데이터 마지막만 출력
		for(Object[] ob : list) {
			map.put("year", ob[0].toString());
			map.put("bank", ob[1].toString());
		}
		
		return map;
	}
	
	@SuppressWarnings("unchecked")
	public List<HashMap<String, Object>> findKebMaxAndMin(){
		
		List<HashMap<String, Object>> resultList = new ArrayList<HashMap<String, Object>>();
		HashMap<String, Object> map = new HashMap<String, Object>();
		double max = -1;
		double min = 999999999;
		double c;
		String query = "select   m.year, b.instituteName" + 
				"          ,     round(avg(m.money),1) as avgMoney" + 
				"       from     Bank b" +
				"       inner join fetch BankMoney m  " + 
				"       on       b.instituteCode = m.instituteCode" +
				"       where    m.instituteCode = 8" + 
				"		and      m.year != '2017'" +
				"       group by m.year, b.instituteName" + 
				"       order by avg(m.money) asc"; 
		Query q = entityManager.createQuery(query); 
		//다른 은행 코드도 넣어서 최대 최소 테스트
		List<Object[]> list = q.getResultList();

		for(Object[] ob : list) {
			c = (double)ob[2]; // 지원 금액

			if(c > max) {//최대값
				max = c;
			}
			if(c < min) {//최소값
				min = c;
			}
		}
		
		for(Object[] ob : list) {
			map = new HashMap<String, Object>();
		
			if((double)ob[2] == max) {
				map.put("year", ob[0].toString());
				map.put("money", ob[2].toString());
				map.put("part", "외환 은행 평균 최대 지원 금액");
				resultList.add(map);
			}
			if((double)ob[2] == min) {
				map.put("year", ob[0].toString());
				map.put("money", ob[2].toString());
				map.put("part", "외환 은행 평균 최소 지원 금액");
				resultList.add(map);
			}
			
		}
		return resultList;
	}
	
	public LinkedHashMap<String, Object> findPredictionAmount(String name, int predMonth){
		
		Bank bank = bankJpaRepo.findByInstituteName(name)
                               .orElseThrow(EntityNotFoundException::new);
		System.out.println("bank"+bank);
		List<BankMoney> bankMoneyList = bankMoneyJpaRepo.findAllByInstituteCode(bank.getInstituteCode());
		System.out.println("bankMoneyList"+bankMoneyList);
		int predYear = bankMoneyList.get(bankMoneyList.size() - 1).getYear() +1;
        int predAmount = predictFinance.predictFinanceAmount(bankMoneyList,predYear,predMonth);
        System.out.println(predYear);
        LinkedHashMap<String, Object> resultMap = new LinkedHashMap<>();
        
        resultMap.put("bank",bankMoneyList.get(0).getInstituteCode());
        resultMap.put("year",predYear);
        resultMap.put("month",predMonth);
        resultMap.put("amount",predAmount);
        System.out.println("resultMap"+resultMap);
        return resultMap;
	}
	
}
