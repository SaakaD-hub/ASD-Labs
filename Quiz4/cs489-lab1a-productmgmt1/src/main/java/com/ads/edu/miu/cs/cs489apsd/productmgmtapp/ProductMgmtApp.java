package com.ads.edu.miu.cs.cs489apsd.productmgmtapp;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class ProductMgmtApp {
    private static final Logger LOGGER = Logger.getLogger(ProductMgmtApp.class.getName());

    public static void main(String[] args) {
        LOGGER.info("Hello! Welcome.\nStarting ProductMgmtApp...");

        List<Product> products = new ArrayList<>();
        products.add(new Product(9189927460L, "Carrot", 2.99, LocalDate.parse("2023-03-31"), 89));
        products.add(new Product(2927458265L, "Apple", 1.09, LocalDate.parse("2022-12-09"), 18));
        products.add(new Product(1374928364L, "Banana", 0.79, LocalDate.parse("2023-02-21"), 54));
        products.add(new Product(4455629837L, "Mango", 3.59, LocalDate.parse("2023-01-18"), 26));

        System.out.println("Printing Products in JSON format:");
        System.out.println(toJson(products));
    }

    private static String toJson(List<Product> products) {
        JSONArray jsonArray = new JSONArray();

        for (Product p : products) {
            JSONObject jsonObj = new JSONObject();
            jsonObj.put("productId", p.getProductId());
            jsonObj.put("name", p.getName());
            jsonObj.put("unitPrice", p.getUnitPrice());
            jsonObj.put("dateSupplied", p.getDateSupplied().toString());
            jsonObj.put("quantityInStock", p.getQuantityInStock());
            jsonArray.add(jsonObj);
        }
        return jsonArray.toJSONString();
    }
}
