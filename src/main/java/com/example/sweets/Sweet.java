package com.example.sweets;

import java.util.Objects;

public class Sweet {
    private Integer id;
    private String name;
    private String company;
    private int price;
    private String prefecture;

    public Sweet(Integer id, String name, String company, int price, String prefecture) {
        this.id = id;
        this.name = name;
        this.company = company;
        this.price = price;
        this.prefecture = prefecture;
    }

    public Sweet(String name, String company, int price, String prefecture) {
        this.name = name;
        this.company = company;
        this.price = price;
        this.prefecture = prefecture;
    }

    public Integer getId() {
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

    public void setId(Integer id) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Sweet sweet = (Sweet) o;

        if (price != sweet.price) return false;
        if (!Objects.equals(id, sweet.id)) return false;
        if (!Objects.equals(name, sweet.name)) return false;
        if (!Objects.equals(company, sweet.company)) return false;
        return Objects.equals(prefecture, sweet.prefecture);
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (company != null ? company.hashCode() : 0);
        result = 31 * result + price;
        result = 31 * result + (prefecture != null ? prefecture.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Sweet{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", company='" + company + '\'' +
                ", price=" + price +
                ", prefecture='" + prefecture + '\'' +
                '}';
    }
}
