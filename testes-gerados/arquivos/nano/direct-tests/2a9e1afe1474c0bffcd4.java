/*
 * @test
 * @summary Test LocalDateTime.of(int, int, int, int, int, int)
 * @run main LocalDateTimeWitupTest
 */

import java.time.LocalDateTime;
import java.time.LocalDate;
import java.time.LocalTime;

public class LocalDateTimeWitupTest {

    public static void main(String[] args) {
        testOfMethod();
    }

    public static void testOfMethod() {
        // Test with typical date and time
        LocalDateTime ldt = LocalDateTime.of(2023, 3, 15, 10, 30, 45);
        assert ldt.getYear() == 2023 : "Year mismatch";
        assert ldt.getMonthValue() == 3 : "Month mismatch";
        assert ldt.getDayOfMonth() == 15 : "Day mismatch";
        assert ldt.getHour() == 10 : "Hour mismatch";
        assert ldt.getMinute() == 30 : "Minute mismatch";
        assert ldt.getSecond() == 45 : "Second mismatch";

        // Test with boundary date (leap year)
        LocalDateTime leapDay = LocalDateTime.of(2020, 2, 29, 0, 0, 0);
        assert leapDay.getYear() == 2020 : "Year mismatch";
        assert leapDay.getMonthValue() == 2 : "Month mismatch";
        assert leapDay.getDayOfMonth() == 29 : "Day mismatch";

        // Test with end of day time
        LocalDateTime endOfDay = LocalDateTime.of(2023, 12, 31, 23, 59, 59);
        assert endOfDay.getYear() == 2023 : "Year mismatch";
        assert endOfDay.getMonthValue() == 12 : "Month mismatch";
        assert endOfDay.getDayOfMonth() == 31 : "Day mismatch";
        assert endOfDay.getHour() == 23 : "Hour mismatch";
        assert endOfDay.getMinute() == 59 : "Minute mismatch";
        assert endOfDay.getSecond() == 59 : "Second mismatch";

        // Test with minimum valid date
        LocalDateTime minDateTime = LocalDateTime.of(1, 1, 1, 0, 0, 0);
        assert minDateTime.getYear() == 1 : "Year mismatch";
        assert minDateTime.getMonthValue() == 1 : "Month mismatch";
        assert minDateTime.getDayOfMonth() == 1 : "Day mismatch";
        assert minDateTime.getHour() == 0 : "Hour mismatch";
        assert minDateTime.getMinute() == 0 : "Minute mismatch";
        assert minDateTime.getSecond() == 0 : "Second mismatch";

        // Test with maximum reasonable date
        LocalDateTime maxDateTime = LocalDateTime.of(9999, 12, 31, 23, 59, 59);
        assert maxDateTime.getYear() == 9999 : "Year mismatch";
        assert maxDateTime.getMonthValue() == 12 : "Month mismatch";
        assert maxDateTime.getDayOfMonth() == 31 : "Day mismatch";
        assert maxDateTime.getHour() == 23 : "Hour mismatch";
        assert maxDateTime.getMinute() == 59 : "Minute mismatch";
        assert maxDateTime.getSecond() == 59 : "Second mismatch";
    }
}
