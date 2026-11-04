/*
 * @test
 * @summary Test for Year.now(Clock clock) method
 * @run main YearWitupTest
 */

import java.time.Clock;
import java.time.LocalDate;
import java.time.Year;

public class YearWitupTest {

    public static void main(String[] args) {
        testNowWithSystemDefaultClock();
        testNowWithFixedClock();
    }

    static void testNowWithSystemDefaultClock() {
        Year year = Year.now(Clock.systemDefaultZone());
        int currentYear = LocalDate.now().getYear();
        assert year.getValue() == currentYear : "Expected year: " + currentYear + ", but got: " + year.getValue();
    }

    static void testNowWithFixedClock() {
        long fixedEpochDay = 0L; // corresponds to 1970-01-01
        Clock fixedClock = Clock.fixed(java.time.Instant.ofEpochMilli(0), java.time.ZoneOffset.UTC);
        Year year = Year.now(fixedClock);
        int expectedYear = 1970;
        assert year.getValue() == expectedYear : "Expected year: " + expectedYear + ", but got: " + year.getValue();
    }
}
