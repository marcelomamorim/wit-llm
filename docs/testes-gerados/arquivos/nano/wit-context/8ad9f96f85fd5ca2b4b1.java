/*
 * @test
 * @summary Test for LocalDate.withMonth(int)
 * @run main LocalDateWitupTest
 */

import java.time.LocalDate;
import java.time.temporal.ChronoField;

public class LocalDateWitupTest {

    public static void main(String[] args) {
        testWithMonthSameMonth();
        testWithMonthValidValue();
        testWithMonthInvalidValue();
    }

    private static void testWithMonthSameMonth() {
        LocalDate date = LocalDate.of(2023, 5, 15);
        LocalDate result = date.withMonth(5);
        if (result != date) {
            throw new AssertionError("Expected same instance when month is unchanged");
        }
    }

    private static void testWithMonthValidValue() {
        LocalDate date = LocalDate.of(2023, 1, 31);
        LocalDate result = date.withMonth(2);
        int expectedDay = Math.min(28, date.getDayOfMonth());
        if (result.getYear() != 2023 || result.getMonthValue() != 2 || result.getDayOfMonth() != expectedDay) {
            throw new AssertionError("Failed to set valid month with correct day");
        }
    }

    private static void testWithMonthInvalidValue() {
        LocalDate date = LocalDate.of(2023, 1, 15);
        boolean exceptionThrown = false;
        try {
            date.withMonth(0);
        } catch (Exception e) {
            exceptionThrown = e instanceof DateTimeException;
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected DateTimeException for invalid month 0");
        }
        exceptionThrown = false;
        try {
            date.withMonth(13);
        } catch (Exception e) {
            exceptionThrown = e instanceof DateTimeException;
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected DateTimeException for invalid month 13");
        }
    }
}
