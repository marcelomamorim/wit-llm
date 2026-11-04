/*
 * @test
 * @summary Test java.time.Clock.millis() method for correctness
 * @run main ClockWitupTest
 */

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;

public class ClockWitupTest {
    public static void main(String[] args) {
        testMillisReturnsExpectedEpochMilli();
    }

    private static void testMillisReturnsExpectedEpochMilli() {
        // Create a fixed clock at a known instant
        Instant fixedInstant = Instant.ofEpochMilli(1234567890123L);
        Clock fixedClock = Clock.fixed(fixedInstant, ZoneId.of("UTC"));

        long millis = fixedClock.millis();

        if (millis != 1234567890123L) {
            throw new AssertionError("Expected millis to be 1234567890123 but was " + millis);
        }
    }
}
