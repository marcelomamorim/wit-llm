/*
 * @test
 * @summary Test java.time.LocalDateTime plusSeconds method behavior
 * @run main LocalDateTimeWitupTest
 */

import java.time.LocalDateTime;
import java.time.Month;

public class LocalDateTimeWitupTest {
    public static void main(String[] args) {
        testPlusSecondsNoOverflow();
        testPlusSecondsWithOverflowToNextDay();
        testPlusSecondsWithNegativeSeconds();
        testPlusSecondsWithLargeSeconds();
    }

    private static void testPlusSecondsNoOverflow() {
        LocalDateTime dt = LocalDateTime.of(2018, Month.JANUARY, 1, 12, 0, 0);
        LocalDateTime result = dt.plusSeconds(30);
        if (result.getSecond() != 30 || result.getMinute() != 0 || result.getHour() != 12) {
            throw new AssertionError("plusSeconds failed for simple addition");
        }
    }

    private static void testPlusSecondsWithOverflowToNextDay() {
        LocalDateTime dt = LocalDateTime.of(2018, Month.JANUARY, 1, 23, 59, 50);
        LocalDateTime result = dt.plusSeconds(15);
        if (result.getDayOfMonth() != 2 || result.getHour() != 0 || result.getMinute() != 0 || result.getSecond() != 5) {
            throw new AssertionError("plusSeconds failed to overflow to next day");
        }
    }

    private static void testPlusSecondsWithNegativeSeconds() {
        LocalDateTime dt = LocalDateTime.of(2018, Month.JANUARY, 2, 0, 0, 10);
        LocalDateTime result = dt.plusSeconds(-15);
        if (result.getDayOfMonth() != 1 || result.getHour() != 23 || result.getMinute() != 59 || result.getSecond() != 55) {
            throw new AssertionError("plusSeconds failed with negative seconds");
        }
    }

    private static void testPlusSecondsWithLargeSeconds() {
        LocalDateTime dt = LocalDateTime.of(2018, Month.JANUARY, 1, 0, 0, 0);
        long secondsToAdd = 86400L * 3 + 3661; // 3 days + 1 hour + 1 minute + 1 second
        LocalDateTime result = dt.plusSeconds(secondsToAdd);
        if (result.getDayOfMonth() != 4 || result.getHour() != 1 || result.getMinute() != 1 || result.getSecond() != 1) {
            throw new AssertionError("plusSeconds failed with large seconds addition");
        }
    }
}
