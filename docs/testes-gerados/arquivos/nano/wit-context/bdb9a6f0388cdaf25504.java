/*
 * @test
 * @summary Test for LocalDateTime.of(int, Month, int, int, int)
 * @run main LocalDateTimeWitupTest
 */

import java.time.LocalDateTime;
import java.time.Month;

public class LocalDateTimeWitupTest {

    public static void main(String[] args) {
        testValidInputs();
        testNullMonth();
        testYearTooSmall();
        testYearTooLarge();
        testInvalidDayOfMonth();
    }

    static void testValidInputs() {
        try {
            LocalDateTime dt = LocalDateTime.of(2020, Month.JANUARY, 15, 10, 30);
            if (dt == null) {
                throw new AssertionError("Expected non-null LocalDateTime");
            }
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception for valid inputs", e);
        }
    }

    static void testNullMonth() {
        try {
            LocalDateTime.of(2020, null, 15, 10, 30);
            throw new AssertionError("Expected NullPointerException for null month");
        } catch (NullPointerException e) {
            // expected
        } catch (Exception e) {
            throw new AssertionError("Expected NullPointerException, got " + e);
        }
    }

    static void testYearTooSmall() {
        try {
            LocalDateTime.of(Integer.MIN_VALUE, Month.JANUARY, 15, 10, 30);
            // No exception expected here, as year can be any int
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception for minimum year", e);
        }
    }

    static void testYearTooLarge() {
        try {
            LocalDateTime.of(Integer.MAX_VALUE, Month.JANUARY, 15, 10, 30);
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception for maximum year", e);
        }
    }

    static void testInvalidDayOfMonth() {
        try {
            LocalDateTime.of(2020, Month.FEBRUARY, 30, 10, 30);
            throw new AssertionError("Expected DateTimeException for invalid day");
        } catch (java.time.DateTimeException e) {
            // expected
        } catch (Exception e) {
            throw new AssertionError("Expected DateTimeException, got " + e);
        }
    }
}
