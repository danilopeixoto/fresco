package com.mercadolibre.fresco.model.enumeration;

public enum BatchStockOrder {
    C("c"),
    F("f"),
    c("c"),
    f("f");

    private String order;

    BatchStockOrder(String order) {
        this.order = order;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }
}
