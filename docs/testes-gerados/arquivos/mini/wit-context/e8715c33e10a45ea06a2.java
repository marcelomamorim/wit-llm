/*
 * @test
 * @summary Test java.time.LocalDateTime.withNano(int) method for valid and invalid nano values
 * @run main LocalDateTimeWitupTest
 */

import java.time.LocalDateTime;
import java.time.Month;
import java.time.DateTimeException;

public class LocalDateTimeWitupTest {
    public static void main(String[] args) {
        testWithNanoValid();
        testWithNanoSameValueReturnsSameInstance();
        testWithNanoInvalidLow();
        testWithNanoInvalidHigh();
    }

    private static void testWithNanoValid() {
        LocalDateTime ldt = LocalDateTime.of(2018, Month.JANUARY, 1, 0, 0, 0, 0);
        LocalDateTime result = ldt.withNano(123456789);
        if (result.getNano() != 123456789) {
            throw new AssertionError("withNano did not set nano correctly");
        }
        if (result.getYear() != 2018 || result.getMonth() != Month.JANUARY || result.getDayOfMonth() != 1) {
            throw new AssertionError("Date part changed unexpectedly");
        }
    }

    private static void testWithNanoSameValueReturnsSameInstance() {
        LocalDateTime ldt = LocalDateTime.of(2018, Month.JANUARY, 1, 0, 0, 0, 500);
        LocalDateTime result = ldt.withNano(500);
        if (result != ldt) {
            throw new AssertionError("withNano with same nano value should return same instance");
        }
    }

    private static void testWithNanoInvalidLow() {
        LocalDateTime ldt = LocalDateTime.of(2018, Month.JANUARY, 1, 0, 0);
        try {
            ldt.withNano(-1);
            throw new AssertionError("Expected DateTimeException for nano < 0");
        } catch (DateTimeException e) {
            // expected
        }
    }

    private static void testWithNanoInvalidHigh() {
        LocalDateTime ldt = LocalDateTime.of(2018, Month.JANUARY, 1, 0, 0);
        try {
            ldt.withNano(1000000000); // 1_000_000_000 is invalid nano
            throw new AssertionError("Expected DateTimeException for nano >= 1_000_000_000");
        } catch (DateTimeException e) {
            // expected
        }
    }
}
