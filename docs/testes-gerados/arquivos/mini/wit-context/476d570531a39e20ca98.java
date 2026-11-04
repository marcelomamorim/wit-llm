/*
 * @test
 * @summary Test java.time.ZonedDateTime.withZoneSameInstant(ZoneId) method
 * @run main ZonedDateTimeWitupTest
 */

import java.time.ZonedDateTime;
import java.time.ZoneId;
import java.time.LocalDateTime;
import java.time.DateTimeException;
import java.util.Objects;

public class ZonedDateTimeWitupTest {
    public static void main(String[] args) {
        testWithZoneSameInstantNullZone();
        testWithZoneSameInstantSameZoneReturnsThis();
        testWithZoneSameInstantDifferentZoneCreatesNew();
    }

    private static void testWithZoneSameInstantNullZone() {
        ZonedDateTime zdt = ZonedDateTime.of(LocalDateTime.of(2018, 9, 1, 12, 0), ZoneId.of("UTC"));
        try {
            zdt.withZoneSameInstant(null);
            throw new AssertionError("Expected NullPointerException for null zone");
        } catch (NullPointerException npe) {
            // expected
        }
    }

    private static void testWithZoneSameInstantSameZoneReturnsThis() {
        ZoneId zone = ZoneId.of("Europe/Paris");
        ZonedDateTime zdt = ZonedDateTime.of(LocalDateTime.of(2018, 9, 1, 12, 0), zone);
        ZonedDateTime result = zdt.withZoneSameInstant(zone);
        if (result != zdt) {
            throw new AssertionError("Expected same instance when zone is unchanged");
        }
    }

    private static void testWithZoneSameInstantDifferentZoneCreatesNew() {
        ZoneId zone1 = ZoneId.of("Europe/Paris");
        ZoneId zone2 = ZoneId.of("America/New_York");
        ZonedDateTime zdt = ZonedDateTime.of(LocalDateTime.of(2018, 9, 1, 12, 0), zone1);
        ZonedDateTime result = zdt.withZoneSameInstant(zone2);
        if (result == zdt) {
            throw new AssertionError("Expected new instance when zone is different");
        }
        if (!result.getZone().equals(zone2)) {
            throw new AssertionError("Expected zone to be changed to the new zone");
        }
        // The instant represented should be the same
        if (zdt.toInstant().equals(result.toInstant()) == false) {
            throw new AssertionError("Expected same instant after zone change");
        }
    }
}
