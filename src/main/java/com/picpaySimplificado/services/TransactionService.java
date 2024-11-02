package com.picpaySimplificado.services;

import com.picpaySimplificado.domain.transaction.Transaction;
import com.picpaySimplificado.domain.user.User;
import com.picpaySimplificado.dtos.TransactionDTO;
import com.picpaySimplificado.repositories.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Objects;

@Service
public class TransactionService {
    @Autowired
    private TransactionRepository repository;

    @Autowired
    private UserService userService;

    @Autowired
    private RestTemplate restTemplate;

    public void createTransaction(TransactionDTO transactionDTO) throws Exception {
        User sender = userService.findUserById(transactionDTO.senderId());
        userService.validateTransaction(sender, transactionDTO.amount());

        boolean isAuthorized = authorizeTransaction();
        if (!isAuthorized) {
            throw new Exception("Transação não autorizada!");
        }

        User receiver = userService.findUserById(transactionDTO.receiverId());
        Transaction transaction = new Transaction(transactionDTO.amount(), sender, receiver, LocalDateTime.now());

        sender.setBalance(sender.getBalance().subtract(transaction.getAmount()));
        receiver.setBalance(receiver.getBalance().add(transaction.getAmount()));

        repository.save(transaction);
        userService.saveUser(sender);
        userService.saveUser(receiver);
    }

    public boolean authorizeTransaction() {
        ResponseEntity<Map> authorizatinoResponse = restTemplate.getForEntity("https://util.devi.tools/api/v2/authorize", Map.class);

        Map<String, Object> body = authorizatinoResponse.getBody();

        if (authorizatinoResponse.getStatusCode().is2xxSuccessful() && body != null) {
            String status = (String) body.get("status");

            Map<String, Object> data = (Map<String, Object>) body.get("data");

            if (status.equals("success") && data != null) {
                return (boolean) data.get("authorization");
            }
        }

        return false;
    }
}
