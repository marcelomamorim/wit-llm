/* @test    @summary Test LocalTime.of(int, int, int) method for valid and invalid inputs    @run main LocalTimeWitupTest */

import java.time.LocalTime;
import java.time.temporal.UnsupportedTemporalTypeException;

public class LocalTimeWitupTest {

    public static void main(String[] args) {
        testValidTime();
        testInvalidHour();
        testInvalidMinute();
        testInvalidSecond();
    }

    static void testValidTime() {
        LocalTime time = LocalTime.of(12, 30, 45);
        assert time.getHour() == 12 : "Expected hour 12, got " + time.getHour();
        assert time.getMinute() == 30 : "Expected minute 30, got " + time.getMinute();
        assert time.getSecond() == 45 : "Expected second 45, got " + time.getSecond();
    }

    static void testInvalidHour() {
        try {
            LocalTime.of(-1, 0, 0);
            assert false : "Expected exception for invalid hour";
        } catch (DateTimeException e) {
            // expected
        }
        try {
            LocalTime.of(24, 0, 0);
            assert false : "Expected exception for invalid hour";
        } catch (DateTimeException e) {
            // expected
        }
    }

    static void testInvalidMinute() {
        try {
            LocalTime.of(12, -1, 0);
            assert false : "Expected exception for invalid minute";
        } catch (DateTimeException e) {
            // expected
        }
        try {
            LocalTime.of(12, 60, 0);
            assert false : "Expected exception for invalid minute";
        } catch (DateTimeException e) {
            // expected
        }
    }

    static void testInvalidSecond() {
        try {
            LocalTime.of(12, 0, -1);
            assert false : "Expected exception for invalid second";
        } catch (DateTimeException e) {
            // expected
        }
        try {
            LocalTime.of(12, 0, 60);
            assert false : "Expected exception for invalid second";
        } catch (DateTimeException e) {
            // expected
        }
    }
}
