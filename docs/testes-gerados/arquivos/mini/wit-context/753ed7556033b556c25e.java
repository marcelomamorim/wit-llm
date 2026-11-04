/*
 * @test
 * @summary Test java.time.LocalTime.atDate(LocalDate) method
 * @run main LocalTimeWitupTest
 */

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Objects;

public class LocalTimeWitupTest {
    public static void main(String[] args) {
        testAtDateWithValidDate();
        testAtDateWithNullDate();
    }

    private static void testAtDateWithValidDate() {
        LocalTime time = LocalTime.of(10, 30, 15);
        LocalDate date = LocalDate.of(2020, 5, 20);
        LocalDateTime result = time.atDate(date);
        if (result.getYear() != 2020 || result.getMonthValue() != 5 || result.getDayOfMonth() != 20) {
            throw new AssertionError("Date part of LocalDateTime is incorrect");
        }
        if (result.getHour() != 10 || result.getMinute() != 30 || result.getSecond() != 15) {
            throw new AssertionError("Time part of LocalDateTime is incorrect");
        }
    }

    private static void testAtDateWithNullDate() {
        LocalTime time = LocalTime.of(0, 0);
        try {
            time.atDate(null);
            throw new AssertionError("Expected NullPointerException not thrown");
        } catch (NullPointerException e) {
            // Expected exception
        }
    }
}
