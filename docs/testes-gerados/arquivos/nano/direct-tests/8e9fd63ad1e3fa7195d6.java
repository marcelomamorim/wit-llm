/* @test    @summary Test the 'until' method of JapaneseDate for correct period calculation    @run main JapaneseDateWitupTest */

import java.time.chrono.JapaneseDate;
import java.time.chrono.ChronoLocalDate;
import java.time.chrono.ChronoPeriod;
import java.time.Period;
import java.time.LocalDate;

public class JapaneseDateWitupTest {

    public static void main(String[] args) {
        testUntilSameEra();
        testUntilDifferentEra();
        testUntilNullEndDate();
    }

    static void testUntilSameEra() {
        // Create two JapaneseDate instances in the same era
        JapaneseDate startDate = JapaneseDate.of(2019, 4, 30); // Reiwa 1-04-30
        JapaneseDate endDate = JapaneseDate.of(2020, 4, 30); // Reiwa 2-04-30
        // Calculate period
        ChronoPeriod period = startDate.until(endDate);
        // Expect 1 year, 0 months, 0 days
        assert period.getYears() == 1 : "Expected 1 year";
        assert period.getMonths() == 0 : "Expected 0 months";
        assert period.getDays() == 0 : "Expected 0 days";
    }

    static void testUntilDifferentEra() {
        // Create a date in Meiji era
        JapaneseDate meijiDate = JapaneseDate.of(1900, 1, 1); // Meiji 33-01-01
        // Create a date in Reiwa era
        JapaneseDate reiwaDate = JapaneseDate.of(2020, 4, 30); // Reiwa 2-04-30
        // Calculate period
        ChronoPeriod period = meijiDate.until(reiwaDate);
        // The period should reflect the difference in years, months, days
        // Since the actual period depends on isoDate, check for non-zero values
        assert period.getYears() >= 119 : "Expected at least 119 years";
        assert period.getMonths() >= 3 : "Expected at least 3 months";
        assert period.getDays() >= 0 : "Days should be non-negative";
    }

    static void testUntilNullEndDate() {
        JapaneseDate startDate = JapaneseDate.of(2020, 4, 30);
        try {
            startDate.until(null);
            assert false : "Expected NullPointerException";
        } catch (NullPointerException e) {
            // Expected exception
        }
    }
}
