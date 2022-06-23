package com.robertmartins.notesapi.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "TB_USERS_PROFILE")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProfileModel {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int id;

    @Column(nullable = false, length = 50)
    private String name;

    @Column(length = 15)
    private String cpf;

    @Column(length = 15)
    private String birthDate;

    @Column(length = 15)
    private String telephone;

    @Column(nullable = false, unique = true, length = 30)
    private String email;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "addressId", referencedColumnName = "id")
    private AddressModel address;

    @Column(nullable = false)
    private LocalDateTime updatedAt;

    @Column(nullable = false)
    private LocalDateTime createdAt;

}
