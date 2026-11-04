/*
 * @test
 * @summary LocalTime.parse(CharSequence, DateTimeFormatter) observable behavior
 * @run main LocalTimeWitupTest
 */

import java.time.DateTimeException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class LocalTimeWitupTest {

    public static void main(String[] args) {
        DateTimeFormatter hm = DateTimeFormatter.ofPattern("HH:mm");

        check(LocalTime.of(10, 15).equals(LocalTime.parse("10:15", hm)), "HH:mm parse");
        check(LocalTime.of(8, 5).equals(LocalTime.parse(new StringBuilder("08:05"), hm)),
              "CharSequence StringBuilder parse");
        check(LocalTime.of(10, 15, 30).equals(
                LocalTime.parse("10:15:30", DateTimeFormatter.ISO_LOCAL_TIME)), "ISO_LOCAL_TIME seconds");
        check(LocalTime.of(23, 59, 59, 999999999).equals(
                LocalTime.parse("23:59:59.999999999", DateTimeFormatter.ISO_LOCAL_TIME)), "ISO nanos");

        try {
            LocalTime.parse("10:15", null);
            fail("null formatter should throw NullPointerException");
        } catch (NullPointerException expected) {
            // expected
        }

        try {
            LocalTime.parse("25:00", hm);
            fail("invalid hour should throw DateTimeParseException");
        } catch (DateTimeParseException expected) {
            // expected
        }

        try {
            LocalTime.parse("10:15x", hm);
            fail("trailing text should throw DateTimeParseException");
        } catch (DateTimeParseException expected) {
            // expected
        }

        try {
            LocalTime.parse("2020-01-01", DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            fail("formatter without time fields should throw DateTimeException");
        } catch (DateTimeException expected) {
            // expected
        }
    }

    private static void check(boolean condition, String message) {
        if (!condition) {
            fail(message);
        }
    }

    private static void fail(String message) {
        throw new RuntimeException(message);
    }
}
