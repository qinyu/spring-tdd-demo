package info.qinyu.currency;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.cloud.contract.stubrunner.spring.AutoConfigureStubRunner;
import org.springframework.cloud.contract.stubrunner.spring.StubRunnerProperties;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.client.RestTemplate;

import static org.assertj.core.api.Assertions.*;

@AutoConfigureStubRunner(
        stubsMode = StubRunnerProperties.StubsMode.LOCAL,
        ids = "info.qinyu:spring-tdd-demo:+:9527"
)
@ExtendWith(SpringExtension.class)
class CurrencyServiceContractTest {

    DefaultCurrencyService currencyService = new DefaultCurrencyService(new RestTemplate());

    @BeforeEach
    public void setUp() throws Exception {
        currencyService.url = "http://localhost:9527";
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
        assertThatThrownBy(() -> currencyService.getExchangeForCurrency("xxx")).hasMessageStartingWith("Can't find rate for currency xxx");
    }

}