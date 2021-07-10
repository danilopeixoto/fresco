package com.mercadolibre.fresco.model.enumeration;

public enum StatusCode {

    APROVADO("Approved"),
    PENDENTE("Pending"),
    CANCELADO("Canceled");

    private String status;

    StatusCode(String status) {
        this.status = status;
    }

    public String getStatus() {
        return this.status;
    }
}
