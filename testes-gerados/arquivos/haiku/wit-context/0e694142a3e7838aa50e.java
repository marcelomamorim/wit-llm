/*
 * @test
 * @summary Verifies public parse(CharSequence) behavior of DateTimeFormatter for null, valid and invalid input
 * @run main DateTimeFormatterWitupTest
 */

import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoField;
import java.time.temporal.TemporalAccessor;

public class DateTimeFormatterWitupTest {

    public static void main(String[] args) {
        testNullTextThrowsNullPointerException();
        testValidIsoDateParses();
        testInvalidTextThrowsDateTimeParseException();
    }

    static void testNullTextThrowsNullPointerException() {
        try {
            DateTimeFormatter.ISO_LOCAL_DATE.parse(null);
            throw new RuntimeException("expected NullPointerException for null text");
        } catch (NullPointerException expected) {
        }
    }

    static void testValidIsoDateParses() {
        TemporalAccessor ta = DateTimeFormatter.ISO_LOCAL_DATE.parse("2012-06-30");
        if (ta.get(ChronoField.YEAR) != 2012
                || ta.get(ChronoField.MONTH_OF_YEAR) != 6
                || ta.get(ChronoField.DAY_OF_MONTH) != 30) {
            throw new RuntimeException("unexpected parsed fields");
        }
    }

    static void testInvalidTextThrowsDateTimeParseException() {
        try {
            DateTimeFormatter.ISO_LOCAL_DATE.parse("not-a-date");
            throw new RuntimeException("expected DateTimeParseException for invalid text");
        } catch (DateTimeParseException expected) {
        }
    }
}
