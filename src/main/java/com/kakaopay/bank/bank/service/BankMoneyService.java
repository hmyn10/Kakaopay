package com.kakaopay.bank.bank.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.Query;

import org.springframework.stereotype.Service;

import com.kakaopay.bank.bank.dto.AmountForm;
import com.kakaopay.bank.bank.entity.Bank;
import com.kakaopay.bank.bank.entity.BankMoney;
import com.kakaopay.bank.bank.predict.PredictFinance;
import com.kakaopay.bank.bank.repository.BankJpaRepo;
import com.kakaopay.bank.bank.repository.BankMoneyJpaRepo;

@Service
public class BankMoneyService {
	
    private final EntityManager entityManager;
	
	private final BankJpaRepo bankJpaRepo;
	private final BankMoneyJpaRepo bankMoneyJpaRepo;
	
	private final PredictFinance predictFinance;
	
	public BankMoneyService(EntityManager entityManager, BankJpaRepo bankJpaRepo, BankMoneyJpaRepo bankMoneyJpaRepo, PredictFinance predictFinance) {
        this.entityManager = entityManager;
        this.bankJpaRepo = bankJpaRepo;
        this.bankMoneyJpaRepo = bankMoneyJpaRepo;
        this.predictFinance = predictFinance;
    }
	
	/**
	 * 
	 * @info : 전체 은행 조회
	 */
	public List<BankMoney> findBankMoneyList(){
		
		return bankMoneyJpaRepo.findAll();
	}
	
	/**
	 * 
	 * @info : 년도별 각 금융기관의 지원금액 합계 및 총계 조회
	 */
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
	
	/**
	 * 
	 * @info : 가장 큰 금액 지원 은행과 금액 출력
	 */
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
	
	/**
	 * 
	 * @info : 외환은행 최소 금액 평균(년도)과 최대 금액 평균(년도) 조회
	 */
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
	
	/**
	 * 
	 * @info : 2018년 특정 은행의 특정 달 지원 금액 예측 API
	 */
	public LinkedHashMap<String, Object> findPredictionAmount(String name, int predMonth){
		
		Bank bank = bankJpaRepo.findByInstituteName(name)
                               .orElseThrow(EntityNotFoundException::new);

		List<BankMoney> bankMoneyList = bankMoneyJpaRepo.findAllByInstituteCode(bank.getInstituteCode());

		int predYear = bankMoneyList.get(bankMoneyList.size() - 1).getYear() +1;
        int predAmount = predictFinance.predictFinanceAmount(bankMoneyList,predYear,predMonth);
 
        LinkedHashMap<String, Object> resultMap = new LinkedHashMap<>();
        
        resultMap.put("bank",bankMoneyList.get(0).getInstituteCode());
        resultMap.put("year",predYear);
        resultMap.put("month",predMonth);
        resultMap.put("amount",predAmount);
        
        return resultMap;
	}
	
}
