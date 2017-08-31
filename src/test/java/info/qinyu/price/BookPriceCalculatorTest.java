package info.qinyu.price;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.closeTo;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

public class BookPriceCalculatorTest {

    private BookPriceCalculator calculator;

    @Before
    public void setUp() throws Exception {
        CurrencyService service = mock(CurrencyService.class);
        calculator = new BookPriceCalculator(service);
    }

    @Test
    public void should_directly_convert_price_queried_from_repository() throws Exception {
        BookPrice price = calculator.calculatePrice(new Book("A name", 8800));
        assertThat(price.getPrice(), is("88.00"));
    }


    @Test
    public void should_calculate_price_with_exchange_rate_provide_by_currency_service() throws Exception {
        given(calculator.getExchangeForCurrency("usd")).willReturn(0.15168d);

        BookPrice price = calculator.calculatePriceInCurrency(new Book("A name", 8900), "usd");
        assertThat(price.getPrice(), is("13.50"));
    }

//    @Test
//    public void should_return_15168_when_query_exchange_rate_between_cny_and_usd() throws Exception {
//        double exchangeForCurrency = calculator.getExchangeForCurrency("usd");
//
//        assertThat(exchangeForCurrency, closeTo(0.15168d, 0.000001d));
//    }
//
//    @Test
//    public void should_return_19114_when_query_exchange_rate_between_cny_and_aud() throws Exception {
//        double exchangeForCurrency = calculator.getExchangeForCurrency("aud");
//
//        assertThat(exchangeForCurrency, closeTo(0.19114d, 0.000001d));
//    }
}