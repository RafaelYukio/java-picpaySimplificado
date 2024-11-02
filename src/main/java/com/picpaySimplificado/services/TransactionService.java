package com.picpaySimplificado.services;

import com.picpaySimplificado.domain.transaction.Transaction;
import com.picpaySimplificado.domain.user.User;
import com.picpaySimplificado.dtos.TransactionRequestDTO;
import com.picpaySimplificado.dtos.TransactionResponseDTO;
import com.picpaySimplificado.repositories.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
public class TransactionService {
    @Autowired
    private TransactionRepository repository;

    @Autowired
    private UserService userService;
    @Autowired
    private NotificationService notificationService;

    @Autowired
    private RestTemplate restTemplate;

    public TransactionResponseDTO createTransaction(TransactionRequestDTO transactionRequestDTO) throws Exception {
        User sender = userService.findUserById(transactionRequestDTO.senderId());
        userService.validateTransaction(sender, transactionRequestDTO.amount());

        boolean isAuthorized = authorizeTransaction();
        if (!isAuthorized) {
            throw new Exception("Transação não autorizada!");
        }

        User receiver = userService.findUserById(transactionRequestDTO.receiverId());
        Transaction transaction = new Transaction(transactionRequestDTO.amount(), sender, receiver, LocalDateTime.now());

        sender.setBalance(sender.getBalance().subtract(transaction.getAmount()));
        receiver.setBalance(receiver.getBalance().add(transaction.getAmount()));

        repository.save(transaction);
        userService.saveUser(sender);
        userService.saveUser(receiver);

        notificationService.sendNotification(sender, "Transação realizada com sucesso!");
        notificationService.sendNotification(receiver, "Transação recebida com sucesso!");

        return new TransactionResponseDTO(transaction.getId(),
                                          transaction.getAmount(),
                                          transaction.getSender(),
                                          transaction.getReceiver(),
                                          transaction.getTimestamp());
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

    public List<TransactionResponseDTO> getAllTransactions() {
        return repository.findAll().stream()
                .map(t -> new TransactionResponseDTO(t.getId(), t.getAmount(), t.getSender(), t.getReceiver(), t.getTimestamp()))
                .toList();
    }
}
