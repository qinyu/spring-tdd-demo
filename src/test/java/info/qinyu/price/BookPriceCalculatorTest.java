package info.qinyu.price;

import info.qinyu.book.Book;
import info.qinyu.currency.CurrencyService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;

public class BookPriceCalculatorTest {

    private BookPriceCalculator calculator;
    private Book.BookBuilder builder;
    private CurrencyService service;

    @BeforeEach
    public void setUp() throws Exception {
        service = mock(CurrencyService.class);
        calculator = spy(new BookPriceCalculator(service));
        builder = Book.builder().id(0).name("A name");
    }

    @Test
    public void should_directly_convert_price_queried_from_repository() throws Exception {
        BookPrice price = calculator.calculatePrice(builder.priceInCent(8800).build());
        assertThat(price.getPrice()).isEqualTo("88.00");
        verify(service, never()).getExchangeForCurrency(anyString());
    }

    @Test
    public void should_calculate_price_with_exchange_rate_provide_by_currency_service() throws Exception {
        given(service.getExchangeForCurrency("usd")).willReturn(0.15168d);

        BookPrice price = calculator.
            calculatePriceInCurrency(builder.priceInCent(8900).build(), "usd");
        assertThat(price.getPrice()).isEqualTo("13.50");
    }

    @Test
    public void should_throw_illegal_arguments_exception_if_currency_is_unknown() throws Exception {
        given(service.getExchangeForCurrency("xxx")).willThrow(new IllegalArgumentException("Can't find rate for currency"));

        assertThatThrownBy(() -> calculator.calculatePriceInCurrency(builder.priceInCent(8900).build(), "xxx")).isInstanceOfAny(IllegalArgumentException.class);
    }
}