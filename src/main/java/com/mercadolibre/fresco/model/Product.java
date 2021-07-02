package com.mercadolibre.fresco.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "products")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String productCode;

    @Column(name = "min_temp")
    private Float minimumTemperature;
    @Column(name = "manufacturing_date")
    private LocalDate manufacturingDate;
    private LocalDateTime manufacturingTime;
    private LocalDate dueDate;
    private Float price;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private List<Stock> stocks;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private List<OrderedProduct>  orderedProducts;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "product_category_id", referencedColumnName = "id")
    private ProductCategory productCategory;




}
