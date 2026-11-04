/*
 * @test
 * @summary Test for LocalDateTime.minusHours(long)
 * @run main LocalDateTimeWitupTest
 */

import java.time.LocalDateTime;
import java.time.Month;

public class LocalDateTimeWitupTest {

    public static void main(String[] args) {
        testMinusHoursZero();
        testMinusHoursPositive();
        testMinusHoursNegative();
        testMinusHoursLarge();
    }

    static void testMinusHoursZero() {
        LocalDateTime dt = LocalDateTime.of(2023, Month.MARCH, 15, 10, 30);
        LocalDateTime result = dt.minusHours(0);
        if (!result.equals(dt)) {
            throw new AssertionError("minusHours(0) should return the same LocalDateTime");
        }
    }

    static void testMinusHoursPositive() {
        LocalDateTime dt = LocalDateTime.of(2023, Month.MARCH, 15, 10, 30);
        LocalDateTime result = dt.minusHours(5);
        LocalDateTime expected = LocalDateTime.of(2023, Month.MARCH, 15, 5, 30);
        if (!result.equals(expected)) {
            throw new AssertionError("minusHours(5) did not produce expected result");
        }
    }

    static void testMinusHoursNegative() {
        LocalDateTime dt = LocalDateTime.of(2023, Month.MARCH, 15, 10, 30);
        LocalDateTime result = dt.minusHours(-3);
        LocalDateTime expected = LocalDateTime.of(2023, Month.MARCH, 15, 13, 30);
        if (!result.equals(expected)) {
            throw new AssertionError("minusHours(-3) did not produce expected result");
        }
    }

    static void testMinusHoursLarge() {
        LocalDateTime dt = LocalDateTime.of(2023, Month.MARCH, 15, 10, 30);
        long largeHours = 1000000L;
        LocalDateTime result = dt.minusHours(largeHours);
        LocalDateTime expected = dt.plusHours(-largeHours);
        if (!result.equals(expected)) {
            throw new AssertionError("minusHours(large) did not produce expected result");
        }
    }
}
