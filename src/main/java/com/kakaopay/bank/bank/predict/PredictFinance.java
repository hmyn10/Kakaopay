package com.kakaopay.bank.bank.predict;

import java.util.List;

import org.springframework.stereotype.Service;

import com.kakaopay.bank.bank.entity.BankMoney;

@Service
public interface PredictFinance {
    int predictFinanceAmount(List<BankMoney> bankFinances,int predYear ,int predMonth);
}
