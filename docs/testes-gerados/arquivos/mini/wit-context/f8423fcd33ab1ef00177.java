/*
 * @test
 * @summary Test java.time.chrono.ThaiBuddhistDate.until(ChronoLocalDate) method
 * @run main ThaiBuddhistDateWitupTest
 */

import java.time.chrono.ChronoLocalDate;
import java.time.chrono.ChronoPeriod;
import java.time.chrono.ThaiBuddhistDate;
import java.time.LocalDate;

public class ThaiBuddhistDateWitupTest {
    public static void main(String[] args) {
        testUntilWithValidDate();
        testUntilWithNullDate();
    }

    private static void testUntilWithValidDate() {
        // Create a ThaiBuddhistDate for 2018-01-01 (ISO)
        ThaiBuddhistDate start = ThaiBuddhistDate.of(2561, 1, 1); // 2561 BE == 2018 CE
        // Create an end date as ChronoLocalDate (use ISO LocalDate)
        LocalDate endIso = LocalDate.of(2019, 1, 1);

        ChronoPeriod period = start.until(endIso);

        // The period should represent 1 year difference
        if (period == null) {
            throw new AssertionError("until returned null");
        }
        // The years part should be 1
        if (period.get(ChronoLocalDate.YEARS) != 1 && period.getUnits().contains(ChronoLocalDate.YEARS)) {
            // Defensive: if YEARS unit present, value must be 1
            throw new AssertionError("Expected 1 year difference, got " + period.get(ChronoLocalDate.YEARS));
        }
    }

    private static void testUntilWithNullDate() {
        ThaiBuddhistDate start = ThaiBuddhistDate.of(2561, 1, 1);
        try {
            start.until(null);
            throw new AssertionError("Expected NullPointerException when passing null to until");
        } catch (NullPointerException expected) {
            // Expected exception
        }
    }
}
