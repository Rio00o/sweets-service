package com.example.sweets.controller.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class SweetRequest {
    @NotBlank
    private String name;
    @NotBlank
    private String company;
    @NotNull
    private int price;
    @NotBlank
    private String prefecture;

    public SweetRequest(String name, String company, int price, String prefecture) {
        this.name = name;
        this.company = company;
        this.price = price;
        this.prefecture = prefecture;
    }

    public String getName() {
        return name;
    }

    public String getCompany() {
        return company;
    }

    public int getPrice() {
        return price;
    }

    public String getPrefecture() {
        return prefecture;
    }
}
