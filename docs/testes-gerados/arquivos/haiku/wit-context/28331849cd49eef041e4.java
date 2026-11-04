/*
 * @test
 * @summary Checks that ZonedDateTime.toOffsetDateTime keeps local date-time, offset and instant consistent with the source ZonedDateTime
 * @run main ZonedDateTimeWitupTest
 */

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public class ZonedDateTimeWitupTest {

    public static void main(String[] args) {
        ZoneId paris = ZoneId.of("Europe/Paris");
        ZoneId utc = ZoneId.of("UTC");

        check(ZonedDateTime.of(2018, 6, 15, 12, 30, 45, 123456789, paris));
        check(ZonedDateTime.of(2018, 1, 10, 0, 0, 0, 0, utc));
        check(ZonedDateTime.of(LocalDate.of(2018, 12, 31), LocalTime.of(23, 59, 59, 999999999), paris));
        check(ZonedDateTime.of(LocalDateTime.of(2018, 3, 25, 2, 30), paris));
        check(ZonedDateTime.of(LocalDateTime.of(2018, 10, 28, 2, 30), paris));
        check(ZonedDateTime.of(LocalDateTime.of(2000, 2, 29, 6, 0), utc));
    }

    private static void check(ZonedDateTime zdt) {
        OffsetDateTime odt = zdt.toOffsetDateTime();
        if (odt == null) {
            throw new RuntimeException("toOffsetDateTime returned null for " + zdt);
        }
        if (!odt.toLocalDateTime().equals(zdt.toLocalDateTime())) {
            throw new RuntimeException("Local date-time mismatch: " + zdt + " -> " + odt);
        }
        if (!odt.getOffset().equals(zdt.getOffset())) {
            throw new RuntimeException("Offset mismatch: " + zdt + " -> " + odt);
        }
        if (!odt.toInstant().equals(zdt.toInstant())) {
            throw new RuntimeException("Instant mismatch: " + zdt + " -> " + odt);
        }
    }
}
