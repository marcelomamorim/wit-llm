/*
 * @test
 * @summary Test for LocalDateTime.plusMinutes(long)
 * @run main LocalDateTimeWitupTest
 */

import java.time.LocalDateTime;
import java.time.Month;

public class LocalDateTimeWitupTest {

    public static void main(String[] args) {
        testPlusMinutesZero();
        testPlusMinutesPositive();
        testPlusMinutesNegative();
        testPlusMinutesLarge();
    }

    static void testPlusMinutesZero() {
        LocalDateTime dt = LocalDateTime.of(2023, Month.JANUARY, 1, 12, 0);
        LocalDateTime result = dt.plusMinutes(0);
        if (!result.equals(dt)) {
            throw new AssertionError("plusMinutes(0) should return the same LocalDateTime");
        }
    }

    static void testPlusMinutesPositive() {
        LocalDateTime dt = LocalDateTime.of(2023, Month.JANUARY, 1, 12, 0);
        LocalDateTime result = dt.plusMinutes(15);
        LocalDateTime expected = LocalDateTime.of(2023, Month.JANUARY, 1, 12, 15);
        if (!result.equals(expected)) {
            throw new AssertionError("plusMinutes(15) did not produce expected result");
        }
    }

    static void testPlusMinutesNegative() {
        LocalDateTime dt = LocalDateTime.of(2023, Month.JANUARY, 1, 12, 0);
        LocalDateTime result = dt.plusMinutes(-30);
        LocalDateTime expected = LocalDateTime.of(2023, Month.JANUARY, 1, 11, 30);
        if (!result.equals(expected)) {
            throw new AssertionError("plusMinutes(-30) did not produce expected result");
        }
    }

    static void testPlusMinutesLarge() {
        LocalDateTime dt = LocalDateTime.of(2023, Month.JANUARY, 1, 12, 0);
        long largeMinutes = 60L * 24 * 365; // 1 year
        LocalDateTime result = dt.plusMinutes(largeMinutes);
        LocalDateTime expected = dt;
        for (int i = 0; i < 365; i++) {
            expected = expected.plusDays(1);
        }
        if (!result.equals(expected)) {
            throw new AssertionError("plusMinutes(large) did not produce expected result");
        }
    }
}
