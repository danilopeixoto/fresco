package com.mercadolibre.fresco.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import lombok.*;

import java.time.LocalDate;

@Builder(toBuilder = true)

public class InfoStockDTO {
    @JsonProperty(value = "batch_number")
    private int batchNumber;
    @JsonProperty(value = "current_quantity")
    private int currentQuantity;

    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonFormat(pattern = "MM-dd-yyyy")
    @JsonProperty(value = "due_date")
    private LocalDate dueDate;
    @JsonProperty(value = "section_code")
    private String sectionCode;
    @JsonProperty(value = "warehouse_code")
    private String warehouseCode;

    public InfoStockDTO(int batchNumber, int currentQuantity, LocalDate dueDate, String sectionCode, String warehouseCode) {
        this.batchNumber = batchNumber;
        this.currentQuantity = currentQuantity;
        this.dueDate = dueDate;
        this.sectionCode = sectionCode;
        this.warehouseCode = warehouseCode;
    }

    public InfoStockDTO() {}

    public int getBatchNumber() {
        return batchNumber;
    }

    public void setBatchNumber(int batchNumber) {
        this.batchNumber = batchNumber;
    }

    public int getCurrentQuantity() {
        return currentQuantity;
    }

    public void setCurrentQuantity(int currentQuantity) {
        this.currentQuantity = currentQuantity;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public String getSectionCode() {
        return sectionCode;
    }

    public void setSectionCode(String sectionCode) {
        this.sectionCode = sectionCode;
    }

    public String getWarehouseCode() {
        return warehouseCode;
    }

    public void setWarehouseCode(String warehouseCode) {
        this.warehouseCode = warehouseCode;
    }
}
