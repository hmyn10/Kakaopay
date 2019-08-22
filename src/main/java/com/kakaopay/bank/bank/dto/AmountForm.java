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
@NoArgsConstructor // ���ھ��� �����ڸ� �ڵ����� ����
@AllArgsConstructor // ���ڸ� ��� ���� �����ڸ� �ڵ����� ����
@SuppressWarnings("serial")
public class AmountForm implements Serializable{
	
	private int year; 
	private int total;
	private HashMap<String, Integer> detail;

}
