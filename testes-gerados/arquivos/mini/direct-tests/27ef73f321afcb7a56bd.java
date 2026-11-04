/*
 * @test
 * @summary Test java.time.LocalDateTime plusMinutes method behavior
 * @run main LocalDateTimeWitupTest
 */

import java.time.LocalDateTime;
import java.time.Month;

public class LocalDateTimeWitupTest {
    public static void main(String[] args) {
        testPlusMinutesPositive();
        testPlusMinutesNegative();
        testPlusMinutesOverflowDay();
        testPlusMinutesZero();
    }

    private static void testPlusMinutesPositive() {
        LocalDateTime dt = LocalDateTime.of(2018, Month.JANUARY, 1, 10, 30);
        LocalDateTime result = dt.plusMinutes(90);
        // 10:30 + 90 minutes = 12:00 same day
        if (result.getYear() != 2018 || result.getMonth() != Month.JANUARY || result.getDayOfMonth() != 1 ||
            result.getHour() != 12 || result.getMinute() != 0) {
            throw new AssertionError("testPlusMinutesPositive failed: " + result);
        }
    }

    private static void testPlusMinutesNegative() {
        LocalDateTime dt = LocalDateTime.of(2018, Month.JANUARY, 1, 10, 30);
        LocalDateTime result = dt.plusMinutes(-45);
        // 10:30 - 45 minutes = 9:45 same day
        if (result.getYear() != 2018 || result.getMonth() != Month.JANUARY || result.getDayOfMonth() != 1 ||
            result.getHour() != 9 || result.getMinute() != 45) {
            throw new AssertionError("testPlusMinutesNegative failed: " + result);
        }
    }

    private static void testPlusMinutesOverflowDay() {
        LocalDateTime dt = LocalDateTime.of(2018, Month.JANUARY, 1, 23, 30);
        LocalDateTime result = dt.plusMinutes(90);
        // 23:30 + 90 minutes = 01:00 next day (2018-01-02)
        if (result.getYear() != 2018 || result.getMonth() != Month.JANUARY || result.getDayOfMonth() != 2 ||
            result.getHour() != 1 || result.getMinute() != 0) {
            throw new AssertionError("testPlusMinutesOverflowDay failed: " + result);
        }
    }

    private static void testPlusMinutesZero() {
        LocalDateTime dt = LocalDateTime.of(2018, Month.JANUARY, 1, 10, 30);
        LocalDateTime result = dt.plusMinutes(0);
        // Adding zero minutes should return the same date-time
        if (!result.equals(dt)) {
            throw new AssertionError("testPlusMinutesZero failed: " + result);
        }
    }
}
