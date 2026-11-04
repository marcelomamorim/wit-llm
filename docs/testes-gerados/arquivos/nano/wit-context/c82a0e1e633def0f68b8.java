/*
 * @test
 * @summary Test for LocalDateTime.minusMinutes(long)
 * @run main LocalDateTimeWitupTest
 */

import java.time.LocalDateTime;
import java.time.Month;

public class LocalDateTimeWitupTest {

    public static void main(String[] args) {
        testMinusMinutesZero();
        testMinusMinutesPositive();
        testMinusMinutesNegative();
        testMinusMinutesLarge();
    }

    static void testMinusMinutesZero() {
        LocalDateTime dt = LocalDateTime.of(2023, Month.MARCH, 15, 12, 0);
        LocalDateTime result = dt.minusMinutes(0);
        if (!result.equals(dt)) {
            throw new AssertionError("minusMinutes(0) should return the same LocalDateTime");
        }
    }

    static void testMinusMinutesPositive() {
        LocalDateTime dt = LocalDateTime.of(2023, Month.MARCH, 15, 12, 30);
        LocalDateTime expected = LocalDateTime.of(2023, Month.MARCH, 15, 12, 0);
        LocalDateTime result = dt.minusMinutes(30);
        if (!result.equals(expected)) {
            throw new AssertionError("minusMinutes(30) did not produce expected result");
        }
    }

    static void testMinusMinutesNegative() {
        LocalDateTime dt = LocalDateTime.of(2023, Month.MARCH, 15, 12, 0);
        LocalDateTime expected = LocalDateTime.of(2023, Month.MARCH, 15, 12, 15);
        LocalDateTime result = dt.minusMinutes(-15);
        if (!result.equals(expected)) {
            throw new AssertionError("minusMinutes(-15) did not produce expected result");
        }
    }

    static void testMinusMinutesLarge() {
        LocalDateTime dt = LocalDateTime.of(2023, Month.MARCH, 15, 12, 0);
        LocalDateTime expected = LocalDateTime.of(2023, Month.MARCH, 15, 11, 0);
        LocalDateTime result = dt.minusMinutes(60);
        if (!result.equals(expected)) {
            throw new AssertionError("minusMinutes(60) did not produce expected result");
        }
    }
}
