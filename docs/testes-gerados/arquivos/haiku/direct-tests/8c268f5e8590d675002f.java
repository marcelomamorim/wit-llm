/*
 * @test
 * @summary Tests for LocalTime.of(int hour, int minute) observable behavior
 * @run main LocalTimeWitupTest
 */

import java.time.DateTimeException;
import java.time.LocalTime;

public class LocalTimeWitupTest {

    public static void main(String[] args) {
        testValidHourAndMinuteFields();
        testMidnightEquality();
        testZeroSecondsAndNanos();
        testToStringFormat();
        testCachedHourEquality();
        testInvalidHour();
        testInvalidMinute();
    }

    static void testValidHourAndMinuteFields() {
        LocalTime t = LocalTime.of(23, 59);
        check(t.getHour() == 23, "hour expected 23 but was " + t.getHour());
        check(t.getMinute() == 59, "minute expected 59 but was " + t.getMinute());

        LocalTime t2 = LocalTime.of(12, 30);
        check(t2.getHour() == 12, "hour expected 12");
        check(t2.getMinute() == 30, "minute expected 30");
    }

    static void testMidnightEquality() {
        check(LocalTime.of(0, 0).equals(LocalTime.MIDNIGHT),
                "of(0,0) should equal LocalTime.MIDNIGHT");
    }

    static void testZeroSecondsAndNanos() {
        LocalTime t = LocalTime.of(7, 45);
        check(t.getSecond() == 0, "second expected 0 but was " + t.getSecond());
        check(t.getNano() == 0, "nano expected 0 but was " + t.getNano());
    }

    static void testToStringFormat() {
        String s = LocalTime.of(12, 30).toString();
        check("12:30".equals(s), "toString expected 12:30 but was " + s);
    }

    static void testCachedHourEquality() {
        LocalTime a = LocalTime.of(5, 0);
        LocalTime b = LocalTime.of(5, 0);
        check(a.equals(b), "of(5,0) instances should be equal");
        check(a.getHour() == 5 && a.getMinute() == 0, "of(5,0) fields incorrect");
    }

    static void testInvalidHour() {
        expectDateTimeException(24, 0, "hour 24");
        expectDateTimeException(-1, 0, "hour -1");
        expectDateTimeException(25, 30, "hour 25 with minute 30");
    }

    static void testInvalidMinute() {
        expectDateTimeException(0, 60, "minute 60");
        expectDateTimeException(0, -1, "minute -1");
        expectDateTimeException(10, 99, "minute 99");
    }

    static void expectDateTimeException(int hour, int minute, String label) {
        boolean thrown = false;
        try {
            LocalTime.of(hour, minute);
        } catch (DateTimeException e) {
            thrown = true;
        }
        check(thrown, "expected DateTimeException for " + label);
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
