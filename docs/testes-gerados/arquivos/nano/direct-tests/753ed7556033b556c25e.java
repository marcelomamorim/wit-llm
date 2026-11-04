/*
 * @test
 * @summary Test LocalTime.atDate(LocalDate) method for correct behavior.
 * @run main LocalTimeWitupTest
 */

import java.time.LocalTime;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class LocalTimeWitupTest {

    public static void main(String[] args) {
        testAtDateWithValidDate();
        testAtDateWithMidnight();
        testAtDateWithEndOfDay();
    }

    static void testAtDateWithValidDate() {
        LocalTime time = LocalTime.of(12, 30, 45);
        LocalDate date = LocalDate.of(2021, 12, 25);
        LocalDateTime result = time.atDate(date);
        LocalDateTime expected = LocalDateTime.of(date, time);
        if (!result.equals(expected)) {
            throw new AssertionError("Failed: atDate with valid date")
                + " Expected: " + expected + " but got: " + result;
        }
    }

    static void testAtDateWithMidnight() {
        LocalTime time = LocalTime.MIDNIGHT;
        LocalDate date = LocalDate.of(2000, 1, 1);
        LocalDateTime result = time.atDate(date);
        LocalDateTime expected = LocalDateTime.of(date, time);
        if (!result.equals(expected)) {
            throw new AssertionError("Failed: atDate with midnight")
                + " Expected: " + expected + " but got: " + result;
        }
    }

    static void testAtDateWithEndOfDay() {
        LocalTime time = LocalTime.of(23, 59, 59, 999_999_999);
        LocalDate date = LocalDate.of(1999, 12, 31);
        LocalDateTime result = time.atDate(date);
        LocalDateTime expected = LocalDateTime.of(date, time);
        if (!result.equals(expected)) {
            throw new AssertionError("Failed: atDate with end of day")
                + " Expected: " + expected + " but got: " + result;
        }
    }
}
