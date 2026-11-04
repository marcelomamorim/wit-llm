/*
 * @test
 * @summary Deterministic tests for java.time.LocalDate.withMonth(int) method
 * @run main LocalDateWitupTest
 */

import java.time.LocalDate;
import java.time.DateTimeException;

public class LocalDateWitupTest {
    public static void main(String[] args) {
        testWithMonthSameMonthReturnsSameInstance();
        testWithMonthValidChange();
        testWithMonthInvalidMonthThrows();
    }

    private static void testWithMonthSameMonthReturnsSameInstance() {
        LocalDate date = LocalDate.of(2018, 5, 15);
        LocalDate result = date.withMonth(5);
        if (result != date) {
            throw new AssertionError("withMonth with same month should return same instance");
        }
    }

    private static void testWithMonthValidChange() {
        LocalDate date = LocalDate.of(2018, 1, 31);
        LocalDate changed = date.withMonth(2);
        // February 2018 has 28 days, so day should be adjusted to 28
        if (changed.getYear() != 2018 || changed.getMonthValue() != 2 || changed.getDayOfMonth() != 28) {
            throw new AssertionError("withMonth should adjust day to valid day of new month");
        }
    }

    private static void testWithMonthInvalidMonthThrows() {
        LocalDate date = LocalDate.of(2018, 1, 15);
        try {
            date.withMonth(0); // invalid month, less than 1
            throw new AssertionError("Expected DateTimeException for month=0");
        } catch (DateTimeException expected) {
        }
        try {
            date.withMonth(13); // invalid month, greater than 12
            throw new AssertionError("Expected DateTimeException for month=13");
        } catch (DateTimeException expected) {
        }
    }
}
