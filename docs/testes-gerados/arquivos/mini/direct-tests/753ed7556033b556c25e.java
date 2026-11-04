/*
 * @test
 * @summary Test java.time.LocalTime.atDate(LocalDate) method
 * @run main LocalTimeWitupTest
 */

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class LocalTimeWitupTest {
    public static void main(String[] args) {
        testAtDateBasic();
        testAtDateEpochDay();
    }

    private static void testAtDateBasic() {
        LocalTime time = LocalTime.of(10, 20, 30, 400);
        LocalDate date = LocalDate.of(2020, 5, 15);
        LocalDateTime dt = time.atDate(date);
        if (!dt.toLocalDate().equals(date)) {
            throw new AssertionError("Date part mismatch: expected " + date + " but got " + dt.toLocalDate());
        }
        if (!dt.toLocalTime().equals(time)) {
            throw new AssertionError("Time part mismatch: expected " + time + " but got " + dt.toLocalTime());
        }
    }

    private static void testAtDateEpochDay() {
        LocalTime time = LocalTime.of(0, 0, 0, 0);
        LocalDate date = LocalDate.ofEpochDay(0L); // 1970-01-01
        LocalDateTime dt = time.atDate(date);
        if (!dt.toLocalDate().equals(date)) {
            throw new AssertionError("Date part mismatch for epoch day: expected " + date + " but got " + dt.toLocalDate());
        }
        if (!dt.toLocalTime().equals(time)) {
            throw new AssertionError("Time part mismatch for epoch day: expected " + time + " but got " + dt.toLocalTime());
        }
    }
}
