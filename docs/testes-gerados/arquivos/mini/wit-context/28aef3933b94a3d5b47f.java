/*
 * @test
 * @summary Test IsoChronology.dateNow(Clock) method for null and normal behavior
 * @run main IsoChronologyWitupTest
 */

import java.time.Clock;
import java.time.LocalDate;
import java.time.chrono.IsoChronology;
import java.util.Objects;

public class IsoChronologyWitupTest {
    public static void main(String[] args) {
        testDateNowWithNullClock();
        testDateNowWithSystemUTCClock();
    }

    private static void testDateNowWithNullClock() {
        IsoChronology iso = IsoChronology.INSTANCE;
        try {
            iso.dateNow(null);
            throw new AssertionError("Expected NullPointerException for null clock");
        } catch (NullPointerException npe) {
            // Expected exception
            if (!"clock".equals(npe.getMessage())) {
                throw new AssertionError("NullPointerException message mismatch", npe);
            }
        }
    }

    private static void testDateNowWithSystemUTCClock() {
        IsoChronology iso = IsoChronology.INSTANCE;
        Clock clock = Clock.systemUTC();
        LocalDate expected = LocalDate.now(clock);
        LocalDate result = iso.dateNow(clock);
        if (!Objects.equals(expected, result)) {
            throw new AssertionError("dateNow returned " + result + ", expected " + expected);
        }
    }
}
