/*
 * @test
 * @summary Tests for LocalDateTime.withHour(int)
 * @run main LocalDateTimeWitupTest
 */

import java.time.LocalDateTime;
import java.time.LocalTime;

public class LocalDateTimeWitupTest {

    public static void main(String[] args) {
        testWithHourValid();
        testWithHourInvalid();
    }

    private static void testWithHourValid() {
        LocalDateTime dateTime = LocalDateTime.of(2023, 3, 15, 10, 30);
        int validHour = 15;
        LocalDateTime result = dateTime.withHour(validHour);
        if (result.getHour() != validHour) {
            throw new AssertionError("Expected hour: " + validHour + ", but got: " + result.getHour());
        }
        if (!result.toLocalDate().equals(dateTime.toLocalDate())) {
            throw new AssertionError("Date part changed unexpectedly.");
        }
        if (!result.toLocalTime().equals(LocalTime.of(validHour, dateTime.toLocalTime().getMinute()))) {
            throw new AssertionError("Time part changed unexpectedly.");
        }
    }

    private static void testWithHourInvalid() {
        LocalDateTime dateTime = LocalDateTime.of(2023, 3, 15, 10, 30);
        int invalidHour = -1; // invalid, as hour should be between 0 and 23
        try {
            dateTime.withHour(invalidHour);
            throw new AssertionError("Expected DateTimeException for invalid hour: " + invalidHour);
        } catch (java.time.DateTimeException e) {
            // expected
        }
        invalidHour = 24; // invalid
        try {
            dateTime.withHour(invalidHour);
            throw new AssertionError("Expected DateTimeException for invalid hour: " + invalidHour);
        } catch (java.time.DateTimeException e) {
            // expected
        }
    }
}
