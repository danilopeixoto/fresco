package com.mercadolibre.fresco.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Builder(toBuilder = true)
@Entity
@Table(name = "stocks")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Stock {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "batch_number", unique = true)
    private Integer batchNumber;
    @Column(name = "init_quantity")
    private Integer initialQuantity;
    @Column(name = "cur_quantity")
    private Integer currentQuantity;
    @Column(name = "cur_temp")
    private Float currentTemperature;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "product_id", referencedColumnName = "id", nullable = true)
    private Product product;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "warehouse_section_id", referencedColumnName = "id")
    private WarehouseSection warehouseSection;
}
