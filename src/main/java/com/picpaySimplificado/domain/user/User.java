package com.picpaySimplificado.domain.user;

import com.picpaySimplificado.domain.base.BaseEntity;
import com.picpaySimplificado.dtos.UserRequestDTO;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity(name = "users")
@Table(name = "users")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User extends BaseEntity {
    private String firstName;
    private String lastName;

    @Column(unique = true)
    private String document;

    @Column(unique = true)
    private String email;
    private String password;

    private BigDecimal balance;

    @Enumerated(EnumType.STRING)
    private UserType userType;

    public User(UserRequestDTO userRequestDTO) {
        this.firstName = userRequestDTO.firstName();
        this.lastName = userRequestDTO.lastName();
        this.document = userRequestDTO.document();
        this.email = userRequestDTO.email();
        this.password = userRequestDTO.password();
        this.balance = userRequestDTO.balance();
        this.userType = userRequestDTO.userType();
    }
}
