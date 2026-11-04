/*
 * @test
 * @summary Verifies OffsetDateTime.toZonedDateTime keeps local date-time, offset and instant
 * @run main OffsetDateTimeWitupTest
 */

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;

public class OffsetDateTimeWitupTest {

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void checkConversion(OffsetDateTime odt) {
        ZonedDateTime zdt = odt.toZonedDateTime();
        check(zdt != null, "toZonedDateTime returned null");
        check(zdt.toLocalDateTime().equals(odt.toLocalDateTime()),
              "local date-time differs: " + zdt + " vs " + odt);
        check(zdt.getOffset().equals(odt.getOffset()),
              "offset differs: " + zdt + " vs " + odt);
        check(zdt.getZone().equals(odt.getOffset()),
              "zone should be the offset: " + zdt);
        check(zdt.toInstant().equals(odt.toInstant()),
              "instant differs: " + zdt + " vs " + odt);
        check(zdt.toOffsetDateTime().equals(odt),
              "round trip differs: " + zdt + " vs " + odt);
    }

    public static void main(String[] args) {
        OffsetDateTime a = OffsetDateTime.of(
                LocalDate.of(2018, 9, 15),
                LocalTime.of(10, 30, 45, 123456789),
                ZoneOffset.ofHours(2));
        checkConversion(a);

        OffsetDateTime b = OffsetDateTime.of(
                LocalDateTime.of(2000, 1, 1, 0, 0, 0, 0),
                ZoneOffset.ofHoursMinutes(-5, -30));
        checkConversion(b);

        OffsetDateTime c = OffsetDateTime.of(
                1999, 12, 31, 23, 59, 59, 999999999, ZoneOffset.UTC);
        checkConversion(c);

        OffsetDateTime d = OffsetDateTime.of(
                2020, 2, 29, 12, 0, 0, 0, ZoneOffset.ofHours(-12));
        checkConversion(d);
    }
}
