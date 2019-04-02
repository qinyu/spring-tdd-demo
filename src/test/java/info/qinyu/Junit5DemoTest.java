package info.qinyu;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.math.RoundingMode;
import java.text.NumberFormat;


@DisplayName("These are some examples of JUnit5 feature")
public class Junit5DemoTest {


    @Test
    void test() {
        NumberFormat format = NumberFormat.getInstance();
        format.setMaximumFractionDigits(2);
        format.setRoundingMode(RoundingMode.CEILING);
        System.out.println(format.format(1.2321312d));
    }


}
