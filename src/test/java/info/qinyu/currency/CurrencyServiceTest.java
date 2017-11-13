package info.qinyu.currency;


import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

import static java.nio.file.Files.readAllBytes;
import static java.nio.file.Paths.get;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.Assertions.offset;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withStatus;


public class CurrencyServiceTest {

    DefaultCurrencyService currencyService = new DefaultCurrencyService(new RestTemplate());
    private MockRestServiceServer mockRestServiceServer;

    @Before
    public void setUp() throws Exception {
        currencyService.url = "https://api.fixer.io";
        mockRestServiceServer = MockRestServiceServer.createServer(currencyService.getRestTemplate());
        mockRestServiceServer.expect(requestTo("https://api.fixer.io/latest?base=cny"))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.OK)
                        .body(readAllBytes(get("jsons", "currency.json"))));
    }

    @Test
    public void should_return_15168_when_query_exchange_rate_between_cny_and_usd() throws Exception {
        double exchangeForCurrency = currencyService.getExchangeForCurrency("usd");

        assertThat(exchangeForCurrency).isCloseTo(0.15168d, offset(0.000001d));
    }

    @Test
    public void should_return_19114_when_query_exchange_rate_between_cny_and_aud() throws Exception {
        double exchangeForCurrency = currencyService.getExchangeForCurrency("aud");

        assertThat(exchangeForCurrency).isCloseTo(0.19114d, offset(0.000001d));
    }

    @Test
    public void should_throw_illegal_param_exception_if_currency_is_unknown() throws Exception {
        assertThatThrownBy(() -> currencyService.getExchangeForCurrency("xxx")).hasMessageStartingWith("Can't find rate for currency ");
    }
}
