/*
 * @test
 * @summary OffsetDateTime.parse(CharSequence) parses ISO offset date-time text and rejects text without a valid offset or time
 * @run main OffsetDateTimeWitupTest
 */

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeParseException;

public class OffsetDateTimeWitupTest {

    public static void main(String[] args) {
        check(OffsetDateTime.parse("2018-01-02T03:04:05+02:00"),
              OffsetDateTime.of(2018, 1, 2, 3, 4, 5, 0, ZoneOffset.ofHours(2)));
        check(OffsetDateTime.parse("2018-01-02T03:04:05.123456789Z"),
              OffsetDateTime.of(2018, 1, 2, 3, 4, 5, 123456789, ZoneOffset.UTC));
        check(OffsetDateTime.parse("2018-01-02T03:04:05-05:30"),
              OffsetDateTime.of(2018, 1, 2, 3, 4, 5, 0, ZoneOffset.of("-05:30")));

        expectParseFailure("2018-01-02T03:04:05");
        expectParseFailure("2018-01-02");
        expectParseFailure("");
    }

    static void check(OffsetDateTime actual, OffsetDateTime expected) {
        if (!expected.equals(actual)) {
            throw new RuntimeException("expected " + expected + " but was " + actual);
        }
    }

    static void expectParseFailure(String text) {
        try {
            OffsetDateTime.parse(text);
        } catch (DateTimeParseException e) {
            return;
        }
        throw new RuntimeException("expected DateTimeParseException for: " + text);
    }
}
