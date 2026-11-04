/*
 * @test
 * @summary Test LocalDate.of(int, Month, int) method for correctness and regressions
 * @run main LocalDateWitupTest
 */

import java.time.LocalDate;
import java.time.Month;

public class LocalDateWitupTest {

    public static void main(String[] args) {
        testValidDate();
        testInvalidMonth();
        testInvalidDay();
    }

    static void testValidDate() {
        LocalDate date = LocalDate.of(2020, Month.JANUARY, 15);
        if (date.getYear() != 2020 || date.getMonth() != Month.JANUARY || date.getDayOfMonth() != 15) {
            throw new AssertionError("Valid date test failed");
        }
    }

    static void testInvalidMonth() {
        try {
            LocalDate.of(2020, null, 10);
            throw new AssertionError("Expected NullPointerException for null month");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    static void testInvalidDay() {
        try {
            LocalDate.of(2020, Month.FEBRUARY, 30);
            throw new AssertionError("Expected DateTimeException for invalid day");
        } catch (RuntimeException e) {
            // Since the actual method may throw DateTimeException, which is a RuntimeException, we catch RuntimeException
            // but in JDK 11, DateTimeException extends RuntimeException
            // So, this test is valid
        }
    }
}
