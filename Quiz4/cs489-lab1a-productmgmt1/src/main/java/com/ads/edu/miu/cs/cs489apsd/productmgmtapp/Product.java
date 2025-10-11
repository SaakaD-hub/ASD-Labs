
package com.ads.edu.miu.cs.cs489apsd.productmgmtapp;


import java.time.LocalDate;

public class Product {
    private long productId;
    private String name;
    private double unitPrice;
    private LocalDate dateSupplied;
    private int quantityInStock;

    public Product(long productId, String name, double unitPrice, LocalDate dateSupplied, int quantityInStock) {
        this.productId = productId;
        this.name = name;
        this.unitPrice = unitPrice;
        this.dateSupplied = dateSupplied;
        this.quantityInStock = quantityInStock;
    }

    public long getProductId() { return productId; }
    public String getName() { return name; }
    public double getUnitPrice() { return unitPrice; }
    public LocalDate getDateSupplied() { return dateSupplied; }
    public int getQuantityInStock() { return quantityInStock; }

    @Override
    public String toString() {
        return "Product{" +
                "productId=" + productId +
                ", name='" + name + '\'' +
                ", unitPrice=" + unitPrice +
                ", dateSupplied=" + dateSupplied +
                ", quantityInStock=" + quantityInStock +
                '}';
    }
}

