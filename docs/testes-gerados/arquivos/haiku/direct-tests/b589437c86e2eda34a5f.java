/*
 * @test
 * @summary Verify YearMonth.format(DateTimeFormatter) output and null formatter handling
 * @run main YearMonthWitupTest
 */

import java.time.YearMonth;
import java.time.format.DateTimeFormatter;

public class YearMonthWitupTest {

    public static void main(String[] args) {
        YearMonth ym = YearMonth.of(2020, 5);

        check("2020-05", ym.format(DateTimeFormatter.ISO_YEAR_MONTH));
        check("05/2020", ym.format(DateTimeFormatter.ofPattern("MM/uuuu")));
        check("2020-05", ym.format(DateTimeFormatter.ofPattern("uuuu-MM")));
        check("+12345-05", YearMonth.of(12345, 5).format(DateTimeFormatter.ISO_YEAR_MONTH));

        DateTimeFormatter f = DateTimeFormatter.ofPattern("MM/uuuu");
        check(f.format(ym), ym.format(f));

        try {
            ym.format(null);
        } catch (NullPointerException expected) {
            return;
        }
        throw new RuntimeException("NullPointerException expected for null formatter");
    }

    private static void check(String expected, String actual) {
        if (!expected.equals(actual)) {
            throw new RuntimeException("Expected " + expected + " but was " + actual);
        }
    }
}
