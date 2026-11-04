/*
 * @test
 * @summary Tests for java.time.OffsetTime
 * @run main OffsetTimeWitupTest
 */

import java.time.OffsetTime;
import java.time.ZoneOffset;
import java.time.Clock;
import java.time.Instant;

public class OffsetTimeWitupTest {

    public static void main(String[] args) {
        testNowWithValidClock();
        testNowWithNullClock();
    }

    public static void testNowWithValidClock() {
        Clock clock = Clock.systemUTC();
        OffsetTime result = OffsetTime.now(clock);
        if (result == null) {
            throw new AssertionError("OffsetTime.now(Clock) returned null");
        }
        // Additional observable checks can be added if needed
    }

    public static void testNowWithNullClock() {
        try {
            OffsetTime.now(null);
            throw new AssertionError("Expected NullPointerException not thrown");
        } catch (NullPointerException e) {
            // Expected exception
        }
    }
}
