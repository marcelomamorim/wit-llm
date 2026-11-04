/*
 * @test
 * @summary WITUP-derived checks for LocalTime.of(int, int) exceptions and observable values
 * @run main LocalTimeWitupTest
 */

import java.time.DateTimeException;
import java.time.LocalTime;

public class LocalTimeWitupTest {

    public static void main(String[] args) {
        testValidValues();
        testInvalidHour();
        testInvalidMinute();
    }

    static void testValidValues() {
        LocalTime t = LocalTime.of(12, 30);
        check(t.getHour() == 12, "hour of 12:30");
        check(t.getMinute() == 30, "minute of 12:30");
        check(t.getSecond() == 0, "second of 12:30");
        check(t.getNano() == 0, "nano of 12:30");

        LocalTime midnight = LocalTime.of(0, 0);
        check(midnight.equals(LocalTime.MIDNIGHT), "of(0,0) equals MIDNIGHT");

        LocalTime last = LocalTime.of(23, 59);
        check(last.getHour() == 23 && last.getMinute() == 59, "23:59 fields");

        LocalTime onHour = LocalTime.of(7, 0);
        check(onHour.getHour() == 7 && onHour.getMinute() == 0, "07:00 fields");
    }

    static void testInvalidHour() {
        expectDateTimeException(-1, 0);
        expectDateTimeException(24, 0);
        expectDateTimeException(24, 30);
        expectDateTimeException(25, 5);
        expectDateTimeException(-1, 15);
    }

    static void testInvalidMinute() {
        expectDateTimeException(10, -1);
        expectDateTimeException(10, 60);
        expectDateTimeException(0, 60);
        expectDateTimeException(0, 99);
        expectDateTimeException(23, 61);
    }

    static void expectDateTimeException(int hour, int minute) {
        try {
            LocalTime t = LocalTime.of(hour, minute);
            throw new RuntimeException("expected DateTimeException for hour=" + hour
                    + " minute=" + minute + " but got " + t);
        } catch (DateTimeException expected) {
            // expected
        }
    }

    static void check(boolean condition, String what) {
        if (!condition) {
            throw new RuntimeException("check failed: " + what);
        }
    }
}
