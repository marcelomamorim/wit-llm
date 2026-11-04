/*
 * @test
 * @summary Basic tests for DateTimeFormatter.parse(CharSequence, TemporalQuery)
 * @run main DateTimeFormatterWitupTest
 */

import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.TemporalQuery;
import java.time.LocalDate;
import java.util.Objects;

public class DateTimeFormatterWitupTest {
    public static void main(String[] args) {
        testNullText();
        testNullQuery();
        testValidParse();
        testInvalidParseThrowsDateTimeParseException();
        testRuntimeExceptionWrapped();
    }

    private static void testNullText() {
        DateTimeFormatter fmt = DateTimeFormatter.ISO_LOCAL_DATE;
        TemporalQuery<LocalDate> query = LocalDate::from;
        try {
            fmt.parse(null, query);
            throw new AssertionError("Expected NullPointerException for null text");
        } catch (NullPointerException npe) {
            // expected
        }
    }

    private static void testNullQuery() {
        DateTimeFormatter fmt = DateTimeFormatter.ISO_LOCAL_DATE;
        try {
            fmt.parse("2018-01-01", null);
            throw new AssertionError("Expected NullPointerException for null query");
        } catch (NullPointerException npe) {
            // expected
        }
    }

    private static void testValidParse() {
        DateTimeFormatter fmt = DateTimeFormatter.ISO_LOCAL_DATE;
        TemporalQuery<LocalDate> query = LocalDate::from;
        LocalDate date = fmt.parse("2018-01-01", query);
        if (!date.equals(LocalDate.of(2018, 1, 1))) {
            throw new AssertionError("Parsed date does not match expected");
        }
    }

    private static void testInvalidParseThrowsDateTimeParseException() {
        DateTimeFormatter fmt = DateTimeFormatter.ISO_LOCAL_DATE;
        TemporalQuery<LocalDate> query = LocalDate::from;
        try {
            fmt.parse("not-a-date", query);
            throw new AssertionError("Expected DateTimeParseException for invalid input");
        } catch (DateTimeParseException dtpe) {
            // expected
        }
    }

    private static void testRuntimeExceptionWrapped() {
        DateTimeFormatter fmt = DateTimeFormatter.ISO_LOCAL_DATE;
        // Create a TemporalQuery that throws a RuntimeException to test wrapping
        TemporalQuery<Object> query = new TemporalQuery<Object>() {
            @Override
            public Object queryFrom(java.time.temporal.TemporalAccessor temporal) {
                throw new RuntimeException("Test runtime exception");
            }
        };
        try {
            fmt.parse("2018-01-01", query);
            throw new AssertionError("Expected RuntimeException wrapped in Error");
        } catch (Error err) {
            Throwable cause = err.getCause();
            if (cause == null || !"Test runtime exception".equals(cause.getMessage())) {
                throw new AssertionError("Error cause is not the expected RuntimeException");
            }
        }
    }
}
