package info.qinyu.legacy;

import info.qinyu.book.Book;
import info.qinyu.book.BookRepository;
import info.qinyu.legacy.BadSmellBookPriceController;
import info.qinyu.legacy.RateService;
import info.qinyu.price.BookPrice;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class BadSmellBookPriceControllerTest {

    @Test
    void should_return_price_in_specific_currency() {
        // Given
        BookRepository repository = mock(BookRepository.class);
        given(repository.findByName("abook")).willReturn(new Book("abook", 10000));
        BadSmellBookPriceController controller = new BadSmellBookPriceController(new RateService() {
            @Override
            public double queryRate(String currency) {
                return 0.15d;

            }
        }, repository);

        // When
        HashMap<String, String> allRequestParams = new HashMap<>();
        allRequestParams.put("name", "abook");
        allRequestParams.put("currency", "usd");
        BookPrice bookPrice = controller.getBookPriceInternal(allRequestParams);

        // Then
        assertThat(bookPrice.getName()).isEqualToIgnoringCase("abook");
        assertThat(bookPrice.getPrice()).isEqualToIgnoringCase("15.00");
    }

    @Test
    void should_keep_2_fraction_digits_and_ceiling() {
        // Given
        BadSmellBookPriceController controller = new BadSmellBookPriceController(null,null);

        // Then
        assertThat(controller.formatPrice(1.005)).isEqualTo("1.01");
        assertThat(controller.formatPrice(1.004)).isEqualTo("1.01");
        assertThat(controller.formatPrice(1.002)).isEqualTo("1.01");
        assertThat(controller.formatPrice(1.009)).isEqualTo("1.01");
        assertThat(controller.formatPrice(1)).isEqualTo("1.00");
    }
}