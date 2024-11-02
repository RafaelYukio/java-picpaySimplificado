package com.picpaySimplificado.dtos;

import java.math.BigDecimal;

public record TransactionRequestDTO(BigDecimal amount, Long senderId, Long receiverId) {
}
