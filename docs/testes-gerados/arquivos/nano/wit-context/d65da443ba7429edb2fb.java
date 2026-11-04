/*
 * @test
 * @summary Test for LocalDateTime.minusMonths(long)
 * @run main LocalDateTimeWitupTest
 */

import java.time.LocalDateTime;
import java.time.Month;

public class LocalDateTimeWitupTest {

    public static void main(String[] args) {
        testMinusMonthsNormal();
        testMinusMonthsMinValue();
        testMinusMonthsMaxValue();
        testMinusMonthsZero();
    }

    static void testMinusMonthsNormal() {
        LocalDateTime dateTime = LocalDateTime.of(2023, Month.MARCH, 15, 10, 30);
        LocalDateTime result = dateTime.minusMonths(2);
        if (!result.equals(LocalDateTime.of(2023, Month.JANUARY, 15, 10, 30))) {
            throw new AssertionError("Failed: minusMonths(2)");
        }
    }

    static void testMinusMonthsMinValue() {
        LocalDateTime dateTime = LocalDateTime.of(2023, Month.MARCH, 15, 10, 30);
        try {
            dateTime.minusMonths(Long.MIN_VALUE);
            throw new AssertionError("Expected exception not thrown for Long.MIN_VALUE");
        } catch (Exception e) {
            if (!(e instanceof java.time.DateTimeException)) {
                throw new AssertionError("Unexpected exception type for Long.MIN_VALUE", e);
            }
        }
    }

    static void testMinusMonthsMaxValue() {
        LocalDateTime dateTime = LocalDateTime.of(2023, Month.MARCH, 15, 10, 30);
        try {
            dateTime.minusMonths(Long.MAX_VALUE);
            throw new AssertionError("Expected exception not thrown for Long.MAX_VALUE");
        } catch (Exception e) {
            if (!(e instanceof java.time.DateTimeException)) {
                throw new AssertionError("Unexpected exception type for Long.MAX_VALUE", e);
            }
        }
    }

    static void testMinusMonthsZero() {
        LocalDateTime dateTime = LocalDateTime.of(2023, Month.MARCH, 15, 10, 30);
        LocalDateTime result = dateTime.minusMonths(0);
        if (!result.equals(dateTime)) {
            throw new AssertionError("Failed: minusMonths(0)");
        }
    }
}
