package com.picpaySimplificado.controllers;

import com.picpaySimplificado.dtos.TransactionRequestDTO;
import com.picpaySimplificado.dtos.TransactionResponseDTO;
import com.picpaySimplificado.services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/transactions")
public class TransactionController {
    @Autowired
    private TransactionService transactionService;

    @PostMapping
    public ResponseEntity<TransactionResponseDTO> createTransaction(@RequestBody TransactionRequestDTO transactionRequestDTO) throws Exception {
        TransactionResponseDTO transactionResponseDTO = transactionService.createTransaction(transactionRequestDTO);
        return new ResponseEntity<>(transactionResponseDTO, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<TransactionResponseDTO>> getAllTransactions() throws Exception {
        List<TransactionResponseDTO> transactionResponseDTOs = transactionService.getAllTransactions();
        return new ResponseEntity<>(transactionResponseDTOs, HttpStatus.OK);
    }
}
