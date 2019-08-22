package com.kakaopay.bank.account.controller;

import java.util.Collections;

import javax.persistence.EntityExistsException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kakaopay.bank.account.dto.UserDto;
import com.kakaopay.bank.account.service.UserService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/user")
public class UserController {
	
	private static Logger logger = LoggerFactory.getLogger(UserController.class);
	
	private static final HttpHeaders httpHeaders = new HttpHeaders();

	private final UserService userService;
	
	/**
	 * 
	 * @info : 계정 생성 시, Token 생성 API
	 */
	@SuppressWarnings("rawtypes")
	@PostMapping(value = "/signUp")
	public ResponseEntity signUp(UserDto userDto) {
		
        try {
            return new ResponseEntity<>(userService.signUp(userDto), httpHeaders, HttpStatus.OK);
        } catch (EntityExistsException e) {
            return new ResponseEntity<>(Collections.singletonMap("error", e.getMessage()), httpHeaders, HttpStatus.UNAUTHORIZED);
        } catch (Exception e) {
            logger.error(e.toString());
            return new ResponseEntity<>(Collections.singletonMap("error","INTERNAL SERVER ERROR"),httpHeaders, HttpStatus.INTERNAL_SERVER_ERROR);
        }
	}
	
	/**
	 * 
	 * @info : 로그인 시, Token 발급 API
	 */
	@SuppressWarnings("rawtypes")
	@PostMapping(value = "/signIn")
	public ResponseEntity signIn(UserDto userDto) {
		
		try {
            return new ResponseEntity<>(userService.signIn(userDto), httpHeaders, HttpStatus.OK);
        } catch (UsernameNotFoundException | BadCredentialsException e) {
            return new ResponseEntity<>(Collections.singletonMap("error", e.getMessage()), httpHeaders, HttpStatus.UNAUTHORIZED);
        } catch (Exception e) {
            logger.error(e.toString());
            return new ResponseEntity<>(Collections.singletonMap("error","INTERNAL SERVER ERROR"),httpHeaders, HttpStatus.INTERNAL_SERVER_ERROR);
        }
	}
	
	/**
	 * 
	 * @info : 요청 시, refresh Token 재발급 API
	 */
	@SuppressWarnings("rawtypes")
	@GetMapping(value = "/refreshToken") 
	public ResponseEntity refreshToken(@RequestHeader("Authorization") String token ) {
		System.out.println("token :::: " + token);
		try {
            return new ResponseEntity<>(userService.updateAccessToken(token), httpHeaders, HttpStatus.OK);
        } catch (IllegalAccessException| UsernameNotFoundException e) {
        	return new ResponseEntity<>(Collections.singletonMap("error", e.getMessage()), httpHeaders, HttpStatus.UNAUTHORIZED);
        } catch (Exception e) {
            logger.error(e.toString());
            return new ResponseEntity<>(Collections.singletonMap("error","INTERNAL SERVER ERROR"),httpHeaders, HttpStatus.INTERNAL_SERVER_ERROR);
        }
	}
	

}
