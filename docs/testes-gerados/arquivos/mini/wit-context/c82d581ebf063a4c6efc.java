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
        testWithZoneSameLocalNullZone();
        testWithZoneSameLocalSameZoneReturnsThis();
        testWithZoneSameLocalDifferentZoneCreatesNew();
    }

    private static void testWithZoneSameLocalNullZone() {
        ZonedDateTime zdt = ZonedDateTime.of(LocalDateTime.of(2018, 9, 1, 12, 0), ZoneOffset.UTC);
        try {
            zdt.withZoneSameLocal(null);
            throw new AssertionError("Expected NullPointerException for null zone");
        } catch (NullPointerException ex) {
            // expected
        }
    }

    private static void testWithZoneSameLocalSameZoneReturnsThis() {
        ZoneId zone = ZoneOffset.UTC;
        ZonedDateTime zdt = ZonedDateTime.of(LocalDateTime.of(2018, 9, 1, 12, 0), zone);
        ZonedDateTime result = zdt.withZoneSameLocal(zone);
        if (result != zdt) {
            throw new AssertionError("Expected same instance when zone is unchanged");
        }
    }

    private static void testWithZoneSameLocalDifferentZoneCreatesNew() {
        ZoneId zone1 = ZoneOffset.UTC;
        ZoneId zone2 = ZoneId.of("Europe/Paris");
        ZonedDateTime zdt = ZonedDateTime.of(LocalDateTime.of(2018, 9, 1, 12, 0), zone1);
        ZonedDateTime result = zdt.withZoneSameLocal(zone2);
        if (result == zdt) {
            throw new AssertionError("Expected different instance when zone is changed");
        }
        if (!result.getZone().equals(zone2)) {
            throw new AssertionError("Expected zone to be updated to new zone");
        }
        if (!result.toLocalDateTime().equals(zdt.toLocalDateTime())) {
            throw new AssertionError("Expected local date-time to be unchanged");
        }
    }
}
