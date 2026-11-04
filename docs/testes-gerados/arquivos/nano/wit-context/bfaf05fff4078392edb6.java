/*
 * @test
 * @summary Tests for java.time.LocalTime methods based on WIT analysis.
 * @run main LocalTimeWitupTest
 */

import java.time.LocalTime;
import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;

public class LocalTimeWitupTest {

    public static void main(String[] args) {
        testNowWithValidClock();
        testNowWithNullClock();
    }

    public static void testNowWithValidClock() {
        Clock clock = Clock.systemUTC();
        LocalTime result = LocalTime.now(clock);
        if (result == null) {
            throw new AssertionError("Expected non-null LocalTime from now(clock)");
        }
        System.out.println("testNowWithValidClock passed: " + result);
    }

    public static void testNowWithNullClock() {
        try {
            LocalTime.now(null);
            throw new AssertionError("Expected NullPointerException when calling now(null)");
        } catch (NullPointerException e) {
            if (!"clock".equals(e.getMessage())) {
                throw new AssertionError("Unexpected message in NullPointerException: " + e.getMessage());
            }
            System.out.println("testNowWithNullClock passed: NullPointerException thrown as expected");
        }
    }
}
