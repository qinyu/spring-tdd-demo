package info.qinyu.price;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BookPriceController {

    BookPriceCalculator bookPriceCalculator;

    BookRepository bookRepository;

    CurrencyService currencyService;

    public BookPriceController(BookRepository bookRepository, CurrencyService currencyService) {
        this.bookRepository = bookRepository;
        this.currencyService = currencyService;
        bookPriceCalculator = new BookPriceCalculator(currencyService);
    }

    public BookPriceController(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @GetMapping(path = "price")
    @ResponseStatus(HttpStatus.OK)
    public BookPrice queryBookPrice(@RequestParam("name") String name,
                                    @RequestParam(value = "currency", required = false) String currency) {

        return bookPriceCalculator.calculatePriceInCurrency(bookRepository.findByName(name), currency);
    }

    private BookPrice calculatePrice(Book book) {
        return bookPriceCalculator.calculatePrice(book);
    }
}
