package com.kakaopay.bank.bank.security;

@SuppressWarnings("serial")
public class UnauthorizedException extends RuntimeException{

    public UnauthorizedException() {
        super("Unauthorized Access Token");
    }

    public UnauthorizedException(String message) {
        super(message);
    }

}