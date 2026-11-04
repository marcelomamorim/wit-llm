/*
 * @test
 * @summary Test java.time.LocalDateTime.plusNanos(long) method behavior
 * @run main LocalDateTimeWitupTest
 */

import java.time.LocalDateTime;
import java.time.Month;

public class LocalDateTimeWitupTest {
    public static void main(String[] args) {
        testPlusNanosNoOverflow();
        testPlusNanosWithOverflowToNextDay();
        testPlusNanosWithNegativeNanos();
        testPlusNanosWithLargeNanos();
    }

    private static void testPlusNanosNoOverflow() {
        LocalDateTime dt = LocalDateTime.of(2018, Month.JANUARY, 1, 0, 0, 0, 0);
        LocalDateTime result = dt.plusNanos(500);
        if (result.getNano() != 500) {
            throw new AssertionError("Expected nano 500 but got " + result.getNano());
        }
        if (!result.toLocalDate().equals(dt.toLocalDate())) {
            throw new AssertionError("Date should not change when adding small nanos");
        }
    }

    private static void testPlusNanosWithOverflowToNextDay() {
        LocalDateTime dt = LocalDateTime.of(2018, Month.JANUARY, 1, 23, 59, 59, 999_999_999);
        LocalDateTime result = dt.plusNanos(1);
        if (result.getNano() != 0) {
            throw new AssertionError("Expected nano 0 after overflow but got " + result.getNano());
        }
        if (!result.toLocalDate().equals(dt.toLocalDate().plusDays(1))) {
            throw new AssertionError("Date should advance by one day after overflow");
        }
        if (result.getHour() != 0 || result.getMinute() != 0 || result.getSecond() != 0) {
            throw new AssertionError("Time should reset to start of day after overflow");
        }
    }

    private static void testPlusNanosWithNegativeNanos() {
        LocalDateTime dt = LocalDateTime.of(2018, Month.JANUARY, 2, 0, 0, 0, 0);
        LocalDateTime result = dt.plusNanos(-1);
        if (result.getNano() != 999_999_999) {
            throw new AssertionError("Expected nano 999999999 after negative overflow but got " + result.getNano());
        }
        if (!result.toLocalDate().equals(dt.toLocalDate().minusDays(1))) {
            throw new AssertionError("Date should go back by one day after negative overflow");
        }
        if (result.getHour() != 23 || result.getMinute() != 59 || result.getSecond() != 59) {
            throw new AssertionError("Time should be end of previous day after negative overflow");
        }
    }

    private static void testPlusNanosWithLargeNanos() {
        LocalDateTime dt = LocalDateTime.of(2018, Month.JANUARY, 1, 0, 0, 0, 0);
        // Add 1_000_000_000 nanos = 1 second
        LocalDateTime result = dt.plusNanos(1_000_000_000L);
        if (result.getSecond() != 1) {
            throw new AssertionError("Expected second 1 after adding 1_000_000_000 nanos but got " + result.getSecond());
        }
        if (result.getNano() != 0) {
            throw new AssertionError("Expected nano 0 after adding 1_000_000_000 nanos but got " + result.getNano());
        }
    }
}
