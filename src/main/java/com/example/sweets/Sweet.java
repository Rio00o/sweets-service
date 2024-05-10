package com.example.sweets;

public class Sweet {
    private int id;
    private String name;
    private String company;
    private int price;
    private String prefecture;

    public Sweet(int id, String name, String company, int price, String prefecture) {
        this.id = id;
        this.name = name;
        this.company = company;
        this.price = price;
        this.prefecture = prefecture;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCompany() {
        return company;
    }

    public int getPrice() { return price;
    }

    public String getPrefecture() {
        return prefecture;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public void setPrice(int price) {
        if (price >= 0) {
            this.price = price;
        } else {
            throw new IllegalArgumentException("価格は正の数でなければなりません。");
        }
    }

    public void setPrefecture(String prefecture) {
        this.prefecture = prefecture;
    }
}
