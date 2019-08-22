package com.kakaopay.bank.bank.entity;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Builder
@Data
@NoArgsConstructor 
@AllArgsConstructor 
@SuppressWarnings("serial")
@IdClass(BankMoneyId.class)
@Table(name = "bank_money") 
public class BankMoney implements Serializable{

	@Id
	@Column(name = "year")
	private int year;
	
	@Id
	@Column(name = "month")
	private int month;
	
	@Id
	@Column(name = "instituteCode")
	private int instituteCode;
	
	@Column(name = "money")
	private int money;  
	
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, optional = true)
    		
	@JoinColumn(name = "parent_institute_code") /*, foreignKey = @ForeignKey(value = ConstraintMode.NO_CONSTRAINT)*/
    private Bank bank;
	
	public BankMoney(int year,
			         int month,
				 	 int instituteCode,
					 int money) {
	this.year = year;
	this.month = month;
	this.instituteCode = instituteCode;
	this.money = money;
	}
	/*
	public void setBankMoney(Bank bank) {
        if (bank != null) {
        	bank.getBankMoneyList().remove(this);
        }
        this.bank = bank;
        this.bank.getBankMoneyList().add(this);
    }*/
}
