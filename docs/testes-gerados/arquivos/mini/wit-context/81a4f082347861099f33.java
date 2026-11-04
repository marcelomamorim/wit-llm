/*
 * @test
 * @summary Test java.time.Year.minusYears(long) method behavior and boundary conditions
 * @run main YearWitupTest
 */

import java.time.Year;
import java.time.DateTimeException;

public class YearWitupTest {
    public static void main(String[] args) {
        testMinusYearsNormal();
        testMinusYearsLongMinValue();
        testMinusYearsBoundaryThrows();
        System.out.println("All tests passed.");
    }

    private static void testMinusYearsNormal() {
        Year y = Year.of(2000);
        Year result = y.minusYears(10);
        if (result.getValue() != 1990) {
            throw new AssertionError("Expected 1990 but got " + result.getValue());
        }
        // minus 0 years returns same year
        Year same = y.minusYears(0);
        if (same.getValue() != 2000) {
            throw new AssertionError("Expected 2000 but got " + same.getValue());
        }
        // minus negative years is plus
        Year plus = y.minusYears(-5);
        if (plus.getValue() != 2005) {
            throw new AssertionError("Expected 2005 but got " + plus.getValue());
        }
    }

    private static void testMinusYearsLongMinValue() {
        // According to source, minusYears(Long.MIN_VALUE) calls plusYears(Long.MAX_VALUE).plusYears(1)
        Year base = Year.of(0);
        Year result = base.minusYears(Long.MIN_VALUE);
        // result should be base + Long.MAX_VALUE + 1 = base + Long.MIN_VALUE negated
        // but since Year.of(int) only accepts int, result must be within int range
        // so test that result is consistent with plusYears(Long.MAX_VALUE).plusYears(1)
        Year plusMax = base.plusYears(Long.MAX_VALUE);
        Year plusMaxPlusOne = plusMax.plusYears(1);
        if (result.getValue() != plusMaxPlusOne.getValue()) {
            throw new AssertionError("minusYears(Long.MIN_VALUE) did not match plusYears(Long.MAX_VALUE).plusYears(1)");
        }
    }

    private static void testMinusYearsBoundaryThrows() {
        // Test that minusYears throws DateTimeException when result year is out of valid range
        // The valid range is enforced by Year.of(int) and ChronoField.YEAR.checkValidIntValue
        // Use values near Integer.MIN_VALUE and Integer.MAX_VALUE to trigger exception

        // Year.of throws DateTimeException if year out of range
        // Try to cause overflow by subtracting large number from max year
        Year maxYear = Year.of(Year.MAX_VALUE);
        try {
            maxYear.minusYears(-1L); // minus negative is plus 1, should overflow
            throw new AssertionError("Expected DateTimeException for overflow not thrown");
        } catch (DateTimeException expected) {
            // expected
        }

        // Try to cause underflow by subtracting large number from min year
        Year minYear = Year.of(Year.MIN_VALUE);
        try {
            minYear.minusYears(1L); // subtract 1 from min year should underflow
            throw new AssertionError("Expected DateTimeException for underflow not thrown");
        } catch (DateTimeException expected) {
            // expected
        }
    }
}