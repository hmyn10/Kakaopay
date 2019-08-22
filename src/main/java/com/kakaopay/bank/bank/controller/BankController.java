package com.kakaopay.bank.bank.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kakaopay.bank.bank.entity.Bank;
import com.kakaopay.bank.bank.service.BankService;

@RestController
@RequestMapping("/api")
public class BankController {
	
	private static Logger log = LoggerFactory.getLogger(BankController.class);
	private static final HttpHeaders httpHeaders = new HttpHeaders();
	
	private final BankService bankService;

	public BankController(BankService bankService) {
        httpHeaders.setContentType(MediaType.APPLICATION_JSON_UTF8);
        this.bankService = bankService;
    }
	
	/**
	 * 
	 * @info : bank table 전체 조회
	 */
	
	@GetMapping(value = "/findBankList")
	public List<Bank> findBankList() { 
		return bankService.findBankList();
	}
	
	/**
	 * 
	 * @info : csv 파일 읽은 후 저장 
	 */
	@PostMapping(value = "/saveBank")
    public ResponseEntity<Boolean> saveBank() { 
		 
		boolean isSuccess = bankService.saveBank(); 
		
		try {
			System.out.println("httpHeaders :::: " + httpHeaders);
			return new ResponseEntity<Boolean>(isSuccess, httpHeaders, HttpStatus.OK);
         } catch (Exception e) {
        	log.error(e.toString());
            return new ResponseEntity<Boolean>(httpHeaders, HttpStatus.INTERNAL_SERVER_ERROR);
         }
    }
	
	/**
	 * 
	 * @info : 기관명 조회
	 */
	@GetMapping(value = "/findBankNameList") 
	public ResponseEntity<List<Bank>> findBankNameList() {
		
		List<Bank> bankList = bankService.findBankNameList();
		
		try {
			return new ResponseEntity<List<Bank>>(bankList, httpHeaders, HttpStatus.OK);
        } catch (Exception e) {
        	log.error(e.toString());
            return new ResponseEntity<List<Bank>>(httpHeaders, HttpStatus.INTERNAL_SERVER_ERROR);
        }
	}
	

}
