package com.kakaopay.bank.bank.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.kakaopay.bank.bank.entity.Bank;

@Repository
public interface BankJpaRepo extends JpaRepository<Bank, Integer>{
	
	Optional<Bank> findByInstituteName(String name);
}
