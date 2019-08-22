package com.kakaopay.bank.bank.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kakaopay.bank.bank.entity.Bank;
import com.kakaopay.bank.bank.entity.BankMoney;
import com.kakaopay.bank.bank.repository.BankJpaRepo;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class BankService {
	
	//@PersistenceContext
    private final EntityManager entityManager;
	
	private final BankJpaRepo bankJpaRepo;
	
	@SuppressWarnings("unchecked")
	public List<Bank> findBankNameList() {
		
		String query = "select b.instituteName from Bank b";
		
		List<Bank> q = (List<Bank>) entityManager.createQuery(query).getResultList();
		System.out.println("q :" + q);
		return q;
	}
	
	/**
	 * 
	 * @info : 전체 은행 조회
	 */
	public List<Bank> findBankList(){
		
		return bankJpaRepo.findAll();
	}
	
	/**
	 * 
	 * @info : csv 파일 읽은 후 데이터 저장 
	 */
	public boolean saveBank() {
		Bank bk;
		BankMoney bm;
		BufferedReader br = null;
        int cnt = 0;
		try {
            File csv = new File("src\\main\\resources\\csv\\file3.csv");
            br = new BufferedReader(new FileReader(csv));
            String line = "";
            
            while((line = br.readLine()) != null){
                List<String> tmpList = new ArrayList<String>();
                String array[] = line.split(",");
                
                //배열에서 리스트 반환
                tmpList = Arrays.asList(array);
                if(cnt == 0) {//csv 첫 줄 저장 
                	for (int c=0; c<tmpList.size()-2; c++) {
                		
                		bk = new Bank();
                		
                		bk.setInstituteCode(c+1);
                		bk.setInstituteName(tmpList.get(c+2));

                		entityManager.persist(bk);
                	}
                	cnt++;
                } else {
                	for (int b=0; b<tmpList.size(); b++) {

                		if(b<2) {
                			//월이 1자리 수 일 때, 0 붙히기
                    		if(array[1].length() == 1) {
                    			array[1] = "0" + array[1];
                    		}
                		}else { //2,3,4,..
                			
                			bm = new BankMoney();
                			
                			bm.setYear(Integer.parseInt(array[0]));
                			bm.setMonth(Integer.parseInt(array[1]));
                			bm.setInstituteCode(b-1);
                			bm.setMoney(Integer.parseInt(array[b]));

                			entityManager.persist(bm);
							
                		}
                		
                	}
                }
                
            }

        }catch (FileNotFoundException e) {
            e.printStackTrace();
        }catch (IOException e) {
            e.printStackTrace();
        }finally {
        	try {
        		if(br != null) {
        			br.close();
        		}
        	}catch (IOException e) {
				e.printStackTrace();
			}
        }
		return true;
	}
	
	
	
	
}
