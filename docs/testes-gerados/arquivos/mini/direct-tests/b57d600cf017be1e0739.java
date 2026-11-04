/*
 * @test
 * @summary Test java.time.LocalDate.now(Clock) method behavior
 * @run main LocalDateWitupTest
 */

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.Objects;

public class LocalDateWitupTest {
    public static void main(String[] args) {
        testNowWithFixedClock();
        testNowWithSystemUTCClock();
        testNowWithNullClock();
    }

    private static void testNowWithFixedClock() {
        Instant fixedInstant = Instant.parse("2020-01-01T10:15:30Z");
        ZoneId zone = ZoneOffset.UTC;
        Clock fixedClock = Clock.fixed(fixedInstant, zone);

        LocalDate result = LocalDate.now(fixedClock);

        // The LocalDate should correspond to the date part of the fixed instant in the zone
        LocalDate expected = LocalDate.of(2020, 1, 1);
        if (!result.equals(expected)) {
            throw new AssertionError("Expected " + expected + " but got " + result);
        }
    }

    private static void testNowWithSystemUTCClock() {
        Clock utcClock = Clock.systemUTC();
        LocalDate result = LocalDate.now(utcClock);

        // The result should be equal to LocalDate.now() in UTC zone
        LocalDate expected = LocalDate.ofInstant(Instant.now(utcClock), ZoneOffset.UTC);

        // Allowing for possible small time difference, test that result equals expected or is close
        if (!result.equals(expected)) {
            throw new AssertionError("Expected " + expected + " but got " + result);
        }
    }

    private static void testNowWithNullClock() {
        try {
            LocalDate.now(null);
            throw new AssertionError("Expected NullPointerException for null clock");
        } catch (NullPointerException npe) {
            // Expected
            if (!"clock".equals(npe.getMessage())) {
                throw new AssertionError("NullPointerException message mismatch: " + npe.getMessage());
            }
        }
    }
}
