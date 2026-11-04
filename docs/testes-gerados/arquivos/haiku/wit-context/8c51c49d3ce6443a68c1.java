/*
 * @test
 * @summary Witup-derived checks for DateTimeFormatter.parse(CharSequence, TemporalQuery) null guards and observable parsing behavior
 * @run main DateTimeFormatterWitupTest
 */

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.TemporalQuery;

public class DateTimeFormatterWitupTest {

    public static void main(String[] args) {
        testNullTextThrowsNpe();
        testNullQueryThrowsNpe();
        testValidParseReturnsQueryResult();
        testInvalidTextThrowsDateTimeParseException();
    }

    static void testNullTextThrowsNpe() {
        TemporalQuery<LocalDate> query = LocalDate::from;
        try {
            DateTimeFormatter.ISO_LOCAL_DATE.parse(null, query);
        } catch (NullPointerException expected) {
            return;
        }
        throw new RuntimeException("expected NullPointerException for null text");
    }

    static void testNullQueryThrowsNpe() {
        try {
            DateTimeFormatter.ISO_LOCAL_DATE.parse("2020-01-02", null);
        } catch (NullPointerException expected) {
            return;
        }
        throw new RuntimeException("expected NullPointerException for null query");
    }

    static void testValidParseReturnsQueryResult() {
        TemporalQuery<LocalDate> query = LocalDate::from;
        LocalDate actual = DateTimeFormatter.ISO_LOCAL_DATE.parse("2020-01-02", query);
        LocalDate expected = LocalDate.of(2020, 1, 2);
        if (!expected.equals(actual)) {
            throw new RuntimeException("expected " + expected + " but got " + actual);
        }
    }

    static void testInvalidTextThrowsDateTimeParseException() {
        TemporalQuery<LocalDate> query = LocalDate::from;
        try {
            DateTimeFormatter.ISO_LOCAL_DATE.parse("not-a-date", query);
        } catch (DateTimeParseException expected) {
            return;
        }
        throw new RuntimeException("expected DateTimeParseException for invalid text");
    }
}
