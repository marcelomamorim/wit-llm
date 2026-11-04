/*
 * @test
 * @summary Test java.time.Clock.fixed method for deterministic behavior
 * @run main ClockWitupTest
 */

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;

public class ClockWitupTest {
    public static void main(String[] args) {
        testFixedClockWithEpoch();
        testFixedClockWithCustomInstant();
        testFixedClockWithNullInstant();
        testFixedClockWithNullZone();
    }

    static void testFixedClockWithEpoch() {
        Instant epoch = Instant.ofEpochSecond(0);
        ZoneId zone = ZoneId.of("UTC");
        Clock clock = Clock.fixed(epoch, zone);
        assert clock.instant().equals(epoch) : "Fixed clock should return the provided instant";
        assert clock.getZone().equals(zone) : "Clock zone should match the provided zone";
    }

    static void testFixedClockWithCustomInstant() {
        Instant customInstant = Instant.ofEpochSecond(1625097600); // 2021-07-01T00:00:00Z
        ZoneId zone = ZoneId.of("America/New_York");
        Clock clock = Clock.fixed(customInstant, zone);
        assert clock.instant().equals(customInstant) : "Fixed clock should return the custom instant";
        assert clock.getZone().equals(zone) : "Clock zone should match the custom zone";
    }

    static void testFixedClockWithNullInstant() {
        boolean exceptionThrown = false;
        try {
            Clock.fixed(null, ZoneId.of("UTC"));
        } catch (NullPointerException e) {
            exceptionThrown = true;
        }
        assert exceptionThrown : "Passing null as Instant should throw NullPointerException";
    }

    static void testFixedClockWithNullZone() {
        boolean exceptionThrown = false;
        try {
            Clock.fixed(Instant.now(), null);
        } catch (NullPointerException e) {
            exceptionThrown = true;
        }
        assert exceptionThrown : "Passing null as ZoneId should throw NullPointerException";
    }
}
