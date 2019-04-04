package info.qinyu.legacy;

import info.qinyu.book.Book;
import info.qinyu.book.BookRepository;
import info.qinyu.price.BookPrice;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.math.RoundingMode;
import java.text.NumberFormat;
import java.util.Map;

import static com.jayway.jsonpath.JsonPath.read;

@RestController
public class BadSmellBookPriceController {

    public static final String CNY = "cny";

    private final BookRepository bookRepository;
    private final RateService rateService;

    public BadSmellBookPriceController(RateService rateService, BookRepository bookRepository) {
        this.rateService = rateService;
        this.bookRepository = bookRepository;
    }

    @GetMapping(path = "badSmellPrice")
    @ResponseStatus(HttpStatus.OK)
    public BookPrice queryBookPrice(@RequestParam Map<String, String> allRequestParams) {
        return getBookPriceInternal(allRequestParams);
    }

    BookPrice getBookPriceInternal(@RequestParam Map<String, String> allRequestParams) {
        // 1. 参数校验
        String bookName = allRequestParams.get("name");
        if (bookName == null) {
            throw new IllegalArgumentException("Must specify a book name !!!");
        }

        // 2. 参数默认值
        String currency = allRequestParams.get("currency");
        currency = currency == null ? CNY : currency;

        // 3. 查询数据库
        Book book = bookRepository.findByName(bookName);

        float priceInCny = book.getPriceInCent() / 100f;
        try {
            if (CNY.equalsIgnoreCase(currency)) {
                // 4.1  人民币不用计算直接返回价格
                return new BookPrice(book.getName(), formatPrice(priceInCny));
            } else {
                // 4.2  从第三方服务查询汇率计算价格
                double rate = rateService.queryRate(currency);
                return new BookPrice(book.getName(),
                        formatPrice(priceInCny * rate),
                        currency,
                        null);
            }
        } catch (Exception e) {
            // 4.3 查询不到汇率，返回人民币价格和错误
            return new BookPrice(book.getName(),
                    formatPrice(priceInCny),
                    CNY,
                    e.getMessage());
        }
    }

    String formatPrice(double price) {
        NumberFormat numberFormat = NumberFormat.getInstance();
        numberFormat.setRoundingMode(RoundingMode.CEILING);
        numberFormat.setMaximumFractionDigits(2);
        numberFormat.setMinimumFractionDigits(2);
        return String.format("%.2f", Double.valueOf(numberFormat.format(price)));
    }

}
