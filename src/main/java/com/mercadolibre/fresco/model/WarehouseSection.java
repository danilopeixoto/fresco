package com.mercadolibre.fresco.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "warehouse_section", uniqueConstraints = {@UniqueConstraint(columnNames = {"section_id", "warehouse_id"})})
@Data
@NoArgsConstructor
@AllArgsConstructor
public class WarehouseSection {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "section_id", referencedColumnName = "id")
    private Section section;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "warehouse_id", referencedColumnName = "id")
    private Warehouse warehouse;

    @OneToOne(mappedBy = "warehouseSection", cascade = CascadeType.ALL)
    private Stock stock;

}
