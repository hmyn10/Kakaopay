package com.kakaopay.bank.account.service;

import java.util.AbstractMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.persistence.EntityExistsException;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.kakaopay.bank.account.dto.UserDto;
import com.kakaopay.bank.account.entity.User;
import com.kakaopay.bank.account.repository.UserRepository;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;

    public UserService(UserRepository userRepository, TokenService tokenService) {
        this.userRepository = userRepository;
        this.tokenService = tokenService;
        this.passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    public Map<String, Object> signUp(UserDto userDto) {
        if (userRepository.existsByUsername(userDto.getUsername())) {
            throw new EntityExistsException("Username is duplicated");
        }

        User user = User.builder().username(userDto.getUsername())
				                  .password(passwordEncoder.encode(userDto.getPassword()))
				                  .build();
  
        userRepository.save(user);
        return getToken(user);
    }

    public Map<String, Object> signIn(UserDto userDto) {
        User user = userRepository.findByUsername(userDto.getUsername())
                                  .orElseThrow(() -> new UsernameNotFoundException("Username not found"));
        if (!passwordEncoder.matches(userDto.getPassword(), user.getPassword())) {
            throw new BadCredentialsException("Incorrect password");
        }
        return getToken(user);
    }

    private Map<String, Object> getToken(User user) {
    	System.out.println("username :::: " + user.getUsername());
        Map<String, Object> body = Stream.of(
					                new AbstractMap.SimpleEntry<>("username", user.getUsername()))
					                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

        return tokenService.publishToken(body, "userInfo");
    }

    public Map<String, Object> updateAccessToken(String originalToken) throws IllegalAccessException {
    	System.out.println("originalToken :::: " + originalToken);
        String username = tokenService.getUsernameFromToken(originalToken);
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Username not found"));

       return getToken(user);
    }
    
}
