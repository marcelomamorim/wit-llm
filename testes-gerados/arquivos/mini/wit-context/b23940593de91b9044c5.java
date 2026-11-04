/*
 * @test
 * @summary Test java.time.Year.now(Clock) method for normal and exceptional behavior
 * @run main YearWitupTest
 */

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDate;
import java.time.Year;
import java.time.ZoneId;
import java.time.ZoneOffset;

public class YearWitupTest {
    public static void main(String[] args) {
        testNowWithValidClock();
        testNowWithNullClock();
    }

    private static void testNowWithValidClock() {
        // Use a fixed clock for deterministic test
        Instant fixedInstant = Instant.parse("2018-09-01T10:15:30.00Z");
        Clock fixedClock = Clock.fixed(fixedInstant, ZoneOffset.UTC);
        Year year = Year.now(fixedClock);
        int expectedYear = LocalDate.now(fixedClock).getYear();
        if (year.getValue() != expectedYear) {
            throw new AssertionError("Year.now(clock) returned " + year.getValue() + ", expected " + expectedYear);
        }
    }

    private static void testNowWithNullClock() {
        try {
            Year.now(null);
            throw new AssertionError("Expected NullPointerException when clock is null");
        } catch (NullPointerException e) {
            // Expected exception
        }
    }
}
