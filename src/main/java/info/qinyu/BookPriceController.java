package info.qinyu;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BookPriceController {

    @GetMapping(path = "price")
    @ResponseStatus(HttpStatus.OK)
    public Price queryBookPrice(@RequestParam("name") String name) {
        return new Price(name, "89.00");
    }
}
