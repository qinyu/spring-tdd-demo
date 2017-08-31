package info.qinyu.price;

import javax.persistence.Entity;

@Entity
public class Book {

    private String name;
    private int priceInCent;

    public Book(String name, int priceInCent) {
        this.name = name;
        this.priceInCent = priceInCent;
    }

    public int getPriceInCent() {
        return priceInCent;
    }

    public void setPriceInCent(int priceInCent) {
        this.priceInCent = priceInCent;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
