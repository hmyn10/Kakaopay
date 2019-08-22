package com.kakaopay.bank.bank.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Id;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
@Getter
@SuppressWarnings("serial")
public class BankMoneyId implements Serializable {
	
	@EqualsAndHashCode.Include
	@Id
	@Column(name = "year", length=4)
	private int year;
	
	@EqualsAndHashCode.Include
	@Id
	@Column(name = "month", length=2)
	private int month;
	
	@EqualsAndHashCode.Include
	@Id
	@Column(name = "instituteCode")
	private int instituteCode;
	
	public BankMoneyId(int year,
			           int month,
					   int instituteCode) {
	  this.year = year;
	  this.month = month;
	  this.instituteCode = instituteCode;
	}
}
