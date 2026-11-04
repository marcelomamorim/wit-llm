/*
 * @test
 * @summary Test java.time.OffsetDateTime.atZoneSameInstant(ZoneId) method
 * @run main OffsetDateTimeWitupTest
 */

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;

public class OffsetDateTimeWitupTest {
    public static void main(String[] args) {
        testAtZoneSameInstantBasic();
        testAtZoneSameInstantDifferentZone();
    }

    private static void testAtZoneSameInstantBasic() {
        // Create OffsetDateTime for 2018-01-01T12:00:00+01:00
        OffsetDateTime odt = OffsetDateTime.of(2018, 1, 1, 12, 0, 0, 0, ZoneOffset.ofHours(1));
        // Use ZoneId UTC
        ZoneId zone = ZoneId.of("UTC");
        ZonedDateTime zdt = odt.atZoneSameInstant(zone);

        // The instant should be the same, so zdt.toInstant() equals odt.toInstant()
        if (!zdt.toInstant().equals(odt.toInstant())) {
            throw new AssertionError("Instant mismatch for atZoneSameInstant basic test");
        }
        // The zone of zdt should be the requested zone
        if (!zdt.getZone().equals(zone)) {
            throw new AssertionError("Zone mismatch for atZoneSameInstant basic test");
        }
    }

    private static void testAtZoneSameInstantDifferentZone() {
        // Create OffsetDateTime for 2020-06-15T15:30:00+02:00
        OffsetDateTime odt = OffsetDateTime.of(LocalDateTime.of(2020, 6, 15, 15, 30), ZoneOffset.ofHours(2));
        // Use ZoneId Europe/London (which may have DST)
        ZoneId zone = ZoneId.of("Europe/London");
        ZonedDateTime zdt = odt.atZoneSameInstant(zone);

        // The instant should be the same
        if (!zdt.toInstant().equals(odt.toInstant())) {
            throw new AssertionError("Instant mismatch for atZoneSameInstant different zone test");
        }
        // The zone of zdt should be the requested zone
        if (!zdt.getZone().equals(zone)) {
            throw new AssertionError("Zone mismatch for atZoneSameInstant different zone test");
        }
    }
}
