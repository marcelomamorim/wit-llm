/*
 * @test
 * @summary Deterministic tests for java.time.YearMonth.minusMonths(long)
 * @run main YearMonthWitupTest
 */

import java.time.YearMonth;
import java.time.Month;
import java.time.DateTimeException;

public class YearMonthWitupTest {
    public static void main(String[] args) {
        testMinusMonthsBasic();
        testMinusMonthsLongMinValue();
        testMinusMonthsOverflow();
    }

    private static void testMinusMonthsBasic() {
        YearMonth ym = YearMonth.of(2020, Month.JANUARY);
        YearMonth result = ym.minusMonths(1);
        // Expect December 2019
        if (result.getYear() != 2019 || result.getMonthValue() != 12) {
            throw new AssertionError("minusMonths(1) from 2020-01 should be 2019-12 but was " + result);
        }
        // minus 0 months returns same YearMonth
        YearMonth same = ym.minusMonths(0);
        if (!same.equals(ym)) {
            throw new AssertionError("minusMonths(0) should return same YearMonth");
        }
    }

    private static void testMinusMonthsLongMinValue() {
        YearMonth ym = YearMonth.of(2000, Month.JANUARY);
        // minusMonths(Long.MIN_VALUE) internally calls plusMonths(Long.MAX_VALUE).plusMonths(1)
        // This should not throw and produce a valid YearMonth
        YearMonth result = ym.minusMonths(Long.MIN_VALUE);
        if (result == null) {
            throw new AssertionError("minusMonths(Long.MIN_VALUE) returned null");
        }
        // The result should not be equal to the original
        if (result.equals(ym)) {
            throw new AssertionError("minusMonths(Long.MIN_VALUE) should not return the same YearMonth");
        }
    }

    private static void testMinusMonthsOverflow() {
        // Test a case that would cause overflow in plusMonths and thus in minusMonths
        // Use a large year near Integer.MAX_VALUE / 12 to trigger DateTimeException
        // YearMonth.of(int year, int month) throws DateTimeException if invalid
        // We try to create a YearMonth and then subtract months to cause overflow
        try {
            int largeYear = Integer.MAX_VALUE / 12 + 1;
            YearMonth ym = YearMonth.of(largeYear, 1);
            // Subtracting a large negative months to cause overflow
            ym.minusMonths(Long.MIN_VALUE);
            throw new AssertionError("Expected DateTimeException not thrown for overflow case");
        } catch (DateTimeException expected) {
            // Expected exception
        } catch (Exception unexpected) {
            throw new AssertionError("Unexpected exception type: " + unexpected);
        }
    }
}
