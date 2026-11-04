/*
 * @test
 * @summary Verifies Clock.system(ZoneId) null handling, UTC zone handling, region zone preservation and current-time behavior
 * @run main ClockWitupTest
 */

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZoneOffset;

public class ClockWitupTest {

    public static void main(String[] args) {
        testNullZoneThrowsNPE();
        testUtcReturnsZoneOffsetUtc();
        testRegionZonePreserved();
        testInstantIsCurrentSystemTime();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testNullZoneThrowsNPE() {
        boolean thrown = false;
        try {
            Clock.system(null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "Clock.system(null) must throw NullPointerException");
    }

    static void testUtcReturnsZoneOffsetUtc() {
        Clock clock = Clock.system(ZoneOffset.UTC);
        check(clock != null, "Clock.system(ZoneOffset.UTC) must not return null");
        check(ZoneOffset.UTC.equals(clock.getZone()),
              "zone of UTC system clock must be ZoneOffset.UTC but was " + clock.getZone());
    }

    static void testRegionZonePreserved() {
        ZoneId paris = ZoneId.of("Europe/Paris");
        Clock clock = Clock.system(paris);
        check(paris.equals(clock.getZone()),
              "zone of system clock must match requested zone but was " + clock.getZone());
    }

    static void testInstantIsCurrentSystemTime() {
        Clock clock = Clock.system(ZoneOffset.UTC);
        long before = System.currentTimeMillis();
        Instant instant = clock.instant();
        long after = System.currentTimeMillis();
        check(instant != null, "instant() must not return null");
        long millis = instant.toEpochMilli();
        check(millis >= before - 1000 && millis <= after + 1000,
              "instant() out of expected range: " + millis + " not in [" + before + ", " + after + "]");
    }
}
