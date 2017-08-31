package info.qinyu;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.hamcrest.core.Is.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(BookPriceController.class)
public class BookPriceControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    public void should_show_89_00_if_query_cny_price_of_kotlin_in_action() throws Exception {
        ResultActions resultActions = mockMvc.perform(
                get("/price").param("name", "Kotlin实战"));

        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("price", is("89.00")));
    }
}
