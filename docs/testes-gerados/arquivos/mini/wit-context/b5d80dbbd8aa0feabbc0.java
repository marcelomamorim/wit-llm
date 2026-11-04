/*
 * @test
 * @summary Deterministic tests for java.time.LocalDate.minusMonths(long)
 * @run main LocalDateWitupTest
 */

import java.time.LocalDate;
import java.time.Month;
import java.time.DateTimeException;

public class LocalDateWitupTest {
    public static void main(String[] args) {
        testMinusMonthsBasic();
        testMinusMonthsLongMinValue();
        testMinusMonthsOverflow();
    }

    private static void testMinusMonthsBasic() {
        LocalDate date = LocalDate.of(2018, Month.JANUARY, 15);
        LocalDate result = date.minusMonths(1);
        if (!result.equals(LocalDate.of(2017, Month.DECEMBER, 15))) {
            throw new AssertionError("Expected 2017-12-15 but got " + result);
        }
        // Test subtracting zero months returns same date
        LocalDate same = date.minusMonths(0);
        if (!same.equals(date)) {
            throw new AssertionError("Expected same date when subtracting zero months");
        }
        // Test subtracting negative months is equivalent to plusMonths
        LocalDate plus = date.minusMonths(-2);
        LocalDate expectedPlus = date.plusMonths(2);
        if (!plus.equals(expectedPlus)) {
            throw new AssertionError("minusMonths(-2) should equal plusMonths(2)");
        }
    }

    private static void testMinusMonthsLongMinValue() {
        LocalDate date = LocalDate.of(2000, Month.JANUARY, 31);
        // According to source, minusMonths(Long.MIN_VALUE) calls plusMonths(Long.MAX_VALUE).plusMonths(1)
        LocalDate result = date.minusMonths(Long.MIN_VALUE);
        LocalDate expected = date.plusMonths(Long.MAX_VALUE).plusMonths(1);
        if (!result.equals(expected)) {
            throw new AssertionError("minusMonths(Long.MIN_VALUE) did not produce expected result");
        }
    }

    private static void testMinusMonthsOverflow() {
        // Test that large values that cause overflow throw DateTimeException
        LocalDate date = LocalDate.of(999999999, Month.DECEMBER, 31);
        try {
            // This should cause overflow and throw DateTimeException
            date.minusMonths(Long.MAX_VALUE);
            throw new AssertionError("Expected DateTimeException for overflow not thrown");
        } catch (DateTimeException expected) {
            // Expected exception
        }

        LocalDate date2 = LocalDate.of(-999999999, Month.JANUARY, 1);
        try {
            date2.minusMonths(-Long.MAX_VALUE);
            throw new AssertionError("Expected DateTimeException for overflow not thrown");
        } catch (DateTimeException expected) {
            // Expected exception
        }
    }
}
