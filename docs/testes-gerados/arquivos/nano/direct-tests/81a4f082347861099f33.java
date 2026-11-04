/*
 * @test
 * @summary Test for Year.minusYears method to ensure correct behavior with various inputs.
 * @run main YearWitupTest
 */

import java.time.Year;

public class YearWitupTest {

    public static void main(String[] args) {
        testMinusYearsZero();
        testMinusYearsPositive();
        testMinusYearsNegative();
        testMinusYearsLongMin();
        testMinusYearsLongMax();
    }

    static void testMinusYearsZero() {
        Year currentYear = Year.of(2023);
        Year result = currentYear.minusYears(0);
        assert result.equals(Year.of(2023)) : "minusYears(0) should return the same year";
    }

    static void testMinusYearsPositive() {
        Year currentYear = Year.of(2023);
        Year result = currentYear.minusYears(5);
        assert result.equals(Year.of(2018)) : "minusYears(5) should subtract 5 years";
    }

    static void testMinusYearsNegative() {
        Year currentYear = Year.of(2023);
        Year result = currentYear.minusYears(-3);
        assert result.equals(Year.of(2026)) : "minusYears(-3) should add 3 years";
    }

    static void testMinusYearsLongMin() {
        Year currentYear = Year.of(2023);
        Year result = currentYear.minusYears(Long.MIN_VALUE);
        Year expected = currentYear.plusYears(Long.MAX_VALUE).plusYears(1);
        assert result.equals(expected) : "minusYears(Long.MIN_VALUE) should be equivalent to plusYears(Long.MAX_VALUE).plusYears(1)";
    }

    static void testMinusYearsLongMax() {
        Year currentYear = Year.of(2023);
        Year result = currentYear.minusYears(Long.MAX_VALUE);
        Year expected = currentYear.plusYears(Long.MIN_VALUE).plusYears(1);
        assert result.equals(expected) : "minusYears(Long.MAX_VALUE) should be equivalent to plusYears(Long.MIN_VALUE).plusYears(1)";
    }
}
