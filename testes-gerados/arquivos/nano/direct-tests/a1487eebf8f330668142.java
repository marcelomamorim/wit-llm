/*
 * @test
 * @summary Test LocalDateTime.of(int, Month, int, int, int, int)
 * @run main LocalDateTimeWitupTest
 */

import java.time.LocalDateTime;
import java.time.Month;

public class LocalDateTimeWitupTest {

    public static void main(String[] args) {
        testValidDateTime();
        testInvalidHour();
        testInvalidMinute();
        testInvalidSecond();
    }

    static void testValidDateTime() {
        LocalDateTime dt = LocalDateTime.of(2023, Month.MARCH, 15, 10, 30, 45);
        assert dt.getYear() == 2023 : "Year mismatch";
        assert dt.getMonth() == Month.MARCH : "Month mismatch";
        assert dt.getDayOfMonth() == 15 : "Day of month mismatch";
        assert dt.getHour() == 10 : "Hour mismatch";
        assert dt.getMinute() == 30 : "Minute mismatch";
        assert dt.getSecond() == 45 : "Second mismatch";
    }

    static void testInvalidHour() {
        try {
            LocalDateTime.of(2023, Month.MARCH, 15, -1, 0, 0);
            assert false : "Expected exception for invalid hour";
        } catch (DateTimeException e) {
            // expected
        }
    }

    static void testInvalidMinute() {
        try {
            LocalDateTime.of(2023, Month.MARCH, 15, 10, -1, 0);
            assert false : "Expected exception for invalid minute";
        } catch (DateTimeException e) {
            // expected
        }
    }

    static void testInvalidSecond() {
        try {
            LocalDateTime.of(2023, Month.MARCH, 15, 10, 30, -1);
            assert false : "Expected exception for invalid second";
        } catch (DateTimeException e) {
            // expected
        }
    }
}
