package info.qinyu.tdd;

public class TDDBookPrice {
    private String name;
    private String price;
    private String currency;

    public TDDBookPrice(String name, String price, String currency) {
        this.name = name;
        this.price = price;
        this.currency = currency;
    }

    public String getName() {
        return name;
    }

    public String getPrice() {
        return price;
    }

    public String getCurrency() {
        return currency;
    }
}
