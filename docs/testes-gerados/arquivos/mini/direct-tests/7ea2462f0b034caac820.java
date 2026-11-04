/*
 * @test
 * @summary Test java.time.LocalDateTime.plusHours(long) method behavior
 * @run main LocalDateTimeWitupTest
 */

import java.time.LocalDateTime;
import java.time.Month;

public class LocalDateTimeWitupTest {
    public static void main(String[] args) {
        testPlusHoursPositive();
        testPlusHoursNegative();
        testPlusHoursOverflowDay();
        testPlusHoursZero();
    }

    private static void testPlusHoursPositive() {
        LocalDateTime dt = LocalDateTime.of(2018, Month.JANUARY, 1, 10, 0);
        LocalDateTime result = dt.plusHours(5);
        if (result.getHour() != 15 || !result.toLocalDate().equals(dt.toLocalDate())) {
            throw new AssertionError("plusHours positive failed: " + result);
        }
    }

    private static void testPlusHoursNegative() {
        LocalDateTime dt = LocalDateTime.of(2018, Month.JANUARY, 1, 10, 0);
        LocalDateTime result = dt.plusHours(-3);
        if (result.getHour() != 7 || !result.toLocalDate().equals(dt.toLocalDate())) {
            throw new AssertionError("plusHours negative failed: " + result);
        }
    }

    private static void testPlusHoursOverflowDay() {
        LocalDateTime dt = LocalDateTime.of(2018, Month.JANUARY, 1, 23, 0);
        LocalDateTime result = dt.plusHours(2);
        if (result.getHour() != 1 || !result.toLocalDate().equals(dt.toLocalDate().plusDays(1))) {
            throw new AssertionError("plusHours overflow day failed: " + result);
        }
    }

    private static void testPlusHoursZero() {
        LocalDateTime dt = LocalDateTime.of(2018, Month.JANUARY, 1, 10, 0);
        LocalDateTime result = dt.plusHours(0);
        if (!result.equals(dt)) {
            throw new AssertionError("plusHours zero failed: " + result);
        }
    }
}
