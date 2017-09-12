package info.qinyu.currency;

import org.springframework.stereotype.Component;

@Component
public interface CurrencyService {
    double getExchangeForCurrency(String currency);
}
