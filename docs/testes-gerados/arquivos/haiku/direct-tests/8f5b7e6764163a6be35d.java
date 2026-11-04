/*
 * @test
 * @summary LocalTime.parse(CharSequence) parses ISO local time text and rejects invalid input
 * @run main LocalTimeWitupTest
 */

import java.time.LocalTime;
import java.time.format.DateTimeParseException;

public class LocalTimeWitupTest {

    public static void main(String[] args) {
        check(LocalTime.parse("10:15").equals(LocalTime.of(10, 15)), "parse hh:mm");
        check(LocalTime.parse("10:15:30").equals(LocalTime.of(10, 15, 30)), "parse hh:mm:ss");
        check(LocalTime.parse("10:15:30.5").equals(LocalTime.of(10, 15, 30, 500000000)), "parse fraction");
        check(LocalTime.parse("00:00").equals(LocalTime.of(0, 0)), "parse midnight");
        check(LocalTime.parse("23:59:59.999999999").equals(LocalTime.of(23, 59, 59, 999999999)), "parse max nano");
        check(LocalTime.parse(new StringBuilder("10:15")).equals(LocalTime.of(10, 15)), "parse CharSequence");

        expectDateTimeParseException("25:00");
        expectDateTimeParseException("10:15 ");
        expectDateTimeParseException("abc");
        expectDateTimeParseException("");

        try {
            LocalTime.parse(null);
            throw new RuntimeException("no NullPointerException for null input");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    static void expectDateTimeParseException(String text) {
        try {
            LocalTime.parse(text);
        } catch (DateTimeParseException expected) {
            return;
        }
        throw new RuntimeException("no DateTimeParseException for: \"" + text + "\"");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
