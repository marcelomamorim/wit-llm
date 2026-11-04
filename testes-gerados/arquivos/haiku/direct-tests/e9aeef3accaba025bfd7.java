/*
 * @test
 * @summary Witup-style tests for Year.atMonth(Month)
 * @run main YearWitupTest
 */
import java.time.Month;
import java.time.Year;
import java.time.YearMonth;

public class YearWitupTest {
    public static void main(String[] args) {
        YearMonth ym = Year.of(2020).atMonth(Month.FEBRUARY);
        check(ym.getYear() == 2020, "year of atMonth result");
        check(ym.getMonth() == Month.FEBRUARY, "month of atMonth result");
        check(ym.lengthOfMonth() == 29, "leap year February length");
        check(Year.of(1900).atMonth(Month.FEBRUARY).lengthOfMonth() == 28, "century non-leap February length");
        check(Year.of(2000).atMonth(Month.FEBRUARY).lengthOfMonth() == 29, "400-year leap February length");
        check(Year.of(2021).atMonth(Month.DECEMBER).getMonthValue() == 12, "December month value");
        check(Year.of(-5).atMonth(Month.MARCH).getYear() == -5, "negative year preserved");
        boolean npe = false;
        try {
            Year.of(2020).atMonth((Month) null);
        } catch (NullPointerException e) {
            npe = true;
        }
        check(npe, "null month must throw NullPointerException");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new AssertionError(message);
        }
    }
}
