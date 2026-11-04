/*
 * @test
 * @summary Test java.time.LocalDateTime.withHour(int) method
 * @run main LocalDateTimeWitupTest
 */

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;

public class LocalDateTimeWitupTest {
    public static void main(String[] args) {
        testWithHourValid();
        testWithHourBoundary();
        testWithHourInvalidLow();
        testWithHourInvalidHigh();
    }

    private static void testWithHourValid() {
        LocalDateTime ldt = LocalDateTime.of(2018, Month.JANUARY, 1, 10, 30);
        LocalDateTime result = ldt.withHour(15);
        if (result.getHour() != 15) {
            throw new AssertionError("Expected hour 15 but got " + result.getHour());
        }
        if (result.getMinute() != 30) {
            throw new AssertionError("Minute changed unexpectedly");
        }
        if (!result.toLocalDate().equals(ldt.toLocalDate())) {
            throw new AssertionError("Date changed unexpectedly");
        }
    }

    private static void testWithHourBoundary() {
        LocalDateTime ldt = LocalDateTime.of(2018, Month.DECEMBER, 31, 0, 0);
        LocalDateTime result = ldt.withHour(0);
        if (result.getHour() != 0) {
            throw new AssertionError("Expected hour 0 but got " + result.getHour());
        }
        result = ldt.withHour(23);
        if (result.getHour() != 23) {
            throw new AssertionError("Expected hour 23 but got " + result.getHour());
        }
    }

    private static void testWithHourInvalidLow() {
        LocalDateTime ldt = LocalDateTime.of(2018, Month.JUNE, 15, 12, 0);
        try {
            ldt.withHour(-1);
            throw new AssertionError("Expected DateTimeException for hour -1");
        } catch (java.time.DateTimeException e) {
            // expected
        }
    }

    private static void testWithHourInvalidHigh() {
        LocalDateTime ldt = LocalDateTime.of(2018, Month.JUNE, 15, 12, 0);
        try {
            ldt.withHour(24);
            throw new AssertionError("Expected DateTimeException for hour 24");
        } catch (java.time.DateTimeException e) {
            // expected
        }
    }
}
