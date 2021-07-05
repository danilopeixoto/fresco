package com.mercadolibre.fresco.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "user_roles")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Role {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(unique = true)
  private String roleCode;

  @OneToMany(mappedBy = "role", cascade = CascadeType.ALL)
  private List<User> users;
}
