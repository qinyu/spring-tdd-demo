package info.qinyu.price;


import info.qinyu.book.Book;
import info.qinyu.currency.CurrencyService;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;

@Component
public class BookPriceCalculator {

    private CurrencyService currencyService;

    public BookPriceCalculator(CurrencyService currencyService) {
        this.currencyService = currencyService;
    }

    BookPrice calculatePrice(Book book) {
        return calculatePriceInCurrency(book, "cny");
    }

    public BookPrice calculatePriceInCurrency(Book book, @NotNull String currency) throws IllegalArgumentException {
        float priceInCny = book.getPriceInCent() / 100f;
        double priceD = "cny".equalsIgnoreCase(currency) ? priceInCny : priceInCny * getExchangeForCurrency(currency);
        return new BookPrice(book.getName(), String.format("%.02f", priceD), currency, null);
    }

    double getExchangeForCurrency(String currency) {
        return currencyService.getExchangeForCurrency(currency);
    }
}