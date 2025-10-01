package edu.miu.cs.cs489appsd.lab1a.productmgmtapp;

import edu.miu.cs.cs489appsd.lab1.productmgmtapp.model.Product;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Locale;

public class ProductMgmtApp {
    public static void main(String[] args) {
        Product[] products = new Product[] {
            new Product(new BigInteger("31288741190182539912"), "Banana",
                    LocalDate.parse("2025-01-24"), 124, new BigDecimal("0.55")),
            new Product(new BigInteger("29274582650152771644"), "Apple",
                    LocalDate.parse("2024-12-09"), 18,  new BigDecimal("1.09")),
            new Product(new BigInteger("91899274600128155167"), "Carrot",
                    LocalDate.parse("2025-03-31"), 89,  new BigDecimal("2.99")),
            new Product(new BigInteger("31288741190182539913"), "Banana",
                    LocalDate.parse("2025-02-13"), 240, new BigDecimal("0.65"))
        };

        printProducts(products);
    }

    public static void printProducts(Product[] products) {
        Product[] sorted = Arrays.copyOf(products, products.length);
        Comparator<Product> byNameThenPriceDesc = Comparator
                .comparing(Product::getName, String.CASE_INSENSITIVE_ORDER)
                .thenComparing(Product::getUnitPrice, Comparator.reverseOrder());
        Arrays.sort(sorted, byNameThenPriceDesc);

        DecimalFormat money = new DecimalFormat("0.00",
                new DecimalFormatSymbols(Locale.US));

        // JSON
        System.out.println("Printed in JSON Format");
        System.out.println("[");
        for (int i = 0; i < sorted.length; i++) {
            Product p = sorted[i];
            String jsonLine = "    { " +
                    "\"productId\":" + p.getProductId() + ", " +
                    "\"name\":\"" + escape(p.getName()) + "\", " +
                    "\"dateSupplied\":\"" + p.getDateSupplied() + "\", " +
                    "\"quantityInStock\":" + p.getQuantityInStock() + ", " +
                    "\"unitPrice\":" + money.format(p.getUnitPrice()) + " " +
                    "}";
            System.out.println(jsonLine + (i < sorted.length - 1 ? "," : ""));
        }
        System.out.println("]");
        System.out.println("--------------------------------");

        // XML
        System.out.println("Printed in XML Format");
        System.out.println("<?xml version=\"1.0\"?>");
        System.out.println("<products>");
        for (Product p : sorted) {
            System.out.println("    <product " +
                    "productId=\"" + p.getProductId() + "\" " +
                    "name=\"" + escape(p.getName()) + "\" " +
                    "dateSupplied=\"" + p.getDateSupplied() + "\" " +
                    "quantityInStock=\"" + p.getQuantityInStock() + "\" " +
                    "unitPrice=\"" + money.format(p.getUnitPrice()) + "\" " +
                    "/>");
        }
        System.out.println("</products>");
        System.out.println("--------------------------------");

        // CSV
        System.out.println("Printed in Comma-Separated Values (CSV) Format");
        for (Product p : sorted) {
            System.out.println(p.getProductId() + ", " +
                    p.getName() + ", " +
                    p.getDateSupplied() + ", " +
                    p.getQuantityInStock() + ", " +
                    money.format(p.getUnitPrice()));
        }
    }

    private static String escape(String s) {
        return s.replace("\"", "\\\"");
    }
}
