package info.qinyu.price;

import com.jayway.jsonpath.JsonPath;
import info.qinyu.book.Book;
import info.qinyu.book.BookRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@RestController
public class LegacyBookPriceController {

    public static final String NAME = "name";
    private RestTemplate restTemplate;

    private BookRepository bookRepository;

    public LegacyBookPriceController(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public LegacyBookPriceController() {

    }

    @GetMapping(path = "legacyPrice")
    @ResponseStatus(HttpStatus.OK)
    public BookPrice queryBookPrice(@RequestParam Map<String, String> allRequestParams) {

        // 1. 参数校验
        String name = getBookNameOrThrowException(allRequestParams);

        // 2. 参数默认值
        String currency1 = getCurrency(allRequestParams);

        // 3. 查询数据库
        return calculateBookPrice(name, currency1);
    }

    BookPrice calculateBookPrice(String bookName, String currency) {
        Book book = bookRepository.findByName(bookName);

        float priceInCny = book.getPriceInCent() / 100f;
        try {
            if ("cny".equalsIgnoreCase(currency)) {
                // 4.1  人民币不用计算直接返回价格
                return new BookPrice(book.getName(), String.format("%.02f", priceInCny));
            } else {
                double rate = queryRateForCurrency(currency);
                return new BookPrice(book.getName(), String.format("%.02f", priceInCny * rate), currency, null);
            }
        } catch (Exception e) {
            // 4.3 查询不到汇率，返回人民币价格和错误
            return new BookPrice(book.getName(), String.format("%.02f", priceInCny), "cny", e.getMessage());
        }
    }

    protected double queryRateForCurrency(String currency) {
        // 4.2  从第三方服务查询汇率计算价格
        ResponseEntity<String> response
                = restTemplate.getForEntity("https://api.fixer.io/latest?base=cny", String.class);
        return JsonPath.read(response.getBody(), "$.rates." + currency.toUpperCase());
    }

    String getCurrency(Map<String, String> paramsMap) {
        String currency = paramsMap.get("currency");
        return currency == null ? "cny" : currency;
    }

    String getBookNameOrThrowException(Map<String, String> paramsMap) {
        String bookName = paramsMap.get(NAME);
        if (bookName == null) {
            throw new IllegalArgumentException("Must specify a book name !!!");
        }
        return bookName;
    }

}
