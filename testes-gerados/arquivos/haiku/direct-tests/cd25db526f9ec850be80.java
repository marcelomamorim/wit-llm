/*
 * @test
 * @summary Verify DateTimeFormatterBuilder.appendOffsetId() returns the builder and formats zero offset as Z and other offsets as +HH:MM / -HH:MM
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

        DateTimeFormatter f = new DateTimeFormatterBuilder().appendOffsetId().toFormatter();
        LocalDateTime ldt = LocalDateTime.of(2020, 1, 1, 12, 0);

        check(f.format(OffsetDateTime.of(ldt, ZoneOffset.UTC)), "Z");
        check(f.format(OffsetDateTime.of(ldt, ZoneOffset.ofHours(1))), "+01:00");
        check(f.format(OffsetDateTime.of(ldt, ZoneOffset.ofHoursMinutesSeconds(-5, -30, 0))), "-05:30");

        ZoneOffset parsedPlus = ZoneOffset.from(f.parse("+02:00"));
        if (!parsedPlus.equals(ZoneOffset.ofHours(2))) {
            throw new RuntimeException("Parsed +02:00 as " + parsedPlus);
        }

        ZoneOffset parsedZ = ZoneOffset.from(f.parse("Z"));
        if (!parsedZ.equals(ZoneOffset.UTC)) {
            throw new RuntimeException("Parsed Z as " + parsedZ);
        }
    }

    private static void check(String actual, String expected) {
        if (!expected.equals(actual)) {
            throw new RuntimeException("Expected " + expected + " but got " + actual);
        }
    }
}
