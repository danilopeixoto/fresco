package com.mercadolibre.fresco.model.enumeration;

public enum StatusCode {

    APROVADO("Approved"),
    PENDENTE("Pending"),
    CANCELADO("CANCELED");

    private String status;

    StatusCode(String status) {
        this.status = status;
    }

}