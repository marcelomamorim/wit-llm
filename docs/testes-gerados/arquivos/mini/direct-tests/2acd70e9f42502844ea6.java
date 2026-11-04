/*
 * @test
 * @summary Test java.time.LocalDate.withDayOfMonth(int) method
 * @run main java.time.LocalDateWitupTest
 */

import java.time.LocalDate;
import java.time.Month;

public class LocalDateWitupTest {
    public static void main(String[] args) {
        testWithSameDayReturnsSameInstance();
        testWithDifferentDayReturnsNewInstance();
        testWithInvalidDayThrowsException();
    }

    private static void testWithSameDayReturnsSameInstance() {
        LocalDate date = LocalDate.of(2018, Month.JANUARY, 15);
        LocalDate result = date.withDayOfMonth(15);
        if (result != date) {
            throw new AssertionError("Expected same instance when dayOfMonth is unchanged");
        }
    }

    private static void testWithDifferentDayReturnsNewInstance() {
        LocalDate date = LocalDate.of(2018, Month.JANUARY, 15);
        LocalDate result = date.withDayOfMonth(20);
        if (result == date) {
            throw new AssertionError("Expected new instance when dayOfMonth is changed");
        }
        if (result.getYear() != 2018 || result.getMonth() != Month.JANUARY || result.getDayOfMonth() != 20) {
            throw new AssertionError("Result date has incorrect year, month or day");
        }
    }

    private static void testWithInvalidDayThrowsException() {
        LocalDate date = LocalDate.of(2018, Month.FEBRUARY, 15);
        try {
            date.withDayOfMonth(30); // February 30 is invalid
            throw new AssertionError("Expected exception for invalid dayOfMonth");
        } catch (java.time.DateTimeException e) {
            // Expected exception
        }
    }
}
