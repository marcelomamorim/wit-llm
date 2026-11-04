/*
 * @test
 * @summary Tests DateTimeFormatter.parse(CharSequence, TemporalQuery) null checks, successful parsing, parse errors and query exception wrapping
 * @run main DateTimeFormatterWitupTest
 */

import java.time.LocalDate;

public class DateTimeFormatterWitupTest {

    public static void main(String[] args) {
        testNullTextThrowsNPE();
        testNullQueryThrowsNPE();
        testParseLocalDate();
        testInvalidTextThrowsDateTimeParseException();
        testQueryRuntimeExceptionIsWrappedAsParseException();
    }

    static void testNullTextThrowsNPE() {
        try {
            DateTimeFormatter.ISO_LOCAL_DATE.parse(null, LocalDate::from);
            throw new AssertionError("expected NullPointerException for null text");
        } catch (NullPointerException expected) {
        }
    }

    static void testNullQueryThrowsNPE() {
        try {
            DateTimeFormatter.ISO_LOCAL_DATE.parse("2012-06-30", (TemporalQuery<LocalDate>) null);
            throw new AssertionError("expected NullPointerException for null query");
        } catch (NullPointerException expected) {
        }
    }

    static void testParseLocalDate() {
        LocalDate d = DateTimeFormatter.ISO_LOCAL_DATE.parse("2012-06-30", LocalDate::from);
        if (!d.equals(LocalDate.of(2012, 6, 30))) {
            throw new AssertionError("unexpected date: " + d);
        }
    }

    static void testInvalidTextThrowsDateTimeParseException() {
        try {
            DateTimeFormatter.ISO_LOCAL_DATE.parse("not a date", LocalDate::from);
            throw new AssertionError("expected DateTimeParseException");
        } catch (DateTimeParseException expected) {
        }
    }

    static void testQueryRuntimeExceptionIsWrappedAsParseException() {
        TemporalQuery<LocalDate> failing = t -> { throw new IllegalStateException("boom"); };
        try {
            DateTimeFormatter.ISO_LOCAL_DATE.parse("2012-06-30", failing);
            throw new AssertionError("expected DateTimeParseException");
        } catch (DateTimeParseException e) {
            if (!(e.getCause() instanceof IllegalStateException)) {
                throw new AssertionError("unexpected cause: " + e.getCause());
            }
            if (e.getErrorIndex() != 0) {
                throw new AssertionError("unexpected error index: " + e.getErrorIndex());
            }
        }
    }
}
