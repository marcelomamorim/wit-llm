/*
 * @test
 * @summary Verifies DateTimeFormatterBuilder.appendOffsetId() returns the builder and formats offsets, using Z for zero offset
 * @run main DateTimeFormatterBuilderWitupTest
 */

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;

public class DateTimeFormatterBuilderWitupTest {

    public static void main(String[] args) {
        DateTimeFormatterBuilder builder = new DateTimeFormatterBuilder();
        if (builder.appendOffsetId() != builder) {
            throw new RuntimeException("appendOffsetId() must return the same builder instance");
        }

        DateTimeFormatter formatter = new DateTimeFormatterBuilder()
                .appendOffsetId()
                .toFormatter();

        check(formatter, ZoneOffset.UTC, "Z");
        check(formatter, ZoneOffset.ofHours(2), "+02:00");
        check(formatter, ZoneOffset.ofHoursMinutesSeconds(-3, -30, 0), "-03:30");
    }

    private static void check(DateTimeFormatter formatter, ZoneOffset offset, String expected) {
        OffsetDateTime odt = LocalDateTime.of(2018, 9, 1, 12, 0).atOffset(offset);
        String actual = formatter.format(odt);
        if (!expected.equals(actual)) {
            throw new RuntimeException("Expected " + expected + " but got " + actual);
        }
    }
}
