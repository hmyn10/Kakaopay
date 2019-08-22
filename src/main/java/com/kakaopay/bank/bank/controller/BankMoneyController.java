package com.kakaopay.bank.bank.controller;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kakaopay.bank.bank.entity.BankMoney;
import com.kakaopay.bank.bank.service.BankMoneyService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class BankMoneyController {
	
	private static Logger log = LoggerFactory.getLogger(BankController.class);
	//private final JwtManager jwtManager;
	//private final UserService userService;
	private static final HttpHeaders httpHeaders = new HttpHeaders();
	
	private final BankMoneyService bankMoneyService; 
	
	/**
	 * 
	 * @info : bank_money table 전체 조회
	 */
	@GetMapping(value = "/findBankMoneyList")
	public ResponseEntity<List<BankMoney>> findBankMoneyList() { 
		
		List<BankMoney> dataList = bankMoneyService.findBankMoneyList();
		
		try {
			return new ResponseEntity<List<BankMoney>>(dataList, httpHeaders, HttpStatus.OK);
        } catch (Exception e) {
        	log.error(e.toString());
            return new ResponseEntity<List<BankMoney>>(httpHeaders, HttpStatus.INTERNAL_SERVER_ERROR);
        }
		
	}
	
	/**
	 * 
	 * @info : 년도별 각 금융기관의 지원금액 합계 및 총계 조회
	 */
	@GetMapping(value = "/findTotalAmount")
    public ResponseEntity<LinkedHashMap<String, Object>> findTotalAmount() { 
		 
		LinkedHashMap<String, Object> totalList = bankMoneyService.findTotalAmount();
		try {
			return new ResponseEntity<LinkedHashMap<String, Object>>(totalList, httpHeaders, HttpStatus.OK);
        } catch (Exception e) {
        	log.error(e.toString());
        	return new ResponseEntity<LinkedHashMap<String, Object>>(httpHeaders, HttpStatus.INTERNAL_SERVER_ERROR);
        } 
		
    }

	/**
	 * 
	 * @info : 가장 큰 금액 지원 은행과 금액 출력
	 */
	@GetMapping(value = "/findGodBank")
    public ResponseEntity<HashMap<String, String>> findGodBank() { 
		
		HashMap<String, String> godBank = bankMoneyService.findGodBank();
		
		try {
			return new ResponseEntity<HashMap<String, String>>(godBank, httpHeaders, HttpStatus.OK);
        } catch (Exception e) {
        	log.error(e.toString());
        	return new ResponseEntity<HashMap<String, String>>(httpHeaders, HttpStatus.INTERNAL_SERVER_ERROR);
        } 
    }
	
	/**
	 * 
	 * @info : 2005 ~ 2016년  외환은행 최소 평균 금액(년도)와 최대 평균 금액(년도) 조회
	 */
	@GetMapping(value = "/findKebMaxAndMin")
    public ResponseEntity<List<HashMap<String, Object>>> findKebMaxAndMin() { 
		
		List<HashMap<String, Object>> maxMinData = bankMoneyService.findKebMaxAndMin();
		
		try {
			return new ResponseEntity<List<HashMap<String, Object>>>(maxMinData, httpHeaders, HttpStatus.OK);
        } catch (Exception e) {
        	log.error(e.toString());
        	return new ResponseEntity<List<HashMap<String, Object>>>(httpHeaders, HttpStatus.INTERNAL_SERVER_ERROR);
        } 
		
    }
	
	/**
	 * 
	 * @info : 2018년 특정 은행의 특정 달 지원 금액 예측 API
	 */
	@SuppressWarnings("rawtypes")
	@GetMapping(value = "/findPredictionAmount")
    public ResponseEntity findPredictionAmount(@RequestParam(value = "bank") String bank,
            								   @RequestParam(value = "month") int month) { 

		HashMap<String, Object> pred = bankMoneyService.findPredictionAmount(bank, month);

		if(month<1 || month>12){
			return new ResponseEntity<>(Collections.singletonMap("error","Please Check Month"),httpHeaders, HttpStatus.BAD_REQUEST);
        }

		try {
			return new ResponseEntity<>(pred, httpHeaders, HttpStatus.OK);
		} catch (EntityNotFoundException e) {
            return new ResponseEntity<>(Collections.singletonMap("error","Cannot Find Bank Name"),httpHeaders, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            log.error(e.toString());
            return new ResponseEntity<>(Collections.singletonMap("error","INTERNAL SERVER ERROR"),httpHeaders, HttpStatus.INTERNAL_SERVER_ERROR);
        } 
		
    }
	
}
