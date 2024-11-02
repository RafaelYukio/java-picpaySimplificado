package com.picpaySimplificado.services;

import com.picpaySimplificado.domain.user.User;
import com.picpaySimplificado.domain.user.UserType;
import com.picpaySimplificado.dtos.UserDTO;
import com.picpaySimplificado.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {
    @Autowired
    private UserRepository repository;

    public void validateTransaction(User sender, BigDecimal amount) throws Exception {
        if (sender.getUserType() == UserType.MERCHANT) {
            throw new Exception("Lojistas não podem realizar transações!");
        }

        if (sender.getBalance().compareTo(amount) < 0) {
            throw new Exception("Saldo insuficiente!");
        }
    }

    public User findUserById(Long id) throws Exception {
        return repository.findUserById(id).orElseThrow(() -> new Exception("Usuário de ID: " + id + " não encontrado"));
    }

    public void saveUser(User user) {
        repository.save(user);
    }

    public UserDTO createUser(UserDTO user) {
        User newUser = new User(user);
        repository.save(newUser);

        return user;
    }

    public List<UserDTO> getAllUsers() {
        List<User> users = repository.findAll();
        return users.stream()
                .map(u -> new UserDTO(u.getFirstName(), u.getLastName(), u.getDocument(), u.getBalance(), u.getEmail(), u.getPassword()))
                .toList();
    }
}
