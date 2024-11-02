package com.picpaySimplificado.controllers;

import com.picpaySimplificado.dtos.UserRequestDTO;
import com.picpaySimplificado.dtos.UserResponseDTO;
import com.picpaySimplificado.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<UserResponseDTO> createUser(@RequestBody UserRequestDTO userRequestDTO) {
        UserResponseDTO newUser = userService.createUser(userRequestDTO);
        return new ResponseEntity<UserResponseDTO>(newUser, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<UserResponseDTO>> getAllUsers() {
        var users = userService.getAllUsers();
        return new ResponseEntity<List<UserResponseDTO>>(users, HttpStatus.OK);
    }
}
