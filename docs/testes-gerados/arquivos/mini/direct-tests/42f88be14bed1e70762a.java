/*
 * @test
 * @summary Test java.time.LocalDateTime.minusHours(long) method behavior
 * @run main LocalDateTimeWitupTest
 */

import java.time.LocalDateTime;
import java.time.Month;

public class LocalDateTimeWitupTest {
    public static void main(String[] args) {
        testMinusHoursZero();
        testMinusHoursPositive();
        testMinusHoursNegative();
        testMinusHoursOverflowDay();
    }

    private static void testMinusHoursZero() {
        LocalDateTime dt = LocalDateTime.of(2018, Month.JANUARY, 1, 12, 0);
        LocalDateTime result = dt.minusHours(0);
        if (!result.equals(dt)) {
            throw new AssertionError("minusHours(0) should return the same LocalDateTime");
        }
    }

    private static void testMinusHoursPositive() {
        LocalDateTime dt = LocalDateTime.of(2018, Month.JANUARY, 1, 12, 0);
        LocalDateTime expected = LocalDateTime.of(2018, Month.JANUARY, 1, 10, 0);
        LocalDateTime result = dt.minusHours(2);
        if (!result.equals(expected)) {
            throw new AssertionError("minusHours(2) should subtract 2 hours");
        }
    }

    private static void testMinusHoursNegative() {
        LocalDateTime dt = LocalDateTime.of(2018, Month.JANUARY, 1, 12, 0);
        LocalDateTime expected = LocalDateTime.of(2018, Month.JANUARY, 1, 14, 0);
        LocalDateTime result = dt.minusHours(-2);
        if (!result.equals(expected)) {
            throw new AssertionError("minusHours(-2) should add 2 hours");
        }
    }

    private static void testMinusHoursOverflowDay() {
        LocalDateTime dt = LocalDateTime.of(2018, Month.JANUARY, 1, 1, 0);
        LocalDateTime expected = LocalDateTime.of(2017, Month.DECEMBER, 31, 23, 0);
        LocalDateTime result = dt.minusHours(2);
        if (!result.equals(expected)) {
            throw new AssertionError("minusHours(2) should correctly overflow to previous day");
        }
    }
}
