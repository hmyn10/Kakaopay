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
	 * @info : ��ü ���� ��ȸ
	 */
	public List<BankMoney> findBankMoneyList(){
		
		return bankMoneyJpaRepo.findAll();
	}
	
	/**
	 * 
	 * @info : �⵵�� �� ��������� �����ݾ� �հ� �� �Ѱ� ��ȸ
	 */
	@SuppressWarnings("unchecked")
	public LinkedHashMap<String, Object> findTotalAmount(){
		
		int totAmout = 0;
		AmountForm bd = new AmountForm();
		HashMap<String, Integer> detail = new HashMap<String, Integer>();
		List<AmountForm> rl = new ArrayList<AmountForm>();
		LinkedHashMap<String, Object> rr = new LinkedHashMap<String, Object>();
		//����� �⵵ ������� ��ȸ
		String query1 = "select m1.year " 
				     +  "from BankMoney m1 "
				     +  "group by m1.year "
				     +  "order by m1.year asc"; 
		Query q1 = entityManager.createQuery(query1);
		List<Integer> yearList = q1.getResultList();
		
		//�⵵ �� ����&���� �ݾ� 
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
				if(year == Integer.parseInt(dataOb[0].toString())) {//data�� ��¥�� ��ȸ ��¥�� ���ٸ�
					bd = new AmountForm();
					detail.put(dataOb[1].toString(), Integer.parseInt(dataOb[2].toString())); // {"��������":1233, "": ,...}

					//�⵵ ���� �ݾ� ����
					totAmout += (Integer.parseInt(dataOb[2].toString()));
					
				}
			}	
			bd.setYear(year);
			bd.setTotal(totAmout);
			bd.setDetail(detail);

			rl.add(bd);
		}
		
		rr.put("name", "���ñ��� ������Ȳ");
		rr.put("data", rl);
		return rr;
	}
	
	/**
	 * 
	 * @info : ���� ū �ݾ� ���� ����� �ݾ� ���
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

		//�ݾ� �������� ���ĵ� ������ �������� ���
		for(Object[] ob : list) {
			map.put("year", ob[0].toString());
			map.put("bank", ob[1].toString());
		}
		
		return map;
	}
	
	/**
	 * 
	 * @info : ��ȯ���� �ּ� �ݾ� ���(�⵵)�� �ִ� �ݾ� ���(�⵵) ��ȸ
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
		//�ٸ� ���� �ڵ嵵 �־ �ִ� �ּ� �׽�Ʈ
		List<Object[]> list = q.getResultList();

		for(Object[] ob : list) {
			c = (double)ob[2]; // ���� �ݾ�

			if(c > max) {//�ִ밪
				max = c;
			}
			if(c < min) {//�ּҰ�
				min = c;
			}
		}
		
		for(Object[] ob : list) {
			map = new HashMap<String, Object>();
		
			if((double)ob[2] == max) {
				map.put("year", ob[0].toString());
				map.put("money", ob[2].toString());
				map.put("part", "��ȯ ���� ��� �ִ� ���� �ݾ�");
				resultList.add(map);
			}
			if((double)ob[2] == min) {
				map.put("year", ob[0].toString());
				map.put("money", ob[2].toString());
				map.put("part", "��ȯ ���� ��� �ּ� ���� �ݾ�");
				resultList.add(map);
			}
			
		}
		return resultList;
	}
	
	/**
	 * 
	 * @info : 2018�� Ư�� ������ Ư�� �� ���� �ݾ� ���� API
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
