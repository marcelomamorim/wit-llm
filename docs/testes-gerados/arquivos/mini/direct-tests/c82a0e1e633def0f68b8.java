/*
 * @test
 * @summary Test java.time.LocalDateTime.minusMinutes(long) method
 * @run main LocalDateTimeWitupTest
 */

import java.time.LocalDateTime;
import java.time.Month;

public class LocalDateTimeWitupTest {
    public static void main(String[] args) {
        testMinusMinutesPositive();
        testMinusMinutesNegative();
        testMinusMinutesZero();
        testMinusMinutesOverflow();
    }

    private static void testMinusMinutesPositive() {
        LocalDateTime dt = LocalDateTime.of(2018, Month.JANUARY, 1, 0, 10);
        LocalDateTime result = dt.minusMinutes(5);
        if (result.getMinute() != 5 || result.getHour() != 0 || result.getDayOfMonth() != 1) {
            throw new AssertionError("Failed testMinusMinutesPositive: expected minute=5, hour=0, day=1 but got " + result);
        }
    }

    private static void testMinusMinutesNegative() {
        LocalDateTime dt = LocalDateTime.of(2018, Month.JANUARY, 1, 0, 10);
        LocalDateTime result = dt.minusMinutes(-5);
        if (result.getMinute() != 15 || result.getHour() != 0 || result.getDayOfMonth() != 1) {
            throw new AssertionError("Failed testMinusMinutesNegative: expected minute=15, hour=0, day=1 but got " + result);
        }
    }

    private static void testMinusMinutesZero() {
        LocalDateTime dt = LocalDateTime.of(2018, Month.JANUARY, 1, 12, 30);
        LocalDateTime result = dt.minusMinutes(0);
        if (!result.equals(dt)) {
            throw new AssertionError("Failed testMinusMinutesZero: expected same LocalDateTime instance or equal value");
        }
    }

    private static void testMinusMinutesOverflow() {
        LocalDateTime dt = LocalDateTime.of(2018, Month.JANUARY, 1, 0, 0);
        LocalDateTime result = dt.minusMinutes(1);
        if (result.getMinute() != 59 || result.getHour() != 23 || result.getDayOfMonth() != 31 || result.getMonth() != Month.DECEMBER || result.getYear() != 2017) {
            throw new AssertionError("Failed testMinusMinutesOverflow: expected 2017-12-31T23:59 but got " + result);
        }
    }
}
