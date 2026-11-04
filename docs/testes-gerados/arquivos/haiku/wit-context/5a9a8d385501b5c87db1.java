/*
 * @test
 * @summary Witup-generated checks for ZonedDateTime.parse(CharSequence)
 * @run main ZonedDateTimeWitupTest
 */

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeParseException;

public class ZonedDateTimeWitupTest {

    public static void main(String[] args) {
        String text = "2007-12-03T10:15:30+01:00[Europe/Paris]";
        ZonedDateTime expected = ZonedDateTime.of(2007, 12, 3, 10, 15, 30, 0, ZoneId.of("Europe/Paris"));

        ZonedDateTime parsed = ZonedDateTime.parse(text);
        check(expected.equals(parsed), "parse(String) result differs from expected");

        ZonedDateTime parsedFromBuilder = ZonedDateTime.parse(new StringBuilder(text));
        check(expected.equals(parsedFromBuilder), "parse(CharSequence) result differs from expected");

        boolean npe = false;
        try {
            ZonedDateTime.parse((CharSequence) null);
        } catch (NullPointerException e) {
            npe = true;
        }
        check(npe, "parse(null) must throw NullPointerException");

        boolean parseFailure = false;
        try {
            ZonedDateTime.parse("not-a-date");
        } catch (DateTimeParseException e) {
            parseFailure = true;
        }
        check(parseFailure, "invalid text must throw DateTimeParseException");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new AssertionError(message);
        }
    }
}
