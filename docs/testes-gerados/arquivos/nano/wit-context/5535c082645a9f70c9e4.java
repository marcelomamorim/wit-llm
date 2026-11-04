/*
 * @test
 * @summary Teste do método plusMonths de java.time.YearMonth
 * @run main YearMonthWitupTest
 */

import java.time.YearMonth;
import static java.time.temporal.ChronoField.YEAR;
import static java.time.temporal.ChronoField.MONTH_OF_YEAR;

public class YearMonthWitupTest {

    public static void main(String[] args) {
        testPlusMonthsZero();
        testPlusMonthsPositive();
        testPlusMonthsNegative();
        testPlusMonthsOverflow();
        testPlusMonthsUnderflow();
    }

    static void testPlusMonthsZero() {
        YearMonth ym = YearMonth.of(2023, 3);
        YearMonth result = ym.plusMonths(0);
        if (!result.equals(ym)) {
            throw new AssertionError("plusMonths(0) should return the same YearMonth instance");
        }
    }

    static void testPlusMonthsPositive() {
        YearMonth ym = YearMonth.of(2023, 1);
        YearMonth result = ym.plusMonths(5);
        YearMonth expected = YearMonth.of(2023, 6);
        if (!result.equals(expected)) {
            throw new AssertionError("plusMonths(5) did not produce expected YearMonth");
        }
    }

    static void testPlusMonthsNegative() {
        YearMonth ym = YearMonth.of(2023, 6);
        YearMonth result = ym.plusMonths(-3);
        YearMonth expected = YearMonth.of(2023, 3);
        if (!result.equals(expected)) {
            throw new AssertionError("plusMonths(-3) did not produce expected YearMonth");
        }
    }

    static void testPlusMonthsOverflow() {
        try {
            YearMonth ym = YearMonth.of(9999, 12);
            ym.plusMonths(Long.MAX_VALUE);
            throw new AssertionError("Expected DateTimeException for overflow");
        } catch (java.time.DateTimeException e) {
            // expected
        }
    }

    static void testPlusMonthsUnderflow() {
        try {
            YearMonth ym = YearMonth.of(0, 1);
            ym.plusMonths(Long.MIN_VALUE);
            throw new AssertionError("Expected DateTimeException for underflow");
        } catch (java.time.DateTimeException e) {
            // expected
        }
    }
}
