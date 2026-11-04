/*
 * @test
 * @summary Verify LocalDateTime.parse(CharSequence) delegates to ISO_LOCAL_DATE_TIME and returns expected values or fails on invalid text
 * @run main LocalDateTimeWitupTest
 */

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;

public class LocalDateTimeWitupTest {

    public static void main(String[] args) {
        check(LocalDateTime.parse("2018-09-14T10:15:30"),
              LocalDateTime.of(2018, 9, 14, 10, 15, 30));

        check(LocalDateTime.parse("2018-09-14T10:15"),
              LocalDateTime.of(2018, 9, 14, 10, 15));

        check(LocalDateTime.parse("2018-09-14T10:15:30.123456789"),
              LocalDateTime.of(2018, 9, 14, 10, 15, 30, 123456789));

        check(LocalDateTime.parse(new StringBuilder("2018-01-01T00:00")),
              LocalDateTime.of(2018, 1, 1, 0, 0));

        expectParseFailure("not-a-date");
        expectParseFailure("2018-09-14");
        expectParseFailure("");
    }

    private static void check(LocalDateTime actual, LocalDateTime expected) {
        if (!expected.equals(actual)) {
            throw new RuntimeException("Expected " + expected + " but got " + actual);
        }
    }

    private static void expectParseFailure(String text) {
        try {
            LocalDateTime result = LocalDateTime.parse(text);
            throw new RuntimeException("Expected DateTimeParseException for '" + text + "' but got " + result);
        } catch (DateTimeParseException expected) {
            // expected
        }
    }
}
