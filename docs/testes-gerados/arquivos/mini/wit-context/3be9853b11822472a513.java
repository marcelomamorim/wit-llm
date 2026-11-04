/*
 * @test
 * @summary Test java.time.LocalDate.withYear(int) method for valid and invalid year inputs
 * @run main LocalDateWitupTest
 */

import java.time.LocalDate;
import java.time.DateTimeException;

public class LocalDateWitupTest {
    public static void main(String[] args) {
        testWithYearSameYearReturnsThis();
        testWithYearValidChange();
        testWithYearInvalidYearThrows();
    }

    private static void testWithYearSameYearReturnsThis() {
        LocalDate date = LocalDate.of(2018, 5, 20);
        LocalDate result = date.withYear(2018);
        if (result != date) {
            throw new AssertionError("withYear should return same instance if year is unchanged");
        }
    }

    private static void testWithYearValidChange() {
        LocalDate date = LocalDate.of(2018, 5, 20);
        LocalDate changed = date.withYear(2019);
        if (changed.getYear() != 2019) {
            throw new AssertionError("withYear did not change the year correctly");
        }
        if (changed.getMonthValue() != 5 || changed.getDayOfMonth() != 20) {
            throw new AssertionError("withYear changed month or day unexpectedly");
        }
    }

    private static void testWithYearInvalidYearThrows() {
        LocalDate date = LocalDate.of(2018, 5, 20);
        int invalidYearLow = -1000000; // well below valid range
        int invalidYearHigh = 1000000; // well above valid range

        boolean thrownLow = false;
        try {
            date.withYear(invalidYearLow);
        } catch (DateTimeException e) {
            thrownLow = true;
        }
        if (!thrownLow) {
            throw new AssertionError("withYear should throw DateTimeException for year " + invalidYearLow);
        }

        boolean thrownHigh = false;
        try {
            date.withYear(invalidYearHigh);
        } catch (DateTimeException e) {
            thrownHigh = true;
        }
        if (!thrownHigh) {
            throw new AssertionError("withYear should throw DateTimeException for year " + invalidYearHigh);
        }
    }
}
