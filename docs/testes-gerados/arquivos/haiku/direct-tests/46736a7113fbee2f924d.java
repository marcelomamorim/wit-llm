/*
 * @test
 * @summary Tests that Clock.fixed returns a clock with the given instant and zone and rejects null arguments
 * @run main ClockWitupTest
 */

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZoneOffset;

public class ClockWitupTest {

    public static void main(String[] args) {
        Instant inst = Instant.ofEpochSecond(1_000_000_000L, 123_456_789);
        ZoneId zone = ZoneId.of("UTC");
        Clock c = Clock.fixed(inst, zone);

        check(inst.equals(c.instant()), "instant() should return the fixed instant");
        check(inst.equals(c.instant()), "instant() should be stable across calls");
        check(inst.toEpochMilli() == c.millis(), "millis() should match fixed instant");
        check(zone.equals(c.getZone()), "getZone() should return the given zone");

        ZoneId other = ZoneOffset.ofHours(2);
        Clock c2 = c.withZone(other);
        check(other.equals(c2.getZone()), "withZone() should change the zone");
        check(inst.equals(c2.instant()), "withZone() should keep the fixed instant");

        expectNullPointerException(null, zone);
        expectNullPointerException(inst, null);
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException("Failed: " + message);
        }
    }

    static void expectNullPointerException(Instant instant, ZoneId zone) {
        try {
            Clock.fixed(instant, zone);
        } catch (NullPointerException e) {
            return;
        }
        throw new RuntimeException("Failed: NullPointerException expected for null argument");
    }
}
