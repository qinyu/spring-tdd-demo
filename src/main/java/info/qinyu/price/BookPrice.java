package info.qinyu.price;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class BookPrice {
    private String name;
    private String price;
    private String currency = "cny";

    public BookPrice(String name, String price) {
        this.name = name;
        this.price = price;
    }
}
