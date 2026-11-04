/*
 * @test
 * @summary Verify LocalDateTime.withSecond replaces only the second-of-minute and preserves date, hour, minute and nanos
 * @run main LocalDateTimeWitupTest
 */

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class LocalDateTimeWitupTest {

    public static void main(String[] args) {
        testReplacesSecondOnly();
        testZeroSecond();
        testPreservesNanos();
        testSameValueReturnsEqual();
        testInvalidSecondRejected();
    }

    static void testReplacesSecondOnly() {
        LocalDateTime dt = LocalDateTime.of(2007, 12, 3, 10, 15, 30);
        LocalDateTime result = dt.withSecond(45);
        check(result.equals(LocalDateTime.of(2007, 12, 3, 10, 15, 45)),
              "expected 2007-12-03T10:15:45 but got " + result);
        check(result.toLocalDate().equals(LocalDate.of(2007, 12, 3)), "date changed");
        check(result.getHour() == 10, "hour changed");
        check(result.getMinute() == 15, "minute changed");
    }

    static void testZeroSecond() {
        LocalDateTime dt = LocalDateTime.of(2008, 1, 31, 23, 59, 59);
        LocalDateTime result = dt.withSecond(0);
        check(result.equals(LocalDateTime.of(2008, 1, 31, 23, 59, 0)),
              "expected 2008-01-31T23:59:00 but got " + result);
    }

    static void testPreservesNanos() {
        LocalDateTime dt = LocalDateTime.of(2007, 12, 3, 10, 15, 30, 123456789);
        LocalDateTime result = dt.withSecond(5);
        check(result.equals(LocalDateTime.of(2007, 12, 3, 10, 15, 5, 123456789)),
              "expected nanos preserved but got " + result);
        check(result.getNano() == 123456789, "nano changed");
    }

    static void testSameValueReturnsEqual() {
        LocalDateTime dt = LocalDateTime.of(2007, 12, 3, 10, 15, 30);
        LocalDateTime result = dt.withSecond(30);
        check(result.equals(dt), "same second should yield equal value");
    }

    static void testInvalidSecondRejected() {
        LocalDateTime dt = LocalDateTime.of(2007, 12, 3, 10, 15, 30);
        boolean thrown = false;
        try {
            dt.withSecond(60);
        } catch (DateTimeException e) {
            thrown = true;
        }
        check(thrown, "withSecond(60) should throw DateTimeException");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
