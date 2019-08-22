package com.kakaopay.bank.bank.dto;

import java.io.Serializable;
import java.util.HashMap;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor // 인자없는 생성자를 자동으로 생성
@AllArgsConstructor // 인자를 모두 갖춘 생성자를 자동으로 생성
@SuppressWarnings("serial")
public class AmountForm implements Serializable{
	
	private int year; 
	private int total;
	private HashMap<String, Integer> detail;

}
