package info.qinyu.price;

import info.qinyu.book.Book;
import info.qinyu.currency.CurrencyService;
import org.hibernate.annotations.Parameter;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.aggregator.ArgumentsAccessor;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(value = MockitoExtension.class)
public class BookPriceCalculatorTest {

    @Mock
    private CurrencyService mock;

    @Captor
    private ArgumentCaptor<List<String>> listArgumentCaptor;

    @Test
    public void should_calculate_book_price_in_usd() {
        // Given
        when(mock.getExchangeForCurrency("eur"))
                .thenReturn(0.15, 0.16);

        BookPriceCalculator calculator = new BookPriceCalculator(mock);
        // When
        BookPrice bookPrice = calculator.calculatePriceInCurrency(new Book("ABook", 10000), "eur");
        // Then
        assertThat(bookPrice)
                .hasFieldOrPropertyWithValue("price", "15.00")
                .hasFieldOrPropertyWithValue("currency", "eur");


        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
        verify(mock, atLeast(1)).getExchangeForCurrency(captor.capture());
        assertThat(captor.getValue()).isEqualTo("eur");


//        soft.assertThat("1").containsPattern("[0-9]+");
//
//        soft.assertThatThrownBy(new ThrowableAssert.ThrowingCallable() {
//            @Override
//            public void call() throws Throwable {
//                throw new Exception();
//            }
//        }).hasNoCause().hasMessage(null);
//
//        soft.assertAll();
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//        Map<String, Book> aMap = new HashMap<>();
//        Map<String, Book> bMap = new HashMap<>();
//        aMap.put("Key", new Book("Foo", 100));
//        bMap.put("Key", new Book("Bar", 200));
//
//        List<Map<String,Book>> aList = Arrays.asList(aMap, bMap);
//
//        assertThat(aList).
//                extracting("Key").
//                extracting("name", "priceInCent").
//                containsExactly(tuple("Foo", 100),
//                        tuple("Bar1", 200));
//

    }

    @ParameterizedTest
    @CsvFileSource(resources = {"test.csv"}, numLinesToSkip = 1)
    public void example(ArgumentsAccessor arguments) {
        assertThat(arguments.getInteger(0)).isEqualTo(100);
    }

    @ParameterizedTest
    @CsvFileSource(resources = {"test.csv"}, numLinesToSkip = 1)
    public void example1(String price, String currency, double rate, double finalPrice) {
//        assertThat(arguments.getInteger(0)).isEqualTo(100);
    }

}