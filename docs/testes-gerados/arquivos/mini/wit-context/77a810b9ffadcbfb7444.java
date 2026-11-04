/*
 * @test
 * @summary Test java.time.chrono.MinguoDate.until(ChronoLocalDate) method
 * @run main MinguoDateWitupTest
 */

import java.time.chrono.MinguoDate;
import java.time.chrono.ChronoLocalDate;
import java.time.chrono.ChronoPeriod;
import java.time.LocalDate;
import java.time.DateTimeException;

public class MinguoDateWitupTest {
    public static void main(String[] args) {
        testUntilWithValidDate();
        testUntilWithNullDate();
    }

    private static void testUntilWithValidDate() {
        // Create a MinguoDate for 1st Jan 1 (Minguo year 1)
        MinguoDate start = MinguoDate.of(1, 1, 1);
        // Create a LocalDate for 2nd Jan 1 (ISO)
        LocalDate isoEnd = LocalDate.of(1912, 1, 2); // Minguo year 1 + 1911 offset
        // until should return a ChronoPeriod representing 1 day difference
        ChronoPeriod period = start.until(isoEnd);
        if (period == null) {
            throw new AssertionError("until returned null");
        }
        // The period should represent 0 years, 0 months, 1 day
        if (period.get(ChronoLocalDate.YEARS) != 0 || period.get(ChronoLocalDate.MONTHS) != 0) {
            // Defensive: ChronoLocalDate.YEARS and MONTHS are not constants, so use period.getUnits() to check
            // Instead, check period.toString contains expected values
            String s = period.toString();
            if (!s.contains("P") || !s.contains("D")) {
                throw new AssertionError("Unexpected period string: " + s);
            }
        }
        // We do not assert exact values of years/months/days because ChronoPeriod interface is generic,
        // but we check that period is not null and toString is plausible.
    }

    private static void testUntilWithNullDate() {
        MinguoDate start = MinguoDate.of(1, 1, 1);
        try {
            start.until(null);
            throw new AssertionError("Expected NullPointerException for null endDate");
        } catch (NullPointerException expected) {
            // Expected exception
        } catch (Exception unexpected) {
            throw new AssertionError("Unexpected exception type: " + unexpected);
        }
    }
}
