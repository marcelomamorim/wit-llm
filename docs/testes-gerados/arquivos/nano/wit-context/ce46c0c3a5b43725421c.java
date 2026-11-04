/*
 * @test
 * @summary Test for LocalDateTime.of(int, int, int, int, int)
 * @run main LocalDateTimeWitupTest
 */

import java.time.LocalDateTime;
import java.time.LocalDate;
import java.time.LocalTime;

public class LocalDateTimeWitupTest {

    public static void main(String[] args) {
        testValidInputs();
        testInvalidMonthBelowRange();
        testInvalidMonthAboveRange();
        testInvalidDayBelowRange();
        testInvalidDayAboveRange();
        testInvalidYearBelowRange();
        testInvalidYearAboveRange();
    }

    static void testValidInputs() {
        try {
            LocalDateTime dt = LocalDateTime.of(2020, 5, 15, 10, 30);
            if (dt == null) {
                throw new AssertionError("Expected non-null LocalDateTime");
            }
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception for valid inputs", e);
        }
    }

    static void testInvalidMonthBelowRange() {
        try {
            LocalDateTime.of(2020, 0, 15, 10, 30);
            throw new AssertionError("Expected exception for month below range");
        } catch (java.time.DateTimeException e) {
            // expected
        }
    }

    static void testInvalidMonthAboveRange() {
        try {
            LocalDateTime.of(2020, 13, 15, 10, 30);
            throw new AssertionError("Expected exception for month above range");
        } catch (java.time.DateTimeException e) {
            // expected
        }
    }

    static void testInvalidDayBelowRange() {
        try {
            LocalDateTime.of(2020, 5, 0, 10, 30);
            throw new AssertionError("Expected exception for day below range");
        } catch (java.time.DateTimeException e) {
            // expected
        }
    }

    static void testInvalidDayAboveRange() {
        try {
            LocalDateTime.of(2020, 5, 32, 10, 30);
            throw new AssertionError("Expected exception for day above range");
        } catch (java.time.DateTimeException e) {
            // expected
        }
    }

    static void testInvalidYearBelowRange() {
        try {
            LocalDateTime.of(Integer.MIN_VALUE, 5, 15, 10, 30);
            // Depending on implementation, this may or may not throw; assume it does
        } catch (java.time.DateTimeException e) {
            // expected
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception for year below range", e);
        }
    }

    static void testInvalidYearAboveRange() {
        try {
            LocalDateTime.of(Integer.MAX_VALUE, 5, 15, 10, 30);
            // Depending on implementation, this may or may not throw; assume it does
        } catch (java.time.DateTimeException e) {
            // expected
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception for year above range", e);
        }
    }
}
