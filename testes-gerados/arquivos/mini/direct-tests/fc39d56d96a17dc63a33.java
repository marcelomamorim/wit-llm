/*
 * @test
 * @summary Test java.time.LocalDate.withDayOfYear(int) method behavior
 * @run main LocalDateWitupTest
 */

import java.time.LocalDate;

public class LocalDateWitupTest {
    public static void main(String[] args) {
        testWithDayOfYearReturnsSameInstanceIfSameDay();
        testWithDayOfYearReturnsNewInstanceIfDifferentDay();
        testWithDayOfYearWithValidDay();
    }

    private static void testWithDayOfYearReturnsSameInstanceIfSameDay() {
        LocalDate date = LocalDate.of(2018, 3, 1); // 60th day of year (non-leap)
        LocalDate result = date.withDayOfYear(60);
        if (result != date) {
            throw new AssertionError("Expected same instance when dayOfYear is unchanged");
        }
    }

    private static void testWithDayOfYearReturnsNewInstanceIfDifferentDay() {
        LocalDate date = LocalDate.of(2018, 3, 1); // 60th day
        LocalDate result = date.withDayOfYear(61);
        if (result == date) {
            throw new AssertionError("Expected different instance when dayOfYear changes");
        }
        if (result.getYear() != 2018 || result.getDayOfYear() != 61) {
            throw new AssertionError("Result date has incorrect year or dayOfYear");
        }
    }

    private static void testWithDayOfYearWithValidDay() {
        LocalDate date = LocalDate.of(2020, 1, 1); // Leap year
        LocalDate result = date.withDayOfYear(366); // last day of leap year
        if (result.getYear() != 2020 || result.getDayOfYear() != 366) {
            throw new AssertionError("Failed to set dayOfYear to 366 in leap year");
        }
    }
}