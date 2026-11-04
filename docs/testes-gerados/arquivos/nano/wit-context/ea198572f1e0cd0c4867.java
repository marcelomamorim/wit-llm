/*
 * @test
 * @summary Test for YearMonth.withMonth(int)
 * @run main YearMonthWitupTest
 */

import java.time.YearMonth;
import static java.time.temporal.ChronoField.MONTH_OF_YEAR;

public class YearMonthWitupTest {

    public static void main(String[] args) {
        testWithMonthValid();
        testWithMonthInvalidBelow();
        testWithMonthInvalidAbove();
    }

    static void testWithMonthValid() {
        YearMonth ym = YearMonth.of(2023, 5);
        YearMonth result = ym.withMonth(12);
        if (result.getMonthValue() != 12 || result.getYear() != 2023) {
            throw new AssertionError("Failed valid withMonth: expected month=12, year=2023, got month=" + result.getMonthValue() + ", year=" + result.getYear());
        }
    }

    static void testWithMonthInvalidBelow() {
        try {
            YearMonth.of(2023, 1).withMonth(0);
            throw new AssertionError("Expected DateTimeException for month=0");
        } catch (java.time.DateTimeException e) {
            // expected
        }
    }

    static void testWithMonthInvalidAbove() {
        try {
            YearMonth.of(2023, 1).withMonth(13);
            throw new AssertionError("Expected DateTimeException for month=13");
        } catch (java.time.DateTimeException e) {
            // expected
        }
    }
}
