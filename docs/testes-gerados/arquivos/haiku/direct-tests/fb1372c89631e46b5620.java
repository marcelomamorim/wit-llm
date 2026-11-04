/*
 * @test
 * @summary Tests LocalTime.of(int, int, int) for valid values, cached-hour path and invalid field rejection
 * @run main LocalTimeWitupTest
 */

import java.time.DateTimeException;
import java.time.LocalTime;

public class LocalTimeWitupTest {

    static int failures = 0;

    static void check(boolean cond, String msg) {
        if (!cond) {
            failures++;
            System.err.println("FAILED: " + msg);
        }
    }

    static void testValidFields() {
        LocalTime t = LocalTime.of(23, 59, 59);
        check(t.getHour() == 23, "hour 23");
        check(t.getMinute() == 59, "minute 59");
        check(t.getSecond() == 59, "second 59");
        check(t.getNano() == 0, "nano 0");

        LocalTime u = LocalTime.of(10, 30, 15);
        check(u.getHour() == 10, "hour 10");
        check(u.getMinute() == 30, "minute 30");
        check(u.getSecond() == 15, "second 15");
        check(u.equals(LocalTime.of(10, 30, 15)), "equal values should be equal");
    }

    static void testZeroMinuteAndSecondUsesHourValue() {
        for (int h = 0; h < 24; h++) {
            LocalTime t = LocalTime.of(h, 0, 0);
            check(t.getHour() == h, "hour preserved for " + h);
            check(t.getMinute() == 0, "minute 0 for hour " + h);
            check(t.getSecond() == 0, "second 0 for hour " + h);
            check(t.getNano() == 0, "nano 0 for hour " + h);
            check(t.equals(LocalTime.of(h, 0)), "matches of(h,0) for hour " + h);
        }
    }

    static void testBoundaryValues() {
        LocalTime first = LocalTime.of(0, 0, 1);
        check(first.getSecond() == 1 && first.getHour() == 0, "00:00:01");
        LocalTime last = LocalTime.of(23, 59, 0);
        check(last.getMinute() == 59 && last.getSecond() == 0, "23:59:00");
    }

    static void expectDateTimeException(int h, int m, int s, String label) {
        try {
            LocalTime.of(h, m, s);
            failures++;
            System.err.println("FAILED: no exception for " + label);
        } catch (DateTimeException e) {
            // expected
        }
    }

    static void testInvalidHour() {
        expectDateTimeException(24, 0, 0, "hour 24");
        expectDateTimeException(-1, 0, 0, "hour -1");
        expectDateTimeException(25, 30, 0, "hour 25 with nonzero minute");
    }

    static void testInvalidMinuteAndSecond() {
        expectDateTimeException(10, 60, 0, "minute 60");
        expectDateTimeException(10, -1, 0, "minute -1");
        expectDateTimeException(10, 0, 60, "second 60");
        expectDateTimeException(10, 0, -1, "second -1");
        expectDateTimeException(10, 30, 60, "second 60 with minute");
    }

    public static void main(String[] args) {
        testValidFields();
        testZeroMinuteAndSecondUsesHourValue();
        testBoundaryValues();
        testInvalidHour();
        testInvalidMinuteAndSecond();
        if (failures > 0) {
            throw new RuntimeException(failures + " check(s) failed");
        }
    }
}
