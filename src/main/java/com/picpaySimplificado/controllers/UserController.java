package com.picpaySimplificado.controllers;

import com.picpaySimplificado.domain.user.User;
import com.picpaySimplificado.dtos.UserDTO;
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
    public ResponseEntity<UserDTO> createUser(@RequestBody UserDTO userDTO) {
        UserDTO newUser = userService.createUser(userDTO);
        return new ResponseEntity<UserDTO>(newUser, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        var users = userService.getAllUsers();
        return new ResponseEntity<List<UserDTO>>(users, HttpStatus.OK);
    }
}
