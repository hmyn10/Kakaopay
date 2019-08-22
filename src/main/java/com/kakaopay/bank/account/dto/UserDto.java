package com.kakaopay.bank.account.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Builder
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@ToString
public class UserDto {
    private String username;
    private String password;
}
/*public class UserDto {

    private Long id;

    @NotNull
    @Size(min = 5, max = 20)
    private String userId;

    @NotNull
    @Size(min = 5, max = 20)
    private String password;

    public UserDto(Long id, String userId) {
        this.id = id;
        this.userId = userId;
    }

    public UserDto(String userId, String password) {
        this.userId = userId;
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserDto userDto = (UserDto) o;
        return userId.equals(userDto.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId);
    }

    public User toEntity(PasswordEncoder passwordEncoder) {
        return new User(userId, passwordEncoder.encode(password));
    }
}
*/