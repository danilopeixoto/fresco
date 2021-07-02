package com.mercadolibre.fresco.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashMap;
import java.util.List;

@Entity
@Table(name = "cart")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer quantity;

    @OneToOne(mappedBy = "cart", cascade = CascadeType.ALL)
    private PurchaseOrder purchaseOrder;

    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL)
    private List<OrderedProduct>  orderedProducts;
}
