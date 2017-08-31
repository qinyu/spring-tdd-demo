package info.qinyu.price;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class BookPriceCalculatorTest {

    private BookPriceCalculator calculator;

    @Before
    public void setUp() throws Exception {
        calculator = new BookPriceCalculator();
    }

    @Test
    public void should_directly_convert_price_queried_from_repository() throws Exception {
        BookPrice price = calculator.calculatePrice(new Book("A name", 8800));
        assertThat(price.getPrice(), is("88.00"));
    }
}