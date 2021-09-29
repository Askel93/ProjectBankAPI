package com.example.project.bank.api.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "natural_persons")
@Getter
@Setter
@NoArgsConstructor
public class Person extends BaseEntity{
    @Column(name = "name")
    private String name;

    @Column(name = "surname")
    private String surname;

    @Column(name = "patronymic")
    private String patronymic;

    @Column(name = "birthdate")
    private Date birthdate;

    @Column(name = "phone_number")
    private Date phoneNumber;

    @Column(name = "email")
    private Date email;
}
