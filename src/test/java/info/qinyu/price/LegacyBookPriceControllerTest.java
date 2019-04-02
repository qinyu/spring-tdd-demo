package info.qinyu.price;

import info.qinyu.book.Book;
import info.qinyu.book.BookRepository;
import org.assertj.core.api.Assertions;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.verification.VerificationMode;
import org.springframework.web.client.RestClientException;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.client.ExpectedCount.once;

class LegacyBookPriceControllerTest {

    private LegacyBookPriceController controller;
    private HashMap<String, String> params;

    @BeforeEach
    void setUp() {
        controller = new LegacyBookPriceController();
        params = new HashMap<>();
    }


    @Test
    void should_return_book_name_if_exist_param_name() {
        params.put(LegacyBookPriceController.NAME, "ABook");

        String bookName = controller.getBookNameOrThrowException(params);

        assertThat(bookName).isEqualTo("ABook");
    }

    @Test
    void should_throw_exception_if_no_name_specified() {
        assertThatThrownBy(() -> controller.getBookNameOrThrowException(params))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Must specify a book name !!!");
    }

    @Test
    void should_return_currency_if_exist_param() {
        params.put("currency", "usd");

        assertThat(controller.getCurrency(params)).isEqualTo("usd");
    }

    @Test
    void should_return_currency_cny_if_param_not_exist() {


        assertThat(controller.getCurrency(params)).isEqualTo("cny");
    }

    @Test
    void should_return_currency_cny_if_param_is_cny() {
        params.put("currency", "cny");

        assertThat(controller.getCurrency(params)).isEqualTo("cny");
    }

    @Test
    void name() {
        BookRepository bookRepository = mock(BookRepository.class);
        when(bookRepository.findByName("ABook")).thenReturn(new Book("ABook", 10000));
        LegacyBookPriceController bookPriceController = new LegacyBookPriceController(bookRepository) {
            @Override
            protected double queryRateForCurrency(String currency) {
                return 0.5;
            }
        };

        BookPrice bookPrice = bookPriceController.calculateBookPrice("ABook", "usd");

        SoftAssertions assertions = new SoftAssertions();

        assertions.assertThat(bookPrice.getName()).isEqualTo("ABook");
        assertions.assertThat(bookPrice.getCurrency()).isEqualTo("usd");
        assertions.assertThat(bookPrice.getPrice()).isEqualTo("50.00");
        assertions.assertThat(bookPrice.getError()).isNull();

        assertions.assertAll();
    }


    @Test
    void name1() {
        BookRepository mockBookRepository = mock(BookRepository.class);
        when(mockBookRepository.findByName("ABook")).thenReturn(new Book("ABook", 10000));
        LegacyBookPriceController bookPriceController = new LegacyBookPriceController(mockBookRepository) {
            @Override
            protected double queryRateForCurrency(String currency) {
                return 0.5;
            }
        };

        BookPrice bookPrice = bookPriceController.calculateBookPrice("ABook", "cny");

        verify(mockBookRepository, only()).findByName(anyString());

        SoftAssertions assertions = new SoftAssertions();

        assertions.assertThat(bookPrice.getName()).isEqualTo("ABook");
        assertions.assertThat(bookPrice.getCurrency()).isEqualTo("cny");
        assertions.assertThat(bookPrice.getPrice()).isEqualTo("100.00");
        assertions.assertThat(bookPrice.getError()).isNull();

        assertions.assertAll();
    }


    @Test
    void name2() {
        BookRepository bookRepository = mock(BookRepository.class);
        when(bookRepository.findByName("ABook")).thenReturn(new Book("ABook", 10000));
        LegacyBookPriceController bookPriceController = new LegacyBookPriceController(bookRepository) {
            @Override
            protected double queryRateForCurrency(String currency) {
                throw new RestClientException("Can't find currency " + currency);
            }
        };

        BookPrice bookPrice = bookPriceController.calculateBookPrice("ABook", "xxx");

        SoftAssertions assertions = new SoftAssertions();

        assertions.assertThat(bookPrice.getName()).isEqualTo("ABook");
        assertions.assertThat(bookPrice.getCurrency()).isEqualTo("cny");
        assertions.assertThat(bookPrice.getPrice()).isEqualTo("100.00");
        assertions.assertThat(bookPrice.getError()).isEqualTo("Can't find currency xxx");

        assertions.assertAll();
    }
}