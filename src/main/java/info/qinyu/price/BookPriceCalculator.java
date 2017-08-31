package info.qinyu.price;


import javax.validation.constraints.NotNull;

public class BookPriceCalculator {

    CurrencyService currencyService;

    public BookPriceCalculator(CurrencyService currencyService) {
        this.currencyService = currencyService;
    }

    BookPrice calculatePrice(Book book) {
        String price = String.format("%.02f", book.getPriceInCent() / 100f);
        return new BookPrice(book.getName(), price);
    }

    public BookPrice calculatePriceInCurrency(Book book, @NotNull String currency) {
        float priceInCny = book.getPriceInCent() / 100f;
        double priceD = "cny".equalsIgnoreCase(currency) ? priceInCny : priceInCny * getExchangeForCurrency(currency);
        return new BookPrice(book.getName(), String.format("%.02f", priceD));
    }

    double getExchangeForCurrency(String currency) {
        return currencyService.getExchangeForCurrency(currency);
    }
}