package info.qinyu.price;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BookPriceController {

    private final BookPriceCalculator bookPriceCalculator = new BookPriceCalculator();
    BookRepository bookRepository;

    public BookPriceController(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @GetMapping(path = "price")
    @ResponseStatus(HttpStatus.OK)
    public BookPrice queryBookPrice(@RequestParam("name") String name) {
        return bookPriceCalculator.calculatePrice(bookRepository.findByName(name));
    }

    private BookPrice calculatePrice(Book book) {
        return bookPriceCalculator.calculatePrice(book);
    }
}
