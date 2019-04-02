package info.qinyu.price;

import info.qinyu.book.Book;
import info.qinyu.book.BookRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static info.qinyu.price.TDDBookPriceService.CNY;
import static org.mockito.Mockito.*;

public class TDDBookPriceServiceTest {

    private BookRepository mock;
    private TDDCurrencyService mockCurrencyService;

    @BeforeEach
    void setUp() {
        mock = mock(BookRepository.class);
        when(mock.findByName("Kotlin")).thenReturn(new Book("Koltin", 8900));
        when(mock.findByName("Java")).thenReturn(new Book("Java", 10000));
        mockCurrencyService = mock(TDDCurrencyService.class);
        when(mockCurrencyService.queryCurrency("usd")).thenReturn(0.15168);
    }

    @Test
    void should_return_89_00_cny_if_kotlin_price_is_89_00_cny() {
        TDDBookPriceService service = new TDDBookPriceService(mock, mockCurrencyService);
        TDDBookPrice bookPrice = service.calculatePrice("Kotlin", CNY);

        Assertions.assertThat(bookPrice.getPrice()).isEqualTo("89.00");
        Assertions.assertThat(bookPrice.getName()).isEqualTo("Kotlin");
        Assertions.assertThat(bookPrice.getCurrency()).isEqualTo(CNY);

        verify(mock, only()).findByName("Kotlin");
    }

    @Test
    void should_return_100_00_cny_if_java_price_is_100_00_cny() {
        TDDBookPriceService service = new TDDBookPriceService(mock, mockCurrencyService);
        TDDBookPrice bookPrice = service.calculatePrice("Java", CNY);

        Assertions.assertThat(bookPrice.getPrice()).isEqualTo("100.00");
        Assertions.assertThat(bookPrice.getName()).isEqualTo("Java");
        Assertions.assertThat(bookPrice.getCurrency()).isEqualTo(CNY);

        verify(mock, only()).findByName("Java");
    }

    @Test
    void should_return_13_50_usd_if_kotlin_price_is_89_00_cny() {

        TDDBookPriceService service = new TDDBookPriceService(mock, mockCurrencyService);
        TDDBookPrice bookPrice = service.calculatePrice("Kotlin", "usd");

        Assertions.assertThat(bookPrice.getPrice()).isEqualTo("13.50");
        Assertions.assertThat(bookPrice.getName()).isEqualTo("Kotlin");
        Assertions.assertThat(bookPrice.getCurrency()).isEqualTo("usd");

        verify(mock, only()).findByName("Kotlin");
        verify(mockCurrencyService, only()).queryCurrency("usd");


    }
}
