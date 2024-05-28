package com.example.sweets;

public class SweetRequest {
    private String name;
    private String company;
    private int price;
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
