/*
 * @test
 * @summary Tests for java.time.LocalDateTime.of(int, Month, int, int, int, int)
 * @run main LocalDateTimeWitupTest
 */

import java.time.LocalDateTime;
import java.time.Month;

public class LocalDateTimeWitupTest {

    public static void main(String[] args) {
        testValidInputs();
        testInvalidYearTooSmall();
        testInvalidYearTooLarge();
        testNullMonth();
    }

    static void testValidInputs() {
        try {
            LocalDateTime dt = LocalDateTime.of(2020, Month.JANUARY, 15, 10, 30, 45);
            if (dt == null) {
                throw new AssertionError("Expected non-null LocalDateTime");
            }
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception for valid inputs", e);
        }
    }

    static void testInvalidYearTooSmall() {
        try {
            LocalDateTime.of(-10000, Month.JANUARY, 15, 10, 30, 45);
            throw new AssertionError("Expected exception for year too small");
        } catch (Exception e) {
            if (!(e instanceof java.time.DateTimeException)) {
                throw new AssertionError("Expected DateTimeException for year too small", e);
            }
        }
    }

    static void testInvalidYearTooLarge() {
        try {
            LocalDateTime.of(300000000, Month.JANUARY, 15, 10, 30, 45);
            throw new AssertionError("Expected exception for year too large");
        } catch (Exception e) {
            if (!(e instanceof java.time.DateTimeException)) {
                throw new AssertionError("Expected DateTimeException for year too large", e);
            }
        }
    }

    static void testNullMonth() {
        try {
            LocalDateTime.of(2020, null, 15, 10, 30, 45);
            throw new AssertionError("Expected NullPointerException for null month");
        } catch (Exception e) {
            if (!(e instanceof NullPointerException)) {
                throw new AssertionError("Expected NullPointerException for null month", e);
            }
        }
    }
}
