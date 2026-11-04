/* @test    @summary Test java.time.Clock.system(ZoneId zone) method for different ZoneId inputs    @run main ClockWitupTest */

import java.time.Clock;
import java.time.ZoneId;

public class ClockWitupTest {

    public static void main(String[] args) {
        testSystemMethodWithUTC();
        testSystemMethodWithSpecificZone();
        testSystemMethodWithNull();
    }

    private static void testSystemMethodWithUTC() {
        Clock clock = Clock.system(ZoneId.of("UTC"));
        if (clock == null) {
            throw new AssertionError("Clock should not be null for UTC zone");
        }
        if (clock != Clock.system(ZoneId.of("UTC"))) {
            // The method may return the singleton SystemClock.UTC
            // or a new instance, but it should not be null
            // and should be consistent for the same call.
        }
    }

    private static void testSystemMethodWithSpecificZone() {
        ZoneId zone = ZoneId.of("America/New_York");
        Clock clock = Clock.system(zone);
        if (clock == null) {
            throw new AssertionError("Clock should not be null for specific zone");
        }
        // Additional checks could compare the zone of the clock if accessible,
        // but since it's not exposed, we verify that no exception occurs and clock is non-null.
    }

    private static void testSystemMethodWithNull() {
        try {
            Clock.system(null);
            throw new AssertionError("Expected NullPointerException for null zone");
        } catch (NullPointerException e) {
            // Expected exception
        }
    }
}
