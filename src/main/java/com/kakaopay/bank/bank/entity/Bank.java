package com.kakaopay.bank.bank.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor // ���ھ��� �����ڸ� �ڵ����� ����
@AllArgsConstructor // ���ڸ� ��� ���� �����ڸ� �ڵ����� ����
@SuppressWarnings("serial")
@Table(name = "bank") 
public class Bank implements Serializable{
	
	@Id
	@Column(name = "instituteCode")
	private int instituteCode; 
	
	@Column(name = "instituteName", nullable=false, length = 100)
	private String instituteName;
	
	@OneToMany(fetch=FetchType.EAGER, mappedBy = "bank")
	private List<BankMoney> bankMoneyList = new ArrayList<BankMoney>();

}

