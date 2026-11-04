/*
 * @test
 * @summary Test LocalDateTime.plusHours() method for correct addition of hours.
 * @run main LocalDateTimeWitupTest
 */

import java.time.LocalDateTime;
import java.time.Month;

public class LocalDateTimeWitupTest {

    public static void main(String[] args) {
        testPlusHoursPositive();
        testPlusHoursZero();
        testPlusHoursNegative();
        testPlusHoursLarge();
    }

    static void testPlusHoursPositive() {
        LocalDateTime dateTime = LocalDateTime.of(2023, Month.MARCH, 15, 10, 30);
        LocalDateTime result = dateTime.plusHours(5);
        LocalDateTime expected = LocalDateTime.of(2023, Month.MARCH, 15, 15, 30);
        if (!result.equals(expected)) {
            throw new AssertionError("Failed: plusHours(5) -> " + result);
        }
    }

    static void testPlusHoursZero() {
        LocalDateTime dateTime = LocalDateTime.of(2023, Month.MARCH, 15, 10, 30);
        LocalDateTime result = dateTime.plusHours(0);
        if (!result.equals(dateTime)) {
            throw new AssertionError("Failed: plusHours(0) -> " + result);
        }
    }

    static void testPlusHoursNegative() {
        LocalDateTime dateTime = LocalDateTime.of(2023, Month.MARCH, 15, 10, 30);
        LocalDateTime result = dateTime.plusHours(-3);
        LocalDateTime expected = LocalDateTime.of(2023, Month.MARCH, 15, 7, 30);
        if (!result.equals(expected)) {
            throw new AssertionError("Failed: plusHours(-3) -> " + result);
        }

        // Crossing previous day
        LocalDateTime dateTime2 = LocalDateTime.of(2023, Month.MARCH, 1, 2, 0);
        LocalDateTime result2 = dateTime2.plusHours(-3);
        LocalDateTime expected2 = LocalDateTime.of(2023, Month.FEBRUARY, 28, 23, 0);
        if (!result2.equals(expected2)) {
            throw new AssertionError("Failed: crossing previous day plusHours(-3) -> " + result2);
        }
    }

    static void testPlusHoursLarge() {
        LocalDateTime dateTime = LocalDateTime.of(2023, Month.MARCH, 15, 10, 30);
        long largeHours = 1000000L;
        LocalDateTime result = dateTime.plusHours(largeHours);
        // Calculate expected date and time
        long totalHours = largeHours + 10;
        long daysToAdd = totalHours / 24;
        long remainingHours = totalHours % 24;
        LocalDateTime expected = dateTime.plusDays(daysToAdd).withHour((int) remainingHours).withMinute(30);
        if (!result.equals(expected)) {
            throw new AssertionError("Failed: large plusHours -> " + result);
        }
    }
}
