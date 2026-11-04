/*
 * @test
 * @summary Test java.time.LocalDateTime.minusHours(long) for normal and boundary cases
 * @run main LocalDateTimeWitupTest
 */

import java.time.LocalDateTime;
import java.time.Month;
import java.time.DateTimeException;

public class LocalDateTimeWitupTest {
    public static void main(String[] args) {
        testMinusHoursNormal();
        testMinusHoursZero();
        testMinusHoursLargeNegative();
    }

    private static void testMinusHoursNormal() {
        LocalDateTime dt = LocalDateTime.of(2018, Month.SEPTEMBER, 30, 10, 0);
        LocalDateTime result = dt.minusHours(5);
        // Expect hour to be 5
        if (result.getHour() != 5) {
            throw new AssertionError("Expected hour 5 but got " + result.getHour());
        }
        // Date should remain the same day
        if (result.getDayOfMonth() != 30) {
            throw new AssertionError("Expected day 30 but got " + result.getDayOfMonth());
        }
    }

    private static void testMinusHoursZero() {
        LocalDateTime dt = LocalDateTime.of(2018, Month.SEPTEMBER, 30, 10, 0);
        LocalDateTime result = dt.minusHours(0);
        // Expect same instance or equal date-time
        if (!dt.equals(result)) {
            throw new AssertionError("Expected same LocalDateTime when minusHours(0)");
        }
    }

    private static void testMinusHoursLargeNegative() {
        LocalDateTime dt = LocalDateTime.of(2018, Month.SEPTEMBER, 30, 10, 0);
        // Subtract a large number of hours to cause date overflow backwards
        LocalDateTime result = dt.minusHours(48);
        // Expect day to be 28 (two days before 30)
        if (result.getDayOfMonth() != 28) {
            throw new AssertionError("Expected day 28 but got " + result.getDayOfMonth());
        }
        // Expect hour to be 10 (same hour)
        if (result.getHour() != 10) {
            throw new AssertionError("Expected hour 10 but got " + result.getHour());
        }
    }
}
