package info.qinyu.price;

import com.jayway.jsonpath.JsonPath;
import info.qinyu.book.Book;
import info.qinyu.book.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@RestController
public class BadSmellBookPriceController {

    @Autowired
    private BookRepository bookRepository;

    @GetMapping(path = "badSmellPrice")
    @ResponseStatus(HttpStatus.OK)
    public BookPrice queryBookPrice(@RequestParam Map<String, String> allRequestParams) {

        // 1. 参数校验
        String bookName = allRequestParams.get("name");
        if (bookName == null) {
            throw new IllegalArgumentException("Must specify a book name !!!");
        }

        // 2. 参数默认值
        String currency = allRequestParams.get("currency");
        currency = currency == null ? "cny" : currency;

        // 3. 查询数据库
        Book book = bookRepository.findByName(bookName);

        float priceInCny = book.getPriceInCent() / 100f;
        try {
            if ("cny".equalsIgnoreCase(currency)) {
                // 4.1  人民币不用计算直接返回价格
                return new BookPrice(book.getName(), String.format("%.02f", priceInCny));
            } else {
                // 4.2  从第三方服务查询汇率计算价格
                ResponseEntity<String> response
                        = new RestTemplate().getForEntity("https://api.fixer.io/latest?base=cny", String.class);
                double rate = JsonPath.read(response.getBody(), "$.rates." + currency.toUpperCase());
                return new BookPrice(book.getName(), String.format("%.02f", priceInCny * rate), currency, null);
            }
        } catch (Exception e) {
            // 4.3 查询不到汇率，返回人民币价格和错误
            return new BookPrice(book.getName(), String.format("%.02f", priceInCny), "cny", e.getMessage());
        }


    }

}
