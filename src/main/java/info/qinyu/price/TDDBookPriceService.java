package info.qinyu.price;

import info.qinyu.book.Book;
import info.qinyu.book.BookRepository;

public class TDDBookPriceService {
    public static final String CNY = "cny";
    private BookRepository bookRepository;
    private TDDCurrencyService currencyService;

    public TDDBookPriceService(BookRepository bookRepository, TDDCurrencyService currencyService) {

        this.bookRepository = bookRepository;
        this.currencyService = currencyService;
    }

    public TDDBookPrice calculatePrice(String bookName, String currency) {

        Book book = bookRepository.findByName(bookName);
        if (currency.equals(CNY))  {
            double price = book.getPriceInCent() / 100.0;
            return new TDDBookPrice(bookName, formastPrice(price), currency);
        }
        double rate = currencyService.queryCurrency(currency);
        double price = book.getPriceInCent() * rate / 100.0;
        return new TDDBookPrice(bookName, formastPrice(price), currency);

    }

    String formastPrice(double price) {
        return String.format("%.2f", price);
    }
}
