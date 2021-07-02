package com.mercadolibre.fresco.model;

import com.mercadolibre.fresco.model.enumeration.StatusCode;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "purchase_order")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private StatusCode statusCode;
    private LocalDate date;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "purchaseOrder")
    private List<OrderedProduct> orderedProducts;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;


}
