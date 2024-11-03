package com.picpaySimplificado.services;

import com.picpaySimplificado.domain.user.User;
import com.picpaySimplificado.domain.user.UserType;
import com.picpaySimplificado.dtos.UserRequestDTO;
import com.picpaySimplificado.dtos.UserResponseDTO;
import com.picpaySimplificado.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

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

    public User findUserById(UUID id) throws Exception {
        return repository.findUserById(id).orElseThrow(() -> new Exception("Usuário de ID: " + id + " não encontrado"));
    }

    public void saveUser(User user) {
        repository.save(user);
    }

    public UserResponseDTO createUser(UserRequestDTO user) {
        User newUser = new User(user);
        repository.save(newUser);

        return new UserResponseDTO(newUser.getId(),
                                   newUser.getFirstName(),
                                   newUser.getLastName(),
                                   newUser.getDocument(),
                                   newUser.getUserType(),
                                   newUser.getBalance(),
                                   newUser.getEmail(),
                                   newUser.getPassword());
    }

    public List<UserResponseDTO> getAllUsers() {
        List<User> users = repository.findAll();
        return users.stream()
                .map(u -> new UserResponseDTO(u.getId(), u.getFirstName(), u.getLastName(), u.getDocument(), u.getUserType(),u.getBalance(), u.getEmail(), u.getPassword()))
                .toList();
    }
}
