package com.picpaySimplificado.dtos;

import com.picpaySimplificado.domain.user.User;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record TransactionResponseDTO(UUID id, BigDecimal amount, User sender, User receiver, LocalDateTime timestamp) {
}
