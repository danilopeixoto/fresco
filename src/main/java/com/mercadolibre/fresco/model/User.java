package com.mercadolibre.fresco.model;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name="user_accounts")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String username;

    private String password;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="role_id", referencedColumnName = "id")
    private Role role;
}
