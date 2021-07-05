package com.mercadolibre.fresco.model.enumeration;

public enum EProductCategory {
  FRESH("FS"),
  REFRIGERADO("RF"),
  CONGELADO("FF");

  private String category;

  EProductCategory(String category) {
    this.category = category;
  }

  public String getCategory() {
    return this.category;
  }
}
