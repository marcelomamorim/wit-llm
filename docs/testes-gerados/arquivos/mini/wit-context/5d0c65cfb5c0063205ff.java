/*
 * @test
 * @summary Test java.time.LocalDate.until(ChronoLocalDate) method for null argument and normal behavior
 * @run main LocalDateWitupTest
 */

import java.time.LocalDate;
import java.time.Period;
import java.time.chrono.ChronoLocalDate;

public class LocalDateWitupTest {
    public static void main(String[] args) {
        testUntilNullArgument();
        testUntilNormalBehavior();
    }

    private static void testUntilNullArgument() {
        LocalDate date = LocalDate.of(2020, 1, 1);
        try {
            date.until(null);
            throw new AssertionError("Expected NullPointerException for null argument");
        } catch (NullPointerException expected) {
            // Expected exception
        }
    }

    private static void testUntilNormalBehavior() {
        LocalDate start = LocalDate.of(2019, 5, 15);
        LocalDate end = LocalDate.of(2020, 7, 10);
        Period p = start.until(end);
        // Expected: 1 year, 1 month, and (10 - 15) days adjusted
        // Calculation:
        // totalMonths = (2020*12+7) - (2019*12+5) = (24247) - (24233) = 14
        // days = 10 - 15 = -5
        // Since totalMonths > 0 and days < 0, totalMonths-- => 13
        // calcDate = start.plusMonths(13) = 2019-05-15 + 13 months = 2020-06-15
        // days = end.toEpochDay() - calcDate.toEpochDay() = days between 2020-07-10 and 2020-06-15 = 25
        // years = 13 / 12 = 1
        // months = 13 % 12 = 1
        // days = 25
        if (p.getYears() != 1) {
            throw new AssertionError("Expected 1 year but got " + p.getYears());
        }
        if (p.getMonths() != 1) {
            throw new AssertionError("Expected 1 month but got " + p.getMonths());
        }
        if (p.getDays() != 25) {
            throw new AssertionError("Expected 25 days but got " + p.getDays());
        }
    }
}
