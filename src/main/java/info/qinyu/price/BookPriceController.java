package info.qinyu.price;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BookPriceController {

    private final BookPriceCalculator bookPriceCalculator;

    private BookRepository bookRepository;

    public BookPriceController(BookRepository bookRepository, BookPriceCalculator bookPriceCalculator) {
        this.bookRepository = bookRepository;
        this.bookPriceCalculator = bookPriceCalculator;
    }

    @GetMapping(path = "price")
    @ResponseStatus(HttpStatus.OK)
    public BookPrice queryBookPrice(@RequestParam("name") String name,
                                    @RequestParam(value = "currency", required = false) String currency) {
        return bookPriceCalculator.calculatePriceInCurrency(bookRepository.findByName(name), currency == null ? "cny" : currency);
    }

}
