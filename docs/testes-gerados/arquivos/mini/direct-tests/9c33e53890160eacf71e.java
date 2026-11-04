/*
 * @test
 * @summary Test LocalDateTime.of(LocalDate, LocalTime) method
 * @run main LocalDateTimeWitupTest
 */

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.LocalDateTime;
import java.util.Objects;

public class LocalDateTimeWitupTest {
    public static void main(String[] args) {
        testOfWithValidDateTime();
        testOfWithNullDate();
        testOfWithNullTime();
    }

    private static void testOfWithValidDateTime() {
        LocalDate date = LocalDate.of(2020, 1, 15);
        LocalTime time = LocalTime.of(10, 30);
        LocalDateTime ldt = LocalDateTime.of(date, time);
        if (!Objects.equals(ldt.toLocalDate(), date)) {
            throw new AssertionError("LocalDate part mismatch");
        }
        if (!Objects.equals(ldt.toLocalTime(), time)) {
            throw new AssertionError("LocalTime part mismatch");
        }
    }

    private static void testOfWithNullDate() {
        LocalTime time = LocalTime.of(10, 30);
        try {
            LocalDateTime.of(null, time);
            throw new AssertionError("Expected NullPointerException for null date");
        } catch (NullPointerException e) {
            if (!"date".equals(e.getMessage())) {
                throw new AssertionError("NullPointerException message mismatch for null date");
            }
        }
    }

    private static void testOfWithNullTime() {
        LocalDate date = LocalDate.of(2020, 1, 15);
        try {
            LocalDateTime.of(date, null);
            throw new AssertionError("Expected NullPointerException for null time");
        } catch (NullPointerException e) {
            if (!"time".equals(e.getMessage())) {
                throw new AssertionError("NullPointerException message mismatch for null time");
            }
        }
    }
}
