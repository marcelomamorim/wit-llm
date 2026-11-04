/*
 * @test
 * @summary Test java.time.chrono.IsoChronology.dateNow(Clock) method
 * @run main IsoChronologyWitupTest
 */

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.chrono.IsoChronology;
import java.util.Objects;

public class IsoChronologyWitupTest {
    public static void main(String[] args) {
        testDateNowWithFixedClock();
        testDateNowWithSystemUTCClock();
        testDateNowWithNullClock();
    }

    private static void testDateNowWithFixedClock() {
        // Arrange
        Instant fixedInstant = Instant.parse("2020-01-01T10:15:30.00Z");
        Clock fixedClock = Clock.fixed(fixedInstant, ZoneId.of("UTC"));
        IsoChronology iso = IsoChronology.INSTANCE;

        // Act
        LocalDate result = iso.dateNow(fixedClock);

        // Assert
        LocalDate expected = LocalDate.of(2020, 1, 1);
        if (!Objects.equals(result, expected)) {
            throw new AssertionError("dateNow with fixed clock returned " + result + ", expected " + expected);
        }
    }

    private static void testDateNowWithSystemUTCClock() {
        // Arrange
        Clock systemUTC = Clock.systemUTC();
        IsoChronology iso = IsoChronology.INSTANCE;

        // Act
        LocalDate result = iso.dateNow(systemUTC);

        // Assert
        LocalDate expected = LocalDate.now(systemUTC);
        if (!Objects.equals(result, expected)) {
            throw new AssertionError("dateNow with systemUTC clock returned " + result + ", expected " + expected);
        }
    }

    private static void testDateNowWithNullClock() {
        IsoChronology iso = IsoChronology.INSTANCE;
        try {
            iso.dateNow(null);
            throw new AssertionError("dateNow did not throw NullPointerException when clock is null");
        } catch (NullPointerException npe) {
            // Expected
        }
    }
}
