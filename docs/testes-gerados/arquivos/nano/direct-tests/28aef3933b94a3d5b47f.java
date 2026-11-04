/*
 * @test
 * @summary Test IsoChronology.dateNow(Clock) method for deterministic behavior
 * @run main IsoChronologyWitupTest
 */

import java.time.Clock;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.chrono.IsoChronology;

public class IsoChronologyWitupTest {

    public static void main(String[] args) {
        testDateNowWithFixedClock();
    }

    public static void testDateNowWithFixedClock() {
        // Create a fixed clock at a specific instant
        Instant fixedInstant = Instant.parse("2023-03-15T12:00:00Z");
        Clock fixedClock = Clock.fixed(fixedInstant, ZoneId.of("UTC"));
        // Call dateNow with the fixed clock
        LocalDate resultDate = IsoChronology.INSTANCE.dateNow(fixedClock);
        // Expected date is 2023-03-15
        LocalDate expectedDate = LocalDate.of(2023, 3, 15);
        if (!resultDate.equals(expectedDate)) {
            throw new AssertionError("Expected: " + expectedDate + ", but got: " + resultDate);
        }
    }
}
