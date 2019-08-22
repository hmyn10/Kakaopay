package com.kakaopay.bank.bank.security;

import org.springframework.stereotype.Service;

@Service
public interface TokenChecker {
    boolean isUsable(String jwt);
}