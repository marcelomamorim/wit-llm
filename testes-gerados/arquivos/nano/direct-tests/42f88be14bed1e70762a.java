/*
 * @test
 * @summary Test for LocalDateTime.minusHours(long)
 * @run main LocalDateTimeWitupTest
 */

import java.time.LocalDateTime;
import java.time.Month;

public class LocalDateTimeWitupTest {

    public static void main(String[] args) {
        testMinusHoursPositive();
        testMinusHoursZero();
        testMinusHoursNegative();
    }

    static void testMinusHoursPositive() {
        LocalDateTime dateTime = LocalDateTime.of(2023, Month.MARCH, 15, 10, 30);
        LocalDateTime result = dateTime.minusHours(5);
        LocalDateTime expected = LocalDateTime.of(2023, Month.MARCH, 15, 5, 30);
        if (!result.equals(expected)) {
            throw new AssertionError("Failed: minusHours positive case. Expected: " + expected + ", but got: " + result);
        }
    }

    static void testMinusHoursZero() {
        LocalDateTime dateTime = LocalDateTime.of(2023, Month.MARCH, 15, 10, 30);
        LocalDateTime result = dateTime.minusHours(0);
        if (!result.equals(dateTime)) {
            throw new AssertionError("Failed: minusHours zero case. Expected: " + dateTime + ", but got: " + result);
        }
    }

    static void testMinusHoursNegative() {
        LocalDateTime dateTime = LocalDateTime.of(2023, Month.MARCH, 15, 10, 30);
        LocalDateTime result = dateTime.minusHours(-3);
        LocalDateTime expected = LocalDateTime.of(2023, Month.MARCH, 15, 13, 30);
        if (!result.equals(expected)) {
            throw new AssertionError("Failed: minusHours negative case. Expected: " + expected + ", but got: " + result);
        }
    }
}
