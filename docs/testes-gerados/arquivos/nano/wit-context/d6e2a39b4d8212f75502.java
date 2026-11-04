/*
 * @test
 * @summary Test for OffsetDateTime.now(Clock clock) method, including null input handling.
 * @run main OffsetDateTimeWitupTest
 */

import java.time.OffsetDateTime;
import java.time.Clock;
import java.time.Instant;
import java.time.ZoneOffset;

public class OffsetDateTimeWitupTest {

    public static void main(String[] args) {
        testNowWithValidClock();
        testNowWithNullClock();
    }

    static void testNowWithValidClock() {
        Clock clock = Clock.systemUTC();
        OffsetDateTime odt = OffsetDateTime.now(clock);
        if (odt == null) {
            throw new AssertionError("OffsetDateTime.now(Clock) returned null with valid clock");
        }
        System.out.println("testNowWithValidClock passed: " + odt);
    }

    static void testNowWithNullClock() {
        try {
            OffsetDateTime.now(null);
            throw new AssertionError("Expected NullPointerException not thrown");
        } catch (NullPointerException e) {
            if (!"clock".equals(e.getMessage())) {
                throw new AssertionError("NullPointerException message mismatch", e);
            }
            System.out.println("testNowWithNullClock passed: NullPointerException thrown as expected");
        }
    }
}
