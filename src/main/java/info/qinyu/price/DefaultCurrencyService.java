package info.qinyu.price;

import com.jayway.jsonpath.JsonPath;
import com.oracle.webservices.internal.api.message.PropertySet;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Map;


public class DefaultCurrencyService implements CurrencyService {

    RestTemplate restTemplate = new RestTemplate();

    @Value("${currency.url}")
    String url;

    public double getExchangeForCurrency(String currency) {
        ResponseEntity<String> response
                = restTemplate.getForEntity(url + "/latest?base=cny", String.class);
        return JsonPath.read(response.getBody(), "$.rates."+currency.toUpperCase());
    }
}
