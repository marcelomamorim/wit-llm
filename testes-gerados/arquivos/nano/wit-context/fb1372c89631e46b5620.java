/*
 * @test
 * @summary Tests for java.time.LocalTime.of(int, int, int)
 * @run main LocalTimeWitupTest
 */

import java.time.LocalTime;
import java.time.DateTimeException;

public class LocalTimeWitupTest {

    public static void main(String[] args) {
        testValidTime();
        testInvalidHourBelowRange();
        testInvalidHourAboveRange();
        testInvalidMinuteBelowRange();
        testInvalidMinuteAboveRange();
        testInvalidSecondBelowRange();
        testInvalidSecondAboveRange();
    }

    static void testValidTime() {
        try {
            LocalTime lt = LocalTime.of(12, 30, 45);
            if (lt.getHour() != 12 || lt.getMinute() != 30 || lt.getSecond() != 45) {
                throw new AssertionError("Valid time test failed");
            }
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception in valid time test", e);
        }
    }

    static void testInvalidHourBelowRange() {
        try {
            LocalTime.of(-1, 0, 0);
            throw new AssertionError("Expected DateTimeException for hour below range");
        } catch (DateTimeException e) {
            // expected
        }
    }

    static void testInvalidHourAboveRange() {
        try {
            LocalTime.of(24, 0, 0);
            throw new AssertionError("Expected DateTimeException for hour above range");
        } catch (DateTimeException e) {
            // expected
        }
    }

    static void testInvalidMinuteBelowRange() {
        try {
            LocalTime.of(12, -1, 0);
            throw new AssertionError("Expected DateTimeException for minute below range");
        } catch (DateTimeException e) {
            // expected
        }
    }

    static void testInvalidMinuteAboveRange() {
        try {
            LocalTime.of(12, 60, 0);
            throw new AssertionError("Expected DateTimeException for minute above range");
        } catch (DateTimeException e) {
            // expected
        }
    }

    static void testInvalidSecondBelowRange() {
        try {
            LocalTime.of(12, 0, -1);
            throw new AssertionError("Expected DateTimeException for second below range");
        } catch (DateTimeException e) {
            // expected
        }
    }

    static void testInvalidSecondAboveRange() {
        try {
            LocalTime.of(12, 0, 60);
            throw new AssertionError("Expected DateTimeException for second above range");
        } catch (DateTimeException e) {
            // expected
        }
    }
}
