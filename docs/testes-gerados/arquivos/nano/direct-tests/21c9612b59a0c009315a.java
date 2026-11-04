/*
 * @test
 * @summary Test LocalDateTime.withHour(int) method for various inputs.
 * @run main LocalDateTimeWitupTest
 */

import java.time.LocalDateTime;
import java.time.LocalDate;
import java.time.LocalTime;

public class LocalDateTimeWitupTest {

    public static void main(String[] args) {
        testWithHourAtMidnight();
        testWithHourAtNoon();
        testWithHourAtEdgeCases();
        testWithHourInvalidNegative();
        testWithHourInvalidTooLarge();
    }

    static void testWithHourAtMidnight() {
        LocalDate date = LocalDate.of(2022, 1, 1);
        LocalTime time = LocalTime.of(0, 0);
        LocalDateTime dateTime = LocalDateTime.of(date, time);
        LocalDateTime result = dateTime.withHour(0);
        assert result.getHour() == 0 : "Expected hour 0";
        assert result.toLocalDate().equals(date) : "Date should remain unchanged";
        assert result.toLocalTime().getMinute() == 0 : "Minutes should remain unchanged";
    }

    static void testWithHourAtNoon() {
        LocalDate date = LocalDate.of(2022, 6, 15);
        LocalTime time = LocalTime.of(12, 30);
        LocalDateTime dateTime = LocalDateTime.of(date, time);
        LocalDateTime result = dateTime.withHour(12);
        assert result.getHour() == 12 : "Expected hour 12";
        assert result.toLocalDate().equals(date) : "Date should remain unchanged";
        assert result.toLocalTime().getMinute() == 30 : "Minutes should remain unchanged";
    }

    static void testWithHourAtEdgeCases() {
        LocalDate date = LocalDate.of(2022, 12, 31);
        LocalTime time = LocalTime.of(23, 59, 59);
        LocalDateTime dateTime = LocalDateTime.of(date, time);
        LocalDateTime result = dateTime.withHour(23);
        assert result.getHour() == 23 : "Expected hour 23";
        assert result.toLocalDate().equals(date) : "Date should remain unchanged";
        assert result.toLocalTime().getMinute() == 59 : "Minutes should remain unchanged";
    }

    static void testWithHourInvalidNegative() {
        LocalDate date = LocalDate.of(2022, 1, 1);
        LocalTime time = LocalTime.of(10, 0);
        LocalDateTime dateTime = LocalDateTime.of(date, time);
        try {
            dateTime.withHour(-1);
            assert false : "Expected DateTimeException for negative hour";
        } catch (IndexOutOfBoundsException e) {
            // Expected exception
        }
    }

    static void testWithHourInvalidTooLarge() {
        LocalDate date = LocalDate.of(2022, 1, 1);
        LocalTime time = LocalTime.of(10, 0);
        LocalDateTime dateTime = LocalDateTime.of(date, time);
        try {
            dateTime.withHour(24);
            assert false : "Expected DateTimeException for hour >= 24";
        } catch (IndexOutOfBoundsException e) {
            // Expected exception
        }
    }
}
