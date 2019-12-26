package info.qinyu.currency;

import com.jayway.jsonpath.JsonPath;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import static java.lang.String.format;

@Component
public class DefaultCurrencyService implements CurrencyService {

    private final RestTemplate restTemplate;

    public DefaultCurrencyService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Value("${currency.url}")
    String url;

    public double getExchangeForCurrency(String currency) throws IllegalArgumentException{
        try {
            ResponseEntity<String> response
                    = restTemplate.getForEntity(url + "/latest?base=cny", String.class);
            return JsonPath.read(response.getBody(), "$.rates." + currency.toUpperCase());
        } catch (Exception e) {
            throw new IllegalArgumentException("Can't find rate for currency "+ currency, e);
        }
    }
}
