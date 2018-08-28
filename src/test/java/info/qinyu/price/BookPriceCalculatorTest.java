package info.qinyu.price;

import info.qinyu.book.Book;
import info.qinyu.currency.CurrencyService;
import org.junit.Test;

import static org.junit.Assert.*;

public class BookPriceCalculatorTest {

    @Test
    public void should_calculate_book_price_in_usd() {
        // Given
        BookPriceCalculator calculator = new BookPriceCalculator(new CurrencyService() {
            @Override
            public double getExchangeForCurrency(String currency) throws IllegalArgumentException {
                return 0.15;
            }
        });
        // When
        BookPrice bookPrice = calculator.calculatePriceInCurrency(new Book("ABook", 10000), "usd");
        // Then
        assertEquals(bookPrice.getPrice(), "15.00");
        assertEquals(bookPrice.getCurrency(), "usd");
    }

}