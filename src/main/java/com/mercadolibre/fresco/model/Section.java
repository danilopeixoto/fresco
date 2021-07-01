package com.mercadolibre.fresco.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "sections")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Section {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String section_code;

    @OneToOne(mappedBy="section", cascade = CascadeType.ALL)
    private WarehouseSection warehouseSection;
}

