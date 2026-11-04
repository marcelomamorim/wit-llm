/*
 * @test
 * @summary Deterministic tests for java.time.YearMonth.plusMonths(long)
 * @run main YearMonthWitupTest
 */

import java.time.YearMonth;
import java.time.DateTimeException;

public class YearMonthWitupTest {
    public static void main(String[] args) {
        testPlusMonthsZero();
        testPlusMonthsPositive();
        testPlusMonthsNegative();
        testPlusMonthsOverflowThrows();
    }

    private static void testPlusMonthsZero() {
        YearMonth ym = YearMonth.of(2020, 5);
        YearMonth result = ym.plusMonths(0);
        if (result != ym) {
            throw new AssertionError("plusMonths(0) should return same instance");
        }
    }

    private static void testPlusMonthsPositive() {
        YearMonth ym = YearMonth.of(2020, 1);
        YearMonth result = ym.plusMonths(14); // 1 year and 2 months later
        if (result.getYear() != 2021 || result.getMonthValue() != 3) {
            throw new AssertionError("plusMonths(14) failed: got " + result);
        }
    }

    private static void testPlusMonthsNegative() {
        YearMonth ym = YearMonth.of(2020, 3);
        YearMonth result = ym.plusMonths(-4); // 4 months before
        if (result.getYear() != 2019 || result.getMonthValue() != 11) {
            throw new AssertionError("plusMonths(-4) failed: got " + result);
        }
    }

    private static void testPlusMonthsOverflowThrows() {
        // The method plusMonths calls YEAR.checkValidIntValue which throws DateTimeException
        // if the year is out of int range. We test a large monthsToAdd that causes overflow.
        YearMonth ym = YearMonth.of(0, 1);
        long largeMonthsToAdd = (long) Integer.MAX_VALUE * 12 + 12L; // definitely overflows year int range
        try {
            ym.plusMonths(largeMonthsToAdd);
            throw new AssertionError("Expected DateTimeException for overflow not thrown");
        } catch (DateTimeException expected) {
            // expected
        }

        try {
            ym.plusMonths(-largeMonthsToAdd);
            throw new AssertionError("Expected DateTimeException for negative overflow not thrown");
        } catch (DateTimeException expected) {
            // expected
        }
    }
}
