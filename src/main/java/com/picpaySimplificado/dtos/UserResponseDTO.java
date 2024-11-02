package com.picpaySimplificado.dtos;

import com.picpaySimplificado.domain.user.UserType;

import java.math.BigDecimal;

public record UserResponseDTO(Long id, String firstName, String lastName, String document, UserType userType, BigDecimal balance, String email, String password) {
}
