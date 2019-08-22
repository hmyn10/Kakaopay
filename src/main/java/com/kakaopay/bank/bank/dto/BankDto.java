package com.kakaopay.bank.bank.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class BankDto {
    String name;
}
