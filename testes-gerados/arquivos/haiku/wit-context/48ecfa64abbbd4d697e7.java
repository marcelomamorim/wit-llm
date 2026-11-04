/*
 * @test
 * @summary Witup-derived tests for LocalDate.from(TemporalAccessor)
 * @run main LocalDateWitupTest
 */

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class LocalDateWitupTest {

    public static void main(String[] args) {
        testNullThrowsNullPointerException();
        testLocalDateReturnsSameValue();
        testLocalDateTimeYieldsDatePart();
        testLocalTimeThrowsDateTimeException();
    }

    static void testNullThrowsNullPointerException() {
        try {
            LocalDate.from(null);
        } catch (NullPointerException e) {
            return;
        }
        throw new AssertionError("expected NullPointerException for null temporal");
    }

    static void testLocalDateReturnsSameValue() {
        LocalDate expected = LocalDate.of(2020, 2, 29);
        LocalDate actual = LocalDate.from(expected);
        if (!expected.equals(actual)) {
            throw new AssertionError("expected " + expected + " but got " + actual);
        }
    }

    static void testLocalDateTimeYieldsDatePart() {
        LocalDateTime dt = LocalDateTime.of(2020, 1, 15, 10, 30);
        LocalDate actual = LocalDate.from(dt);
        LocalDate expected = LocalDate.of(2020, 1, 15);
        if (!expected.equals(actual)) {
            throw new AssertionError("expected " + expected + " but got " + actual);
        }
    }

    static void testLocalTimeThrowsDateTimeException() {
        try {
            LocalDate.from(LocalTime.of(10, 0));
        } catch (DateTimeException e) {
            return;
        }
        throw new AssertionError("expected DateTimeException for LocalTime without date");
    }
}
