/*
 * @test
 * @summary Test LocalDateTime.of(LocalDate, LocalTime) for null argument checks and normal creation
 * @run main LocalDateTimeWitupTest
 */

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Objects;

public class LocalDateTimeWitupTest {
    public static void main(String[] args) {
        testNullDateThrows();
        testNullTimeThrows();
        testValidCreation();
    }

    private static void testNullDateThrows() {
        LocalTime time = LocalTime.of(12, 0);
        try {
            LocalDateTime.of(null, time);
            throw new AssertionError("Expected NullPointerException for null date");
        } catch (NullPointerException e) {
            // expected
            if (!"date".equals(e.getMessage())) {
                throw new AssertionError("NullPointerException message mismatch for null date");
            }
        }
    }

    private static void testNullTimeThrows() {
        LocalDate date = LocalDate.of(2000, 1, 1);
        try {
            LocalDateTime.of(date, null);
            throw new AssertionError("Expected NullPointerException for null time");
        } catch (NullPointerException e) {
            // expected
            if (!"time".equals(e.getMessage())) {
                throw new AssertionError("NullPointerException message mismatch for null time");
            }
        }
    }

    private static void testValidCreation() {
        LocalDate date = LocalDate.of(2020, 5, 20);
        LocalTime time = LocalTime.of(15, 30);
        LocalDateTime ldt = LocalDateTime.of(date, time);
        if (!date.equals(ldt.toLocalDate())) {
            throw new AssertionError("LocalDateTime date part mismatch");
        }
        if (!time.equals(ldt.toLocalTime())) {
            throw new AssertionError("LocalDateTime time part mismatch");
        }
    }
}
