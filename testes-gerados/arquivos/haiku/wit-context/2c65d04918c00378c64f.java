/*
 * @test
 * @summary Verify OffsetDateTime.toZonedDateTime preserves local date-time, offset and instant
 * @run main OffsetDateTimeWitupTest
 */

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;

public class OffsetDateTimeWitupTest {

    public static void main(String[] args) {
        check(OffsetDateTime.of(2018, 9, 15, 10, 30, 0, 0, ZoneOffset.UTC));
        check(OffsetDateTime.of(2018, 9, 15, 10, 30, 45, 123456789, ZoneOffset.ofHoursMinutes(5, 30)));
        check(OffsetDateTime.of(LocalDateTime.of(2000, 1, 1, 0, 0), ZoneOffset.ofHours(-8)));
        check(OffsetDateTime.of(LocalDate.of(1999, 12, 31), LocalTime.of(23, 59, 59), ZoneOffset.ofHours(14)));
        check(OffsetDateTime.of(2018, 3, 25, 2, 30, 0, 0, ZoneOffset.ofHours(1)));
    }

    private static void check(OffsetDateTime odt) {
        ZonedDateTime zdt = odt.toZonedDateTime();
        if (zdt == null) {
            throw new RuntimeException("toZonedDateTime returned null for " + odt);
        }
        assertEquals(odt.toLocalDateTime(), zdt.toLocalDateTime(), "local date-time");
        assertEquals(odt.getOffset(), zdt.getOffset(), "offset");
        assertEquals(odt.getOffset(), zdt.getZone(), "zone");
        assertEquals(odt.toInstant(), zdt.toInstant(), "instant");
        assertEquals(odt, zdt.toOffsetDateTime(), "round trip");
    }

    private static void assertEquals(Object expected, Object actual, String what) {
        if (!expected.equals(actual)) {
            throw new RuntimeException(what + ": expected " + expected + " but was " + actual);
        }
    }
}
