/*
 * @test
 * @summary Test java.time.chrono.JapaneseDate.until(ChronoLocalDate) method
 * @run main JapaneseDateWitupTest
 */

import java.time.chrono.ChronoLocalDate;
import java.time.chrono.ChronoPeriod;
import java.time.chrono.JapaneseDate;
import java.time.chrono.JapaneseEra;
import java.time.Period;
import java.time.LocalDate;

public class JapaneseDateWitupTest {
    public static void main(String[] args) {
        testUntilWithValidDate();
        testUntilWithNullDate();
    }

    private static void testUntilWithValidDate() {
        // Create a JapaneseDate for Reiwa era year 1, May 1
        JapaneseDate startDate = JapaneseDate.of(JapaneseEra.REIWA, 1, 5, 1);
        // Create another JapaneseDate for Reiwa era year 2, May 1
        JapaneseDate endDate = JapaneseDate.of(JapaneseEra.REIWA, 2, 5, 1);

        ChronoPeriod period = startDate.until(endDate);

        // The period should be approximately 1 year
        if (period == null) {
            throw new AssertionError("until() returned null");
        }

        // The period years should be 1, months and days 0
        if (period.get(ChronoPeriod.YEARS) != 1 || period.get(ChronoPeriod.MONTHS) != 0 || period.get(ChronoPeriod.DAYS) != 0) {
            throw new AssertionError("Unexpected period: " + period);
        }
    }

    private static void testUntilWithNullDate() {
        JapaneseDate date = JapaneseDate.of(JapaneseEra.HEISEI, 1, 1, 8);
        try {
            date.until(null);
            throw new AssertionError("Expected NullPointerException not thrown");
        } catch (NullPointerException expected) {
            // Expected exception
        }
    }
}
