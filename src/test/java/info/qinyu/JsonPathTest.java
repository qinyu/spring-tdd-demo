package info.qinyu;

import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import info.qinyu.book.Book;
import org.assertj.core.api.ListAssert;
import org.assertj.core.api.ThrowableAssert;
import org.assertj.core.util.Files;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.condition.DisabledIf;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.Assertions.fail;

@Disabled
public class JsonPathTest {

    private DocumentContext context;

    @BeforeEach
    void setUp() throws IOException {
        System.out.println("setup");
        context = JsonPath.parse(Paths.get("jsons", "currency.json").toFile());
    }

    @Test
    @Disabled
    public void jsonTest() throws IOException {

        String base = context.read("$.base");
        assertThat(base).isEqualTo("CNY");

        Map<String, Double> rates = context.read("$.rates");
        assertThat(rates).size().isEqualTo(31);

        Double usdRate = context.read("rates.USD");
        assertThat(usdRate).isEqualTo(0.15168);

//        assertThatThrownBy(() -> {
//
//        });

    }

    @Test
    @Disabled
//    @DisplayName("this is a test")
    public void jsonTest1() throws IOException {

        String base = context.read("$.base");
        assertThat(base).isEqualTo("CNY");

        Map<String, Double> rates = context.read("$.rates");
        assertThat(rates).size().isEqualTo(31);

        try {
            Double usdRate = context.read("rates.CNY");
//            fail();
        } catch (Exception e) {
            assertThat(e).isNotNull();
        }


        //assertThat(usdRate).isEqualTo(0.15168);



























    }
}
