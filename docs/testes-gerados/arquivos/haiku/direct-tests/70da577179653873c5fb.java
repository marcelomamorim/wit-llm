/*
 * @test
 * @summary Verifies Year.atMonth(int) returns a YearMonth with the same year and the requested month, and rejects invalid months
 * @run main YearWitupTest
 */
import java.time.DateTimeException;
import java.time.Year;
import java.time.YearMonth;

public class YearWitupTest {
    public static void main(String[] args) {
        YearMonth ym = Year.of(2020).atMonth(2);
        check(ym.getYear() == 2020, "year of 2020.atMonth(2)");
        check(ym.getMonthValue() == 2, "month of 2020.atMonth(2)");

        ym = Year.of(1999).atMonth(12);
        check(ym.getYear() == 1999, "year of 1999.atMonth(12)");
        check(ym.getMonthValue() == 12, "month of 1999.atMonth(12)");

        ym = Year.of(1999).atMonth(1);
        check(ym.getYear() == 1999, "year of 1999.atMonth(1)");
        check(ym.getMonthValue() == 1, "month of 1999.atMonth(1)");

        ym = Year.of(-5).atMonth(1);
        check(ym.getYear() == -5, "negative year preserved");
        check(ym.getMonthValue() == 1, "month for negative year");

        expectDateTimeException(0);
        expectDateTimeException(13);
        expectDateTimeException(-1);
    }

    static void expectDateTimeException(int month) {
        boolean thrown = false;
        try {
            Year.of(2020).atMonth(month);
        } catch (DateTimeException e) {
            thrown = true;
        }
        check(thrown, "DateTimeException expected for month " + month);
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
