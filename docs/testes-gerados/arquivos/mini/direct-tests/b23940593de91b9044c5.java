/*
 * @test
 * @summary Test java.time.Year.now(Clock) method behavior
 * @run main YearWitupTest
 */

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDate;
import java.time.Year;
import java.time.ZoneId;

public class YearWitupTest {
    public static void main(String[] args) {
        testNowWithFixedClock();
        testNowWithSystemUTCClock();
    }

    private static void testNowWithFixedClock() {
        // Fixed instant for 2018-09-01T00:00:00Z
        Instant fixedInstant = Instant.parse("2018-09-01T00:00:00Z");
        Clock fixedClock = Clock.fixed(fixedInstant, ZoneId.of("UTC"));

        Year year = Year.now(fixedClock);
        if (year.getValue() != 2018) {
            throw new AssertionError("Expected year 2018 but got " + year.getValue());
        }
    }

    private static void testNowWithSystemUTCClock() {
        Clock systemUTC = Clock.systemUTC();
        Year year = Year.now(systemUTC);
        int currentYear = LocalDate.now(systemUTC).getYear();
        if (year.getValue() != currentYear) {
            throw new AssertionError("Expected year " + currentYear + " but got " + year.getValue());
        }
    }
}
