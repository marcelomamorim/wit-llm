/*
 * @test
 * @summary Test Year.atMonth(Month) method for correct behavior.
 * @run main YearWitupTest
 */

import java.time.Year;
import java.time.Month;
import java.time.YearMonth;

public class YearWitupTest {

    public static void main(String[] args) {
        testAtMonthWithValidMonth();
        testAtMonthWithJanuary();
        testAtMonthWithDecember();
    }

    static void testAtMonthWithValidMonth() {
        Year year = Year.of(2023);
        Month month = Month.APRIL;
        YearMonth result = year.atMonth(month);
        assert result.getYear() == 2023 : "Expected year 2023";
        assert result.getMonth() == Month.APRIL : "Expected month April";
    }

    static void testAtMonthWithJanuary() {
        Year year = Year.of(1999);
        Month month = Month.JANUARY;
        YearMonth result = year.atMonth(month);
        assert result.getYear() == 1999 : "Expected year 1999";
        assert result.getMonth() == Month.JANUARY : "Expected month January";
    }

    static void testAtMonthWithDecember() {
        Year year = Year.of(2000);
        Month month = Month.DECEMBER;
        YearMonth result = year.atMonth(month);
        assert result.getYear() == 2000 : "Expected year 2000";
        assert result.getMonth() == Month.DECEMBER : "Expected month December";
    }
}
