package com.mercadolibre.fresco.model.enumeration;

public enum EProductCategory {
    FS("FS"),
    RF("RF"),
    FF("FF");

    private String category;

    EProductCategory(String category) {
        this.category = category;
    }

    public String getCategory() {
        return this.category;
    }
}
