package com.mercadolibre.fresco.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "ordered_products")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderedProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer quantity;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "purchase_order_id", referencedColumnName = "id")
    private PurchaseOrder purchaseOrder;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "product_code", referencedColumnName = "productCode")
    private Product product;

}
