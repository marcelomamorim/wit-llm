/*
 * @test
 * @summary Deterministic tests for java.time.YearMonth.of(int,int) method
 * @run main YearMonthWitupTest
 */

import java.time.YearMonth;
import java.time.DateTimeException;

public class YearMonthWitupTest {
    public static void main(String[] args) {
        testValidYearMonth();
        testYearTooLow();
        testYearTooHigh();
        testMonthTooLow();
        testMonthTooHigh();
    }

    private static void testValidYearMonth() {
        // Typical valid values
        YearMonth ym = YearMonth.of(2018, 1);
        if (ym.getYear() != 2018 || ym.getMonthValue() != 1) {
            throw new AssertionError("YearMonth.of did not create expected instance");
        }
        ym = YearMonth.of(0, 12);
        if (ym.getYear() != 0 || ym.getMonthValue() != 12) {
            throw new AssertionError("YearMonth.of did not create expected instance for year 0");
        }
    }

    private static void testYearTooLow() {
        // Year below supported range should throw DateTimeException
        try {
            YearMonth.of(Integer.MIN_VALUE, 1);
            throw new AssertionError("Expected DateTimeException for year too low");
        } catch (DateTimeException expected) {
            // expected
        }
    }

    private static void testYearTooHigh() {
        // Year above supported range should throw DateTimeException
        try {
            YearMonth.of(Integer.MAX_VALUE, 1);
            throw new AssertionError("Expected DateTimeException for year too high");
        } catch (DateTimeException expected) {
            // expected
        }
    }

    private static void testMonthTooLow() {
        // Month below 1 should throw DateTimeException
        try {
            YearMonth.of(2000, 0);
            throw new AssertionError("Expected DateTimeException for month too low");
        } catch (DateTimeException expected) {
            // expected
        }
    }

    private static void testMonthTooHigh() {
        // Month above 12 should throw DateTimeException
        try {
            YearMonth.of(2000, 13);
            throw new AssertionError("Expected DateTimeException for month too high");
        } catch (DateTimeException expected) {
            // expected
        }
    }
}
