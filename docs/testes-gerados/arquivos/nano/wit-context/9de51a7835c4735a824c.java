/*
 * @test
 * @summary Test for LocalDateTime.withSecond(int second) method.
 * @run main LocalDateTimeWitupTest
 */

import java.time.LocalDateTime;
import java.time.LocalTime;

public class LocalDateTimeWitupTest {

    public static void main(String[] args) {
        testWithSecondValid();
        testWithSecondBelowMinimum();
        testWithSecondAboveMaximum();
        testWithSecondEqualMinimum();
        testWithSecondEqualMaximum();
    }

    private static void testWithSecondValid() {
        LocalDateTime dateTime = LocalDateTime.of(2023, 3, 15, 10, 30, 45);
        int validSecond = 15;
        LocalDateTime result = dateTime.withSecond(validSecond);
        if (result.getSecond() != validSecond) {
            throw new AssertionError("Expected second: " + validSecond + ", but got: " + result.getSecond());
        }
    }

    private static void testWithSecondBelowMinimum() {
        LocalDateTime dateTime = LocalDateTime.of(2023, 3, 15, 10, 30, 45);
        int invalidSecond = -1;
        try {
            dateTime.withSecond(invalidSecond);
            throw new AssertionError("Expected DateTimeException for second: " + invalidSecond);
        } catch (java.time.DateTimeException e) {
            // Expected exception
        }
    }

    private static void testWithSecondAboveMaximum() {
        LocalDateTime dateTime = LocalDateTime.of(2023, 3, 15, 10, 30, 45);
        int invalidSecond = 60;
        try {
            dateTime.withSecond(invalidSecond);
            throw new AssertionError("Expected DateTimeException for second: " + invalidSecond);
        } catch (java.time.DateTimeException e) {
            // Expected exception
        }
    }

    private static void testWithSecondEqualMinimum() {
        LocalDateTime dateTime = LocalDateTime.of(2023, 3, 15, 10, 30, 45);
        int minSecond = 0;
        LocalDateTime result = dateTime.withSecond(minSecond);
        if (result.getSecond() != minSecond) {
            throw new AssertionError("Expected second: " + minSecond + ", but got: " + result.getSecond());
        }
    }

    private static void testWithSecondEqualMaximum() {
        LocalDateTime dateTime = LocalDateTime.of(2023, 3, 15, 10, 30, 45);
        int maxSecond = 59;
        LocalDateTime result = dateTime.withSecond(maxSecond);
        if (result.getSecond() != maxSecond) {
            throw new AssertionError("Expected second: " + maxSecond + ", but got: " + result.getSecond());
        }
    }
}
