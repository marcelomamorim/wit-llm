/*
 * @test
 * @summary Test LocalDateTime.of with various valid inputs
 * @run main LocalDateTimeWitupTest
 */

import java.time.LocalDateTime;
import java.time.Month;

public class LocalDateTimeWitupTest {

    public static void main(String[] args) {
        testOfMethod();
    }

    static void testOfMethod() {
        // Test with typical date and time
        LocalDateTime dt1 = LocalDateTime.of(2020, Month.JANUARY, 15, 10, 30, 45, 123456789);
        assert dt1.getYear() == 2020 : "Year mismatch";
        assert dt1.getMonth() == Month.JANUARY : "Month mismatch";
        assert dt1.getDayOfMonth() == 15 : "Day of month mismatch";
        assert dt1.getHour() == 10 : "Hour mismatch";
        assert dt1.getMinute() == 30 : "Minute mismatch";
        assert dt1.getSecond() == 45 : "Second mismatch";
        assert dt1.getNano() == 123456789 : "NanoOfSecond mismatch";

        // Test with minimum values
        LocalDateTime dt2 = LocalDateTime.of(1, Month.JANUARY, 1, 0, 0, 0, 0);
        assert dt2.getYear() == 1 : "Year mismatch";
        assert dt2.getMonth() == Month.JANUARY : "Month mismatch";
        assert dt2.getDayOfMonth() == 1 : "Day of month mismatch";
        assert dt2.getHour() == 0 : "Hour mismatch";
        assert dt2.getMinute() == 0 : "Minute mismatch";
        assert dt2.getSecond() == 0 : "Second mismatch";
        assert dt2.getNano() == 0 : "NanoOfSecond mismatch";

        // Test with maximum values
        LocalDateTime dt3 = LocalDateTime.of(9999, Month.DECEMBER, 31, 23, 59, 59, 999999999);
        assert dt3.getYear() == 9999 : "Year mismatch";
        assert dt3.getMonth() == Month.DECEMBER : "Month mismatch";
        assert dt3.getDayOfMonth() == 31 : "Day of month mismatch";
        assert dt3.getHour() == 23 : "Hour mismatch";
        assert dt3.getMinute() == 59 : "Minute mismatch";
        assert dt3.getSecond() == 59 : "Second mismatch";
        assert dt3.getNano() == 999999999 : "NanoOfSecond mismatch";
    }
}
