/*
 * @test
 * @summary Testa o método Instant.now(Clock)
 * @run main InstantWitupTest
 */

import java.time.Clock;
import java.time.Instant;

public class InstantWitupTest {

    public static void main(String[] args) {
        testNowWithValidClock();
        testNowWithNullClock();
    }

    static void testNowWithValidClock() {
        Clock clock = Clock.systemUTC();
        Instant result = Instant.now(clock);
        if (result == null) {
            throw new AssertionError("Expected non-null Instant for valid clock");
        }
        System.out.println("testNowWithValidClock passed");
    }

    static void testNowWithNullClock() {
        try {
            Instant.now(null);
            throw new AssertionError("Expected NullPointerException for null clock");
        } catch (NullPointerException e) {
            if (!"clock".equals(e.getMessage())) {
                throw new AssertionError("Unexpected message in NullPointerException: " + e.getMessage());
            }
            System.out.println("testNowWithNullClock passed");
        }
    }
}
