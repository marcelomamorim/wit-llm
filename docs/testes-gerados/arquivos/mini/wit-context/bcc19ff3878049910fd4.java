/*
 * @test
 * @summary Test IsoChronology.date(TemporalAccessor) method for null input and valid input
 * @run main IsoChronologyWitupTest
 */

import java.time.LocalDate;
import java.time.chrono.IsoChronology;
import java.time.temporal.TemporalAccessor;

public class IsoChronologyWitupTest {
    public static void main(String[] args) {
        testDateWithNull();
        testDateWithValidTemporalAccessor();
    }

    private static void testDateWithNull() {
        try {
            IsoChronology.INSTANCE.date(null);
            throw new AssertionError("Expected NullPointerException for null input");
        } catch (NullPointerException expected) {
            // Expected exception
        }
    }

    private static void testDateWithValidTemporalAccessor() {
        LocalDate now = LocalDate.now();
        LocalDate result = IsoChronology.INSTANCE.date(now);
        if (!now.equals(result)) {
            throw new AssertionError("Expected date to return the same LocalDate instance or equal value");
        }
    }
}
