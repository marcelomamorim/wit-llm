/*
 * @test
 * @summary Test java.time.LocalDateTime.withNano(int) method
 * @run main LocalDateTimeWitupTest
 */

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;

public class LocalDateTimeWitupTest {
    public static void main(String[] args) {
        testWithNanoValid();
        testWithNanoZero();
        testWithNanoMax();
        testWithNanoInvalidNegative();
        testWithNanoInvalidTooLarge();
    }

    private static void testWithNanoValid() {
        LocalDateTime ldt = LocalDateTime.of(2018, Month.JANUARY, 1, 12, 30, 45, 123456789);
        LocalDateTime result = ldt.withNano(987654321);
        if (result.getNano() != 987654321) {
            throw new AssertionError("Expected nano 987654321 but got " + result.getNano());
        }
        if (!result.toLocalDate().equals(ldt.toLocalDate())) {
            throw new AssertionError("Date part changed unexpectedly");
        }
        if (result == ldt) {
            throw new AssertionError("Expected a new LocalDateTime instance");
        }
    }

    private static void testWithNanoZero() {
        LocalDateTime ldt = LocalDateTime.of(2020, 5, 15, 0, 0, 0, 1);
        LocalDateTime result = ldt.withNano(0);
        if (result.getNano() != 0) {
            throw new AssertionError("Expected nano 0 but got " + result.getNano());
        }
    }

    private static void testWithNanoMax() {
        LocalDateTime ldt = LocalDateTime.of(2021, 12, 31, 23, 59, 59, 0);
        LocalDateTime result = ldt.withNano(999_999_999);
        if (result.getNano() != 999_999_999) {
            throw new AssertionError("Expected nano 999999999 but got " + result.getNano());
        }
    }

    private static void testWithNanoInvalidNegative() {
        LocalDateTime ldt = LocalDateTime.of(2019, 7, 20, 10, 10);
        try {
            ldt.withNano(-1);
            throw new AssertionError("Expected DateTimeException for negative nano");
        } catch (java.time.DateTimeException e) {
            // expected
        }
    }

    private static void testWithNanoInvalidTooLarge() {
        LocalDateTime ldt = LocalDateTime.of(2019, 7, 20, 10, 10);
        try {
            ldt.withNano(1_000_000_000);
            throw new AssertionError("Expected DateTimeException for nano >= 1_000_000_000");
        } catch (java.time.DateTimeException e) {
            // expected
        }
    }
}
