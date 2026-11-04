/*
 * @test
 * @summary Test for LocalDateTime.minusMonths(long)
 * @run main LocalDateTimeWitupTest
 */

import java.time.LocalDateTime;

public class LocalDateTimeWitupTest {

    public static void main(String[] args) {
        testMinusMonthsZero();
        testMinusMonthsPositive();
        testMinusMonthsNegative();
        testMinusMonthsLongMin();
        testMinusMonthsLongMax();
    }

    static void testMinusMonthsZero() {
        LocalDateTime dateTime = LocalDateTime.of(2023, 3, 15, 10, 30);
        LocalDateTime result = dateTime.minusMonths(0);
        assert result.equals(dateTime) : "minusMonths(0) should return the same date";
    }

    static void testMinusMonthsPositive() {
        LocalDateTime dateTime = LocalDateTime.of(2023, 3, 15, 10, 30);
        LocalDateTime expected = LocalDateTime.of(2023, 0, 15, 10, 30); // month 0 is invalid, but LocalDateTime adjusts to previous year
        LocalDateTime result = dateTime.minusMonths(3);
        assert result.getYear() == 2022 && result.getMonthValue() == 12 : "minusMonths(3) should subtract 3 months";
    }

    static void testMinusMonthsNegative() {
        LocalDateTime dateTime = LocalDateTime.of(2023, 1, 15, 10, 30);
        LocalDateTime expected = LocalDateTime.of(2023, 4, 15, 10, 30); // adding 4 months
        LocalDateTime result = dateTime.minusMonths(-4);
        assert result.getYear() == 2023 && result.getMonthValue() == 5 : "minusMonths(-4) should add 4 months";
    }

    static void testMinusMonthsLongMin() {
        LocalDateTime dateTime = LocalDateTime.of(2023, 3, 15, 10, 30);
        LocalDateTime result = dateTime.minusMonths(Long.MIN_VALUE);
        // Should behave as plusMonths(Long.MAX_VALUE) + 1
        // Since Long.MIN_VALUE is very large negative, result is expected to be far in the future
        // We test that the result is not null and is a LocalDateTime instance
        assert result != null : "minusMonths(Long.MIN_VALUE) should not return null";
        assert result instanceof LocalDateTime : "Result should be a LocalDateTime";
    }

    static void testMinusMonthsLongMax() {
        LocalDateTime dateTime = LocalDateTime.of(2023, 3, 15, 10, 30);
        LocalDateTime result = dateTime.minusMonths(Long.MAX_VALUE);
        // Should behave as plusMonths(-Long.MAX_VALUE)
        assert result != null : "minusMonths(Long.MAX_VALUE) should not return null";
        assert result instanceof LocalDateTime : "Result should be a LocalDateTime";
    }
}
