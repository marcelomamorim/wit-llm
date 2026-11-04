/*
 * @test
 * @summary Test java.time.ZonedDateTime.withZoneSameLocal(ZoneId) method
 * @run main ZonedDateTimeWitupTest
 */

import java.time.ZonedDateTime;
import java.time.ZoneId;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Objects;

public class ZonedDateTimeWitupTest {
    public static void main(String[] args) {
        testWithZoneSameLocalReturnsSameInstanceIfZoneEquals();
        testWithZoneSameLocalReturnsNewInstanceIfZoneDiffers();
        testWithZoneSameLocalRejectsNullZone();
    }

    private static void testWithZoneSameLocalReturnsSameInstanceIfZoneEquals() {
        ZoneId zone = ZoneId.of("Europe/Paris");
        LocalDateTime ldt = LocalDateTime.of(2020, 1, 1, 12, 0);
        ZonedDateTime zdt = ZonedDateTime.of(ldt, zone);
        ZonedDateTime result = zdt.withZoneSameLocal(zone);
        if (result != zdt) {
            throw new AssertionError("Expected same instance when zone is equal");
        }
    }

    private static void testWithZoneSameLocalReturnsNewInstanceIfZoneDiffers() {
        ZoneId zone1 = ZoneId.of("Europe/Paris");
        ZoneId zone2 = ZoneId.of("America/New_York");
        LocalDateTime ldt = LocalDateTime.of(2020, 1, 1, 12, 0);
        ZonedDateTime zdt = ZonedDateTime.of(ldt, zone1);
        ZonedDateTime result = zdt.withZoneSameLocal(zone2);
        if (result == zdt) {
            throw new AssertionError("Expected different instance when zone differs");
        }
        if (!result.getZone().equals(zone2)) {
            throw new AssertionError("Expected zone to be changed to the new zone");
        }
        if (!result.toLocalDateTime().equals(zdt.toLocalDateTime())) {
            throw new AssertionError("Expected local date-time to be unchanged");
        }
    }

    private static void testWithZoneSameLocalRejectsNullZone() {
        ZoneId zone = ZoneId.of("Europe/Paris");
        LocalDateTime ldt = LocalDateTime.of(2020, 1, 1, 12, 0);
        ZonedDateTime zdt = ZonedDateTime.of(ldt, zone);
        try {
            zdt.withZoneSameLocal(null);
            throw new AssertionError("Expected NullPointerException for null zone");
        } catch (NullPointerException e) {
            // expected
            if (!"zone".equals(e.getMessage())) {
                throw new AssertionError("Expected NullPointerException with message 'zone'");
            }
        }
    }
}
