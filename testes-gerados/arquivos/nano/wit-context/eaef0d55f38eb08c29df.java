/*
 * @test
 * @summary Testa o método minusSeconds de LocalDateTime para comportamento observável.
 * @run main LocalDateTimeWitupTest
 */

import java.time.LocalDateTime;
import java.time.Month;

public class LocalDateTimeWitupTest {

    public static void main(String[] args) {
        testMinusSecondsZero();
        testMinusSecondsPositive();
        testMinusSecondsNegative();
        testMinusSecondsLarge();
    }

    static void testMinusSecondsZero() {
        LocalDateTime dt = LocalDateTime.of(2023, Month.MARCH, 15, 12, 0);
        LocalDateTime result = dt.minusSeconds(0);
        if (!result.equals(dt)) {
            throw new AssertionError("minusSeconds(0) should return the same instance");
        }
    }

    static void testMinusSecondsPositive() {
        LocalDateTime dt = LocalDateTime.of(2023, Month.MARCH, 15, 12, 0);
        LocalDateTime result = dt.minusSeconds(60);
        LocalDateTime expected = LocalDateTime.of(2023, Month.MARCH, 15, 11, 59);
        if (!result.equals(expected)) {
            throw new AssertionError("minusSeconds(60) did not produce expected result");
        }
    }

    static void testMinusSecondsNegative() {
        LocalDateTime dt = LocalDateTime.of(2023, Month.MARCH, 15, 12, 0);
        LocalDateTime result = dt.minusSeconds(-120);
        LocalDateTime expected = LocalDateTime.of(2023, Month.MARCH, 15, 12, 2);
        if (!result.equals(expected)) {
            throw new AssertionError("minusSeconds(-120) did not produce expected result");
        }
    }

    static void testMinusSecondsLarge() {
        LocalDateTime dt = LocalDateTime.of(2023, Month.MARCH, 15, 12, 0);
        long largeSeconds = 1_000_000L;
        LocalDateTime result = dt.minusSeconds(largeSeconds);
        LocalDateTime expected = dt.plusSeconds(-largeSeconds);
        if (!result.equals(expected)) {
            throw new AssertionError("minusSeconds(large) did not produce expected result");
        }
    }
}
