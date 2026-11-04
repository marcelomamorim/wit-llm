/*
 * @test
 * @summary WITUP-derived checks for ZonedDateTime.parse(CharSequence, DateTimeFormatter)
 * @run main ZonedDateTimeWitupTest
 */

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class ZonedDateTimeWitupTest {

    public static void main(String[] args) {
        testNullFormatterThrowsNPE();
        testNullTextThrowsNPE();
        testValidParse();
        testInvalidTextThrowsDateTimeParseException();
    }

    static void testNullFormatterThrowsNPE() {
        assertThrows(NullPointerException.class, () ->
                ZonedDateTime.parse("2007-12-03T10:15:30+01:00[Europe/Paris]", null));
    }

    static void testNullTextThrowsNPE() {
        assertThrows(NullPointerException.class, () ->
                ZonedDateTime.parse(null, DateTimeFormatter.ISO_ZONED_DATE_TIME));
    }

    static void testValidParse() {
        ZonedDateTime actual = ZonedDateTime.parse("2007-12-03T10:15:30+01:00[Europe/Paris]",
                DateTimeFormatter.ISO_ZONED_DATE_TIME);
        ZonedDateTime expected = ZonedDateTime.of(2007, 12, 3, 10, 15, 30, 0,
                ZoneId.of("Europe/Paris"));
        if (!expected.equals(actual)) {
            throw new RuntimeException("unexpected parse result: " + actual);
        }
    }

    static void testInvalidTextThrowsDateTimeParseException() {
        assertThrows(DateTimeParseException.class, () ->
                ZonedDateTime.parse("not-a-date", DateTimeFormatter.ISO_ZONED_DATE_TIME));
    }

    static void assertThrows(Class<? extends Throwable> expected, Runnable action) {
        try {
            action.run();
        } catch (Throwable t) {
            if (expected.isInstance(t)) {
                return;
            }
            throw new RuntimeException("unexpected exception: " + t, t);
        }
        throw new RuntimeException("expected exception not thrown: " + expected.getName());
    }
}
