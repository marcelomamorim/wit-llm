/*
 * @test
 * @summary Test java.time.ZonedDateTime.withZoneSameInstant(ZoneId) method
 * @run main ZonedDateTimeWitupTest
 */

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.LocalDateTime;
import java.time.DateTimeException;
import java.util.Objects;

public class ZonedDateTimeWitupTest {
    public static void main(String[] args) {
        testWithZoneSameInstantReturnsSameInstanceForSameZone();
        testWithZoneSameInstantReturnsDifferentInstanceForDifferentZone();
        testWithZoneSameInstantRejectsNullZone();
    }

    private static void testWithZoneSameInstantReturnsSameInstanceForSameZone() {
        ZoneId zone = ZoneId.of("Europe/Paris");
        LocalDateTime ldt = LocalDateTime.of(2018, 9, 1, 12, 0);
        ZonedDateTime zdt = ZonedDateTime.of(ldt, zone);
        ZonedDateTime result = zdt.withZoneSameInstant(zone);
        if (result != zdt) {
            throw new AssertionError("Expected same instance when zone is identical");
        }
    }

    private static void testWithZoneSameInstantReturnsDifferentInstanceForDifferentZone() {
        ZoneId zone1 = ZoneId.of("Europe/Paris");
        ZoneId zone2 = ZoneId.of("America/New_York");
        LocalDateTime ldt = LocalDateTime.of(2018, 9, 1, 12, 0);
        ZonedDateTime zdt = ZonedDateTime.of(ldt, zone1);
        ZonedDateTime result = zdt.withZoneSameInstant(zone2);
        if (result == zdt) {
            throw new AssertionError("Expected different instance when zone differs");
        }
        // The instant represented by both ZonedDateTime should be equal
        if (zdt.toInstant().equals(result.toInstant()) == false) {
            throw new AssertionError("Expected same instant after zone change");
        }
        // The zone should be updated
        if (!result.getZone().equals(zone2)) {
            throw new AssertionError("Expected zone to be updated to new zone");
        }
    }

    private static void testWithZoneSameInstantRejectsNullZone() {
        ZoneId zone = ZoneId.of("Europe/Paris");
        LocalDateTime ldt = LocalDateTime.of(2018, 9, 1, 12, 0);
        ZonedDateTime zdt = ZonedDateTime.of(ldt, zone);
        try {
            zdt.withZoneSameInstant(null);
            throw new AssertionError("Expected NullPointerException when zone is null");
        } catch (NullPointerException npe) {
            // expected
            if (!"zone".equals(npe.getMessage())) {
                throw new AssertionError("Expected NullPointerException with message 'zone'");
            }
        }
    }
}
