package com.mercadolibre.fresco.model.enumeration;

public enum EResultOrder {
    asc("asc"),
    desc("desc");

    private String order;

    EResultOrder(String order) {
        this.order = order;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }
}
