package com.robertmartins.notesapi.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "TB_USERS_ADDRESS")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AddressModel {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int id;

    @Column(nullable = false, length = 20)
    private String country;

    @Column(length = 15)
    private String postalCode;

    @Column(length = 15)
    private String uf;

    @Column(length = 20)
    private String city;

    @Column(nullable = false)
    private LocalDateTime updatedAt;

    @Column(nullable = false)
    private LocalDateTime createdAt;

}
