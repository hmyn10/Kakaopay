package com.kakaopay.bank.bank.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.kakaopay.bank.bank.entity.BankMoney;
import com.kakaopay.bank.bank.entity.BankMoneyId;

@Repository
public interface BankMoneyJpaRepo extends JpaRepository<BankMoney, BankMoneyId>{
	
	List<BankMoney> findAllByInstituteCode(int code);
}
