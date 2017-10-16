package com.sainsburys.services.plpsummary.response;

import com.google.gson.annotations.SerializedName;

import java.math.BigDecimal;

/**
 * Product Data
 */
public class Product {

    /**
     * Title of the product (eg. Sainsbury's Blueberries 200g)
     */
    private String title;

    /**
     * Kcal Per 100g of the product. May not always be present.
     */
    @SerializedName("kcal_per_100g")
    private Integer kcalPer100g;

    /**
     * Price per unit of the product (eg. 1.56)
     */
    @SerializedName("unit_price")
    private BigDecimal pricePerUnit;

    /**
     * First line of the product description (eg. by Sainsbury's blueberries)
     */
    private String description;

    public Product(String title, Integer kcalPer100g, BigDecimal pricePerUnit, String description) {
        this.title = title;
        this.kcalPer100g = kcalPer100g;
        this.pricePerUnit = pricePerUnit;
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public Integer getKcalPer100g() {
        return kcalPer100g;
    }

    public BigDecimal getPricePerUnit() {
        return pricePerUnit;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return "Product{title=" + title +
                ", kcalPer100g=" + kcalPer100g +
                ", pricePerUnit=" + pricePerUnit +
                ", description=" + description +
                "}";
    }
}
