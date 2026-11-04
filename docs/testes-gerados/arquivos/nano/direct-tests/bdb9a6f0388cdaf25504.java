/*
 * @test
 * @summary Test LocalDateTime.of(int, Month, int, int, int) for correctness and regressions
 * @run main LocalDateTimeWitupTest
 */

import java.time.LocalDateTime;
import java.time.Month;

public class LocalDateTimeWitupTest {

    public static void main(String[] args) {
        testOfMethod();
    }

    public static void testOfMethod() {
        // Test with typical date and time
        LocalDateTime dt = LocalDateTime.of(2023, Month.MARCH, 15, 10, 30);
        assert dt.getYear() == 2023 : "Year mismatch";
        assert dt.getMonth() == Month.MARCH : "Month mismatch";
        assert dt.getDayOfMonth() == 15 : "Day of month mismatch";
        assert dt.getHour() == 10 : "Hour mismatch";
        assert dt.getMinute() == 30 : "Minute mismatch";

        // Test with boundary date (e.g., leap year)
        LocalDateTime leapDay = LocalDateTime.of(2020, Month.FEBRUARY, 29, 0, 0);
        assert leapDay.getYear() == 2020 : "Leap year year mismatch";
        assert leapDay.getMonth() == Month.FEBRUARY : "Leap year month mismatch";
        assert leapDay.getDayOfMonth() == 29 : "Leap day mismatch";

        // Test with minimum valid date
        LocalDateTime minDate = LocalDateTime.of(1, Month.JANUARY, 1, 0, 0);
        assert minDate.getYear() == 1 : "Minimum year mismatch";
        assert minDate.getMonth() == Month.JANUARY : "Minimum month mismatch";
        assert minDate.getDayOfMonth() == 1 : "Minimum day mismatch";
        assert minDate.getHour() == 0 : "Minimum hour mismatch";
        assert minDate.getMinute() == 0 : "Minimum minute mismatch";

        // Test with maximum valid date (assuming year 9999)
        LocalDateTime maxDate = LocalDateTime.of(9999, Month.DECEMBER, 31, 23, 59);
        assert maxDate.getYear() == 9999 : "Maximum year mismatch";
        assert maxDate.getMonth() == Month.DECEMBER : "Maximum month mismatch";
        assert maxDate.getDayOfMonth() == 31 : "Maximum day mismatch";
        assert maxDate.getHour() == 23 : "Maximum hour mismatch";
        assert maxDate.getMinute() == 59 : "Maximum minute mismatch";
    }
}
