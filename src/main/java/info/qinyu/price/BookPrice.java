package info.qinyu.price;

import javax.persistence.Entity;

public class BookPrice {
    private String name;
    private String price;
    private String currency = "cny";

    public BookPrice(String name, String price) {
        this.name = name;
        this.price = price;
    }

    public BookPrice(String name, String price, String currency) {
        this.name = name;
        this.price = price;
        this.currency = currency;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }
}
