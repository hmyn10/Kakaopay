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
@NoArgsConstructor // 인자없는 생성자를 자동으로 생성
@AllArgsConstructor // 인자를 모두 갖춘 생성자를 자동으로 생성
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

