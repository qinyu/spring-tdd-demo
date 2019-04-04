package info.qinyu.legacy;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import static com.jayway.jsonpath.JsonPath.read;


@Service
class FixerIORateService implements RateService {
    @Override
    public double queryRate(String currency) {
        ResponseEntity<String> response
                = new RestTemplate()
                .getForEntity("https://api.fixer.io/latest?base=cny",
                        String.class);
        return (double) read(response.getBody(),
                "$.rates." + currency.toUpperCase());
    }
}
