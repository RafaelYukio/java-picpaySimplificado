package com.picpaySimplificado.dtos;

import com.picpaySimplificado.domain.user.User;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record TransactionResponseDTO(Long id, BigDecimal amount, User Sender, User Receiver, LocalDateTime timestamp) {
}
