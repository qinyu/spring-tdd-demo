package info.qinyu.price;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@Builder
public class BookPrice {
    private String name;
    private String price;
    private String currency = "cny";

    private String error;

    public BookPrice(String name, String price) {
        this.name = name;
        this.price = price;
    }
}
