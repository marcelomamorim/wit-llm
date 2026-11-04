/*
 * @test
 * @summary Test java.time.LocalDateTime.withSecond(int) method
 * @run main LocalDateTimeWitupTest
 */

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;

public class LocalDateTimeWitupTest {
    public static void main(String[] args) {
        testWithSecondValid();
        testWithSecondZero();
        testWithSecondMax();
        testWithSecondInvalidNegative();
        testWithSecondInvalidTooLarge();
    }

    private static void testWithSecondValid() {
        LocalDateTime dt = LocalDateTime.of(2018, Month.JANUARY, 1, 12, 30, 15);
        LocalDateTime result = dt.withSecond(45);
        if (result.getSecond() != 45) {
            throw new AssertionError("Expected second 45 but got " + result.getSecond());
        }
        if (result.getMinute() != 30 || result.getHour() != 12 || result.getDayOfMonth() != 1) {
            throw new AssertionError("Date or time fields other than second changed");
        }
    }

    private static void testWithSecondZero() {
        LocalDateTime dt = LocalDateTime.of(2020, 5, 20, 23, 59, 59);
        LocalDateTime result = dt.withSecond(0);
        if (result.getSecond() != 0) {
            throw new AssertionError("Expected second 0 but got " + result.getSecond());
        }
    }

    private static void testWithSecondMax() {
        LocalDateTime dt = LocalDateTime.of(2021, 12, 31, 0, 0, 0);
        LocalDateTime result = dt.withSecond(59);
        if (result.getSecond() != 59) {
            throw new AssertionError("Expected second 59 but got " + result.getSecond());
        }
    }

    private static void testWithSecondInvalidNegative() {
        LocalDateTime dt = LocalDateTime.of(2019, 7, 15, 10, 10, 10);
        try {
            dt.withSecond(-1);
            throw new AssertionError("Expected DateTimeException for second -1");
        } catch (java.time.DateTimeException e) {
            // expected
        }
    }

    private static void testWithSecondInvalidTooLarge() {
        LocalDateTime dt = LocalDateTime.of(2019, 7, 15, 10, 10, 10);
        try {
            dt.withSecond(60);
            throw new AssertionError("Expected DateTimeException for second 60");
        } catch (java.time.DateTimeException e) {
            // expected
        }
    }
}
