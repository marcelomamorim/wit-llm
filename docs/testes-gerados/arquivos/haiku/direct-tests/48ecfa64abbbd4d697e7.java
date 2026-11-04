/*
 * @test
 * @summary Witup-style behavioural tests for LocalDate.from(TemporalAccessor)
 * @run main LocalDateWitupTest
 */

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.TemporalAccessor;

public class LocalDateWitupTest {

    public static void main(String[] args) {
        testFromLocalDateReturnsSameDate();
        testFromLocalDateTimeReturnsDatePart();
        testFromNullThrowsNullPointerException();
        testFromTimeOnlyThrowsDateTimeException();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testFromLocalDateReturnsSameDate() {
        LocalDate expected = LocalDate.of(2020, 1, 15);
        LocalDate actual = LocalDate.from(expected);
        check(expected.equals(actual), "from(LocalDate) should return an equal date");
    }

    static void testFromLocalDateTimeReturnsDatePart() {
        LocalDateTime dt = LocalDateTime.of(2019, 3, 4, 10, 20, 30);
        LocalDate actual = LocalDate.from(dt);
        check(actual.equals(LocalDate.of(2019, 3, 4)),
              "from(LocalDateTime) should return its date part");
    }

    static void testFromNullThrowsNullPointerException() {
        TemporalAccessor temporal = null;
        try {
            LocalDate.from(temporal);
            throw new RuntimeException("from(null) should throw NullPointerException");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    static void testFromTimeOnlyThrowsDateTimeException() {
        LocalTime time = LocalTime.of(10, 0);
        try {
            LocalDate.from(time);
            throw new RuntimeException("from(LocalTime) should throw DateTimeException");
        } catch (DateTimeException expected) {
            // expected
        }
    }
}
