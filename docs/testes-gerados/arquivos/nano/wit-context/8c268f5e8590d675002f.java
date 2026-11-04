/*
 * @test
 * @summary Tests for java.time.LocalTime.of(int, int)
 * @run main LocalTimeWitupTest
 */

import java.time.LocalTime;
import java.time.temporal.ChronoField;

public class LocalTimeWitupTest {

    public static void main(String[] args) {
        testValidInputs();
        testInvalidHourBelowRange();
        testInvalidHourAboveRange();
        testInvalidMinuteBelowRange();
        testInvalidMinuteAboveRange();
    }

    static void testValidInputs() {
        try {
            LocalTime lt = LocalTime.of(12, 30);
            if (lt.getHour() != 12 || lt.getMinute() != 30) {
                throw new AssertionError("Unexpected time: " + lt);
            }
        } catch (Exception e) {
            throw new AssertionError("Valid input caused exception", e);
        }
    }

    static void testInvalidHourBelowRange() {
        try {
            LocalTime.of(-1, 0);
            throw new AssertionError("Expected exception for hour below range not thrown");
        } catch (Exception e) {
            if (!(e instanceof java.time.DateTimeException)) {
                throw new AssertionError("Unexpected exception type", e);
            }
        }
    }

    static void testInvalidHourAboveRange() {
        try {
            LocalTime.of(24, 0);
            throw new AssertionError("Expected exception for hour above range not thrown");
        } catch (Exception e) {
            if (!(e instanceof java.time.DateTimeException)) {
                throw new AssertionError("Unexpected exception type", e);
            }
        }
    }

    static void testInvalidMinuteBelowRange() {
        try {
            LocalTime.of(10, -1);
            throw new AssertionError("Expected exception for minute below range not thrown");
        } catch (Exception e) {
            if (!(e instanceof java.time.DateTimeException)) {
                throw new AssertionError("Unexpected exception type", e);
            }
        }
    }

    static void testInvalidMinuteAboveRange() {
        try {
            LocalTime.of(10, 60);
            throw new AssertionError("Expected exception for minute above range not thrown");
        } catch (Exception e) {
            if (!(e instanceof java.time.DateTimeException)) {
                throw new AssertionError("Unexpected exception type", e);
            }
        }
    }
}
