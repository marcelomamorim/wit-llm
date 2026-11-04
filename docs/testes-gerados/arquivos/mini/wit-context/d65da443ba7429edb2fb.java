/*
 * @test
 * @summary Deterministic tests for java.time.LocalDateTime.minusMonths(long)
 * @run main LocalDateTimeWitupTest
 */

import java.time.LocalDateTime;
import java.time.Month;
import java.time.DateTimeException;

public class LocalDateTimeWitupTest {
    public static void main(String[] args) {
        testMinusMonthsNormal();
        testMinusMonthsLongMinValue();
    }

    private static void testMinusMonthsNormal() {
        LocalDateTime dt = LocalDateTime.of(2018, Month.JANUARY, 15, 10, 30);
        LocalDateTime result = dt.minusMonths(1);
        LocalDateTime expected = dt.plusMonths(-1);
        if (!result.equals(expected)) {
            throw new AssertionError("minusMonths(1) did not match plusMonths(-1): " + result + " vs " + expected);
        }

        result = dt.minusMonths(0);
        expected = dt.plusMonths(0);
        if (!result.equals(expected)) {
            throw new AssertionError("minusMonths(0) did not match plusMonths(0): " + result + " vs " + expected);
        }

        result = dt.minusMonths(-5);
        expected = dt.plusMonths(5);
        if (!result.equals(expected)) {
            throw new AssertionError("minusMonths(-5) did not match plusMonths(5): " + result + " vs " + expected);
        }
    }

    private static void testMinusMonthsLongMinValue() {
        LocalDateTime dt = LocalDateTime.of(2000, Month.JUNE, 30, 12, 0);
        // According to source, minusMonths(Long.MIN_VALUE) == plusMonths(Long.MAX_VALUE).plusMonths(1)
        LocalDateTime result = dt.minusMonths(Long.MIN_VALUE);
        LocalDateTime expected = dt.plusMonths(Long.MAX_VALUE).plusMonths(1);
        if (!result.equals(expected)) {
            throw new AssertionError("minusMonths(Long.MIN_VALUE) did not match plusMonths(Long.MAX_VALUE).plusMonths(1): " + result + " vs " + expected);
        }
    }
}
