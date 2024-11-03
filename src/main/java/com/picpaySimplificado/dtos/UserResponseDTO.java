package com.picpaySimplificado.dtos;

import com.picpaySimplificado.domain.user.UserType;

import java.math.BigDecimal;
import java.util.UUID;

public record UserResponseDTO(UUID id, String firstName, String lastName, String document, UserType userType, BigDecimal balance, String email, String password) {
}
