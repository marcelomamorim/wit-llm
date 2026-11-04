/*
 * @test
 * @summary Test YearMonth.plusYears method for correct behavior.
 * @run main YearMonthWitupTest
 */

import java.time.YearMonth;

public class YearMonthWitupTest {

    public static void main(String[] args) {
        testPlusYearsZero();
        testPlusYearsPositive();
        testPlusYearsNegative();
        testPlusYearsOverflow();
    }

    static void testPlusYearsZero() {
        YearMonth ym = YearMonth.of(2020, 5);
        YearMonth result = ym.plusYears(0);
        if (result.equals(ym)) {
            System.out.println("testPlusYearsZero passed");
        } else {
            throw new AssertionError("plusYears(0) should return the same YearMonth instance");
        }
    }

    static void testPlusYearsPositive() {
        YearMonth ym = YearMonth.of(2020, 5);
        YearMonth result = ym.plusYears(5);
        YearMonth expected = YearMonth.of(2025, 5);
        if (result.equals(expected)) {
            System.out.println("testPlusYearsPositive passed");
        } else {
            throw new AssertionError("plusYears(5) did not produce expected YearMonth");
        }
    }

    static void testPlusYearsNegative() {
        YearMonth ym = YearMonth.of(2020, 5);
        YearMonth result = ym.plusYears(-3);
        YearMonth expected = YearMonth.of(2017, 5);
        if (result.equals(expected)) {
            System.out.println("testPlusYearsNegative passed");
        } else {
            throw new AssertionError("plusYears(-3) did not produce expected YearMonth");
        }
    }

    static void testPlusYearsOverflow() {
        YearMonth ym = YearMonth.of(9999, 12);
        try {
            YearMonth result = ym.plusYears(1_000_000_000L);
            int expectedYear = YearMonth.of(9999, 12).getYear() + 1_000_000_000L;
            YearMonth expected = YearMonth.of(expectedYear, 12);
            if (result.equals(expected)) {
                System.out.println("testPlusYearsOverflow passed");
            } else {
                throw new AssertionError("plusYears with large value did not produce expected YearMonth");
            }
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception during overflow test", e);
        }
    }
}
