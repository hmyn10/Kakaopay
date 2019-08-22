package com.kakaopay.bank.account.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Builder
@Getter @Setter
@AllArgsConstructor
@Table(name = "user")
@ToString
public class User {
    protected User() {
    }

    @Id
    @GeneratedValue
    @Column(name = "user_id")
    private Long id;

    @Column(name = "user_name",unique = true)
    private String username;

    @Column(name = "user_password")
    private String password;
}

/*public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(min = 5, max = 20)
    @Column(nullable = false, unique = true)
    private String userId;

    @Column(nullable = false)
    private String password;

    public User(String userId, String password) {
        this.userId = userId;
        this.password = password;
    }

    public boolean matchPassword(String inputPassword, PasswordEncoder passwordEncoder) {
        if (!passwordEncoder.matches(inputPassword, password)) {
            throw new UnAuthenticationException(ErrorMessage.WRONG_PASSWORD);
        }
        return true;
    }

    public UserDto toUserDto() {
        return new UserDto(id, userId);
    }
}*/