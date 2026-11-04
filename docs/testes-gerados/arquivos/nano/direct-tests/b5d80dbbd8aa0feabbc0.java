/*
 * @test
 * @summary Test LocalDate.minusMonths for various inputs
 * @run main LocalDateMinusMonthsTest
 */

import java.time.LocalDate;
import static java.time.LocalDate.of;

public class LocalDateMinusMonthsTest {

    public static void main(String[] args) {
        testMinusMonthsZero();
        testMinusMonthsPositive();
        testMinusMonthsNegative();
        testMinusMonthsLongMin();
        testMinusMonthsLongMax();
        testMinusMonthsLargePositive();
        testMinusMonthsLargeNegative();
    }

    static void testMinusMonthsZero() {
        LocalDate date = of(2023, 3, 15);
        LocalDate result = date.minusMonths(0);
        assert result.equals(date) : "minusMonths(0) should return the same date";
    }

    static void testMinusMonthsPositive() {
        LocalDate date = of(2023, 3, 15);
        LocalDate result = date.minusMonths(2);
        LocalDate expected = of(2023, 1, 15);
        assert result.equals(expected) : "minusMonths(2) should subtract 2 months";
    }

    static void testMinusMonthsNegative() {
        LocalDate date = of(2023, 3, 15);
        LocalDate result = date.minusMonths(-3);
        LocalDate expected = of(2023, 6, 15);
        assert result.equals(expected) : "minusMonths(-3) should add 3 months";
    }

    static void testMinusMonthsLongMin() {
        LocalDate date = of(2023, 3, 15);
        LocalDate result = date.minusMonths(Long.MIN_VALUE);
        LocalDate expected = date.plusMonths(Long.MAX_VALUE).plusMonths(1);
        assert result.equals(expected) : "minusMonths(Long.MIN_VALUE) should be equivalent to plusMonths(Long.MAX_VALUE).plusMonths(1)";
    }

    static void testMinusMonthsLongMax() {
        LocalDate date = of(2023, 3, 15);
        LocalDate result = date.minusMonths(Long.MAX_VALUE);
        LocalDate expected = date.plusMonths(-Long.MAX_VALUE);
        assert result.equals(expected) : "minusMonths(Long.MAX_VALUE) should be equivalent to plusMonths(-Long.MAX_VALUE)";
    }

    static void testMinusMonthsLargePositive() {
        LocalDate date = of(2000, 1, 1);
        long largeMonths = 1000000L;
        LocalDate result = date.minusMonths(largeMonths);
        LocalDate expected = date.plusMonths(-largeMonths);
        assert result.equals(expected) : "minusMonths(large positive) should be equivalent to plusMonths(negative large)";
    }

    static void testMinusMonthsLargeNegative() {
        LocalDate date = of(2000, 1, 1);
        long largeMonths = -1000000L;
        LocalDate result = date.minusMonths(largeMonths);
        LocalDate expected = date.plusMonths(-largeMonths);
        assert result.equals(expected) : "minusMonths(large negative) should be equivalent to plusMonths(positive large)";
    }
}
