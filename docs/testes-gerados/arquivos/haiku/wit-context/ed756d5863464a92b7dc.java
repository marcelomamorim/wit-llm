/*
 * @test
 * @summary Witup-generated checks for OffsetDateTime.parse(CharSequence)
 * @run main OffsetDateTimeWitupTest
 */

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeParseException;

public class OffsetDateTimeWitupTest {

    public static void main(String[] args) {
        OffsetDateTime expected = OffsetDateTime.of(2018, 9, 12, 10, 15, 30, 0, ZoneOffset.UTC);
        check(OffsetDateTime.parse("2018-09-12T10:15:30Z").equals(expected), "Z offset parsed");

        OffsetDateTime shifted = OffsetDateTime.parse("2018-09-12T10:15:30-05:30");
        check(shifted.getOffset().getTotalSeconds() == -19800, "negative offset preserved");
        check(shifted.getHour() == 10, "local hour preserved");

        check(OffsetDateTime.parse("2018-09-12T10:15:30.000000001Z").getNano() == 1, "nano preserved");

        expectParseFailure("2018-09-12");
        expectParseFailure("not a date");
        expectParseFailure("");

        expectNullPointerException();
    }

    private static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    private static void expectParseFailure(String text) {
        try {
            OffsetDateTime.parse(text);
        } catch (DateTimeParseException expected) {
            return;
        }
        throw new RuntimeException("DateTimeParseException expected for: " + text);
    }

    private static void expectNullPointerException() {
        try {
            OffsetDateTime.parse((String) null);
        } catch (NullPointerException expected) {
            return;
        }
        throw new RuntimeException("NullPointerException expected for null text");
    }
}
