/*
 * @test
 * @summary Test java.time.LocalDate.now(Clock) method behavior including null clock handling
 * @run main java.time.LocalDateWitupTest
 */

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.Objects;

public class LocalDateWitupTest {
    public static void main(String[] args) {
        testNowWithValidClock();
        testNowWithNullClock();
        System.out.println("All tests passed.");
    }

    private static void testNowWithValidClock() {
        // Use a fixed clock at epoch second 0 UTC
        Instant fixedInstant = Instant.ofEpochSecond(0L);
        ZoneId zone = ZoneOffset.UTC;
        Clock fixedClock = Clock.fixed(fixedInstant, zone);

        LocalDate expected = LocalDate.ofInstant(fixedInstant, zone);
        LocalDate actual = LocalDate.now(fixedClock);

        if (!Objects.equals(expected, actual)) {
            throw new AssertionError("LocalDate.now(clock) returned " + actual + ", expected " + expected);
        }
    }

    private static void testNowWithNullClock() {
        try {
            LocalDate.now(null);
            throw new AssertionError("Expected NullPointerException for null clock");
        } catch (NullPointerException e) {
            // Expected exception
        }
    }
}
