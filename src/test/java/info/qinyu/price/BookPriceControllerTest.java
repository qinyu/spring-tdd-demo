package info.qinyu.price;

import info.qinyu.book.Book;
import info.qinyu.book.BookRepository;
import info.qinyu.currency.CurrencyService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.hamcrest.core.Is.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(BookPriceController.class)
public class BookPriceControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    BookRepository bookRepository;

    @MockBean
    CurrencyService currencyService;

    @SpyBean
    BookPriceCalculator bookPriceCalculator;

    @Test
    public void should_show_89_00_if_query_cny_price_of_kotlin_in_action() throws Exception {
        String bookName = "Kotlin实战";
        given(bookRepository.findByName(bookName)).willReturn(new Book(bookName, 8900));

        ResultActions resultActions = mockMvc.perform(
                get("/price").param("name", bookName));

        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("price", is("89.00")))
                .andExpect(jsonPath("currency", is("cny")))
                .andExpect(jsonPath("name", is(bookName)));
    }

    @Test
    public void should_show_13_50_if_query_usd_price_of_kotlin_in_action() throws Exception {
        String bookName = "Kotlin实战";
        given(bookRepository.findByName(bookName)).willReturn(new Book(bookName, 8900));
        given(currencyService.getExchangeForCurrency("usd")).willReturn(0.15168d);

        ResultActions resultActions = mockMvc.perform(
                get("/price").param("name", bookName).param("currency", "usd"));

        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("price", is("13.50")))
                .andExpect(jsonPath("currency", is("usd")))
                .andExpect(jsonPath("name", is(bookName)));
    }
}
