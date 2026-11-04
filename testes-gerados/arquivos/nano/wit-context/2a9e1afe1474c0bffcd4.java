/*
 * @test
 * @summary Tests for java.time.LocalDateTime.of(int, int, int, int, int, int)
 * @run main LocalDateTimeWitupTest
 */

import java.time.LocalDateTime;
import java.time.DateTimeException;

public class LocalDateTimeWitupTest {

    public static void main(String[] args) {
        testValidInputs();
        testInvalidYear();
        testInvalidMonth();
        testInvalidDay();
    }

    static void testValidInputs() {
        try {
            LocalDateTime dt = LocalDateTime.of(2020, 5, 15, 10, 30, 45);
            if (dt == null) {
                throw new AssertionError("Expected non-null LocalDateTime");
            }
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception for valid inputs", e);
        }
    }

    static void testInvalidYear() {
        try {
            LocalDateTime.of(Integer.MAX_VALUE, 5, 15, 10, 30, 45);
            throw new AssertionError("Expected DateTimeException for invalid year");
        } catch (DateTimeException e) {
            // expected
        }
    }

    static void testInvalidMonth() {
        try {
            LocalDateTime.of(2020, 0, 15, 10, 30, 45);
            throw new AssertionError("Expected DateTimeException for invalid month (0)");
        } catch (DateTimeException e) {
            // expected
        }
        try {
            LocalDateTime.of(2020, 13, 15, 10, 30, 45);
            throw new AssertionError("Expected DateTimeException for invalid month (13)");
        } catch (DateTimeException e) {
            // expected
        }
    }

    static void testInvalidDay() {
        try {
            LocalDateTime.of(2020, 2, 30, 10, 30, 45);
            throw new AssertionError("Expected DateTimeException for invalid day (Feb 30)");
        } catch (DateTimeException e) {
            // expected
        }
        try {
            LocalDateTime.of(2020, 4, 31, 10, 30, 45);
            throw new AssertionError("Expected DateTimeException for invalid day (Apr 31)");
        } catch (DateTimeException e) {
            // expected
        }
    }
}
