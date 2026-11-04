/*
 * @test
 * @summary Tests for LocalDateTime.withMinute(int)
 * @run main LocalDateTimeWitupTest
 */

import java.time.LocalDateTime;
import java.time.LocalTime;

public class LocalDateTimeWitupTest {

    public static void main(String[] args) {
        testWithMinuteValid();
        testWithMinuteInvalid();
    }

    private static void testWithMinuteValid() {
        LocalDateTime dateTime = LocalDateTime.of(2023, 1, 1, 12, 30);
        int validMinute = 45;
        LocalDateTime result = dateTime.withMinute(validMinute);
        if (result == null) {
            throw new AssertionError("Result should not be null for valid minute");
        }
        if (result.getMinute() != validMinute) {
            throw new AssertionError("Expected minute: " + validMinute + ", but got: " + result.getMinute());
        }
        // Additional check: date part remains unchanged
        if (!result.toLocalDate().equals(dateTime.toLocalDate())) {
            throw new AssertionError("Date part should remain unchanged");
        }
        // Additional check: time part's other fields remain unchanged
        if (result.toLocalTime().getHour() != dateTime.toLocalTime().getHour() ||
            result.toLocalTime().getSecond() != dateTime.toLocalTime().getSecond() ||
            result.toLocalTime().getNano() != dateTime.toLocalTime().getNano()) {
            throw new AssertionError("Time fields other than minute should remain unchanged");
        }
    }

    private static void testWithMinuteInvalid() {
        LocalDateTime dateTime = LocalDateTime.of(2023, 1, 1, 12, 30);
        int invalidMinute = 60; // invalid, as valid range is 0-59
        try {
            dateTime.withMinute(invalidMinute);
            throw new AssertionError("Expected DateTimeException for invalid minute")
        } catch (java.time.DateTimeException e) {
            // Expected exception
        }
        invalidMinute = -1; // invalid negative
        try {
            dateTime.withMinute(invalidMinute);
            throw new AssertionError("Expected DateTimeException for negative minute")
        } catch (java.time.DateTimeException e) {
            // Expected exception
        }
    }
}
