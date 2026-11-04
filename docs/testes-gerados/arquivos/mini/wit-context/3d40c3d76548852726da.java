/*
 * @test
 * @summary Deterministic tests for java.time.LocalDate.minusYears(long)
 * @run main java.time.LocalDateWitupTest
 */

import java.time.LocalDate;
import java.time.Month;
import java.time.DateTimeException;

public class LocalDateWitupTest {
    public static void main(String[] args) {
        testMinusYearsNormal();
        testMinusYearsLongMinValue();
    }

    private static void testMinusYearsNormal() {
        LocalDate date = LocalDate.of(2000, Month.JANUARY, 1);
        LocalDate result = date.minusYears(10);
        if (result.getYear() != 1990 || result.getMonth() != Month.JANUARY || result.getDayOfMonth() != 1) {
            throw new AssertionError("minusYears normal case failed: " + result);
        }
        // Test subtracting zero years returns same date
        LocalDate same = date.minusYears(0);
        if (!same.equals(date)) {
            throw new AssertionError("minusYears zero years should return same date");
        }
        // Test subtracting negative years is equivalent to plusYears
        LocalDate plus = date.minusYears(-5);
        LocalDate expectedPlus = date.plusYears(5);
        if (!plus.equals(expectedPlus)) {
            throw new AssertionError("minusYears negative years failed: " + plus);
        }
    }

    private static void testMinusYearsLongMinValue() {
        LocalDate date = LocalDate.of(2000, Month.JANUARY, 1);
        // According to source, minusYears(Long.MIN_VALUE) calls plusYears(Long.MAX_VALUE).plusYears(1)
        LocalDate result = date.minusYears(Long.MIN_VALUE);
        LocalDate expected = date.plusYears(Long.MAX_VALUE).plusYears(1);
        if (!result.equals(expected)) {
            throw new AssertionError("minusYears(Long.MIN_VALUE) failed: " + result + " expected: " + expected);
        }
    }
}
