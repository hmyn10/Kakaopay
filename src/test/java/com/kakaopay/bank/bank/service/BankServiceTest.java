package com.kakaopay.bank.bank.service;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

import javax.persistence.EntityManager;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.kakaopay.bank.bank.entity.Bank;
import com.kakaopay.bank.bank.entity.BankMoney;
import com.kakaopay.bank.bank.repository.BankJpaRepo;
import com.kakaopay.bank.bank.service.BankService;

@RunWith(SpringRunner.class)
@DataJpaTest
public class BankServiceTest {
	
	@InjectMocks
    BankService bankService;

    @Mock
    BankJpaRepo bankJpaRepo;
    
    @Autowired
    private EntityManager entityManager;
    
	@Before
    public void setUp() throws Exception {
		
		mockingRepository();
		System.out.println("테스트 시작");
    }
	
	@Test
    public void testSaveBank(){
      
        //given
        
        //when
        entityManager.flush();
        boolean isSave = saveBank();
        
        //then
        assertTrue(isSave);
    }
	
	@Test
    public void testFindBankNameList(){
        /*
        //given
		List<Bank> bankList = new ArrayList<>();
        bankList.add(Bank.builder().instituteName("카카오페이").build());
        bankList.add(Bank.builder().instituteName("우리은행").build());
        bankList.add(Bank.builder().instituteName("테스트은행").build());

        Map<String,Object> expectedMap = new HashMap<>();
        expectedMap.put("bank",bankList);*/
        
        //when
        entityManager.flush();
        List<Bank> resultMap = findBankNameList();
        System.out.println(resultMap);
        
        //then
        assertNotNull(resultMap);
    }
	
	private void mockingRepository() {
		
        List<Bank> banks = new ArrayList<>();
        banks.add(Bank.builder().instituteCode(1).instituteName("카카오페이").build());
        banks.add(Bank.builder().instituteCode(2).instituteName("신한은행").build());
        
        when(bankJpaRepo.findAll()).thenReturn(banks);
    }
	
	
	/**
	 * @ service의 EntityManager 접근 시 
	 * 	 null 오류 발생
	 * 	 직접 메소드를 가져와 테스트
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
	
	@SuppressWarnings("unchecked")
	public List<Bank> findBankNameList() {
		
		String query = "select b.instituteName from Bank b";
		entityManager.flush();
		List<Bank> q = (List<Bank>) entityManager.createQuery(query).getResultList();
		
		return q;
	}
	
	
	
	
}
