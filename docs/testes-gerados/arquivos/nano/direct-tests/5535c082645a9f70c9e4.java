/*
 * @test
 * @summary Test YearMonth.plusMonths method for positive, zero, and negative inputs
 * @run main YearMonthWitupTest
 */

import java.time.YearMonth;

public class YearMonthWitupTest {

    public static void main(String[] args) {
        testPlusMonthsZero();
        testPlusMonthsPositive();
        testPlusMonthsNegative();
    }

    static void testPlusMonthsZero() {
        YearMonth ym = YearMonth.of(2023, 3);
        YearMonth result = ym.plusMonths(0);
        if (result.equals(ym)) {
            System.out.println("testPlusMonthsZero passed");
        } else {
            throw new AssertionError("Expected " + ym + " but got " + result);
        }
    }

    static void testPlusMonthsPositive() {
        YearMonth ym = YearMonth.of(2023, 1);
        YearMonth result = ym.plusMonths(14); // should move to March 2024
        YearMonth expected = YearMonth.of(2024, 3);
        if (result.equals(expected)) {
            System.out.println("testPlusMonthsPositive passed");
        } else {
            throw new AssertionError("Expected " + expected + " but got " + result);
        }
    }

    static void testPlusMonthsNegative() {
        YearMonth ym = YearMonth.of(2023, 5);
        YearMonth result = ym.plusMonths(-7); // should move to October 2022
        YearMonth expected = YearMonth.of(2022, 10);
        if (result.equals(expected)) {
            System.out.println("testPlusMonthsNegative passed");
        } else {
            throw new AssertionError("Expected " + expected + " but got " + result);
        }
    }
}
