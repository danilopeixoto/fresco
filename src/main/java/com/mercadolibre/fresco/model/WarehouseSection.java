package com.mercadolibre.fresco.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "warehouse_section", uniqueConstraints = {@UniqueConstraint(columnNames = {"section_id", "warehouse_id"})})
@Data
@NoArgsConstructor
@AllArgsConstructor
public class WarehouseSection {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "section_id", referencedColumnName = "id")
    private Section section;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "warehouse_id", referencedColumnName = "id")
    private Warehouse warehouse;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "warehouseSection", cascade = CascadeType.ALL)
    private List<Stock> stock;
}
