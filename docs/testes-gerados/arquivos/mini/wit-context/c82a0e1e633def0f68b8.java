/*
 * @test
 * @summary Test java.time.LocalDateTime.minusMinutes(long) method
 * @run main LocalDateTimeWitupTest
 */

import java.time.LocalDateTime;
import java.time.Month;
import java.time.DateTimeException;

public class LocalDateTimeWitupTest {
    public static void main(String[] args) {
        testMinusMinutesBasic();
        testMinusMinutesZero();
        testMinusMinutesLargeValue();
    }

    private static void testMinusMinutesBasic() {
        LocalDateTime dt = LocalDateTime.of(2018, Month.JANUARY, 1, 0, 10);
        LocalDateTime result = dt.minusMinutes(5);
        // Expect 2018-01-01T00:05
        if (result.getYear() != 2018 || result.getMonth() != Month.JANUARY || result.getDayOfMonth() != 1 ||
            result.getHour() != 0 || result.getMinute() != 5) {
            throw new AssertionError("minusMinutes basic test failed: " + result);
        }
    }

    private static void testMinusMinutesZero() {
        LocalDateTime dt = LocalDateTime.of(2018, Month.JANUARY, 1, 12, 0);
        LocalDateTime result = dt.minusMinutes(0);
        // Expect unchanged
        if (!result.equals(dt)) {
            throw new AssertionError("minusMinutes zero test failed: " + result);
        }
    }

    private static void testMinusMinutesLargeValue() {
        LocalDateTime dt = LocalDateTime.of(2018, Month.JANUARY, 1, 0, 0);
        long largeMinutes = (long)24 * 60 * 365 * 100; // 100 years in minutes
        LocalDateTime result = dt.minusMinutes(largeMinutes);
        // Expect year to be 1918 (2018 - 100)
        if (result.getYear() != 1918) {
            throw new AssertionError("minusMinutes large value test failed: " + result);
        }
    }
}
