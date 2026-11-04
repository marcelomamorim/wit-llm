/*
 * @test
 * @summary Witup-derived tests for Year.plusYears(long) boundary and overflow behavior
 * @run main YearWitupTest
 */

import java.time.DateTimeException;
import java.time.Year;

public class YearWitupTest {

    static int failures = 0;

    static void check(boolean condition, String message) {
        if (!condition) {
            failures++;
            System.err.println("FAILED: " + message);
        }
    }

    static void testZeroReturnsSameInstance() {
        Year y = Year.of(2000);
        check(y.plusYears(0) == y, "plusYears(0) must return this");
    }

    static void testSimpleAddition() {
        check(Year.of(2000).plusYears(5).getValue() == 2005, "2000 + 5 must be 2005");
    }

    static void testNegativeAddition() {
        check(Year.of(0).plusYears(-1).getValue() == -1, "0 + (-1) must be -1");
    }

    static void testUpperBoundExceededThrows() {
        try {
            Year.of(999999999).plusYears(1);
            check(false, "plusYears past MAX must throw DateTimeException");
        } catch (DateTimeException e) {
            // expected
        }
    }

    static void testLowerBoundExceededThrows() {
        try {
            Year.of(-999999999).plusYears(-1);
            check(false, "plusYears below MIN must throw DateTimeException");
        } catch (DateTimeException e) {
            // expected
        }
    }

    static void testOverflowThrows() {
        try {
            Year.of(999999999).plusYears(Long.MAX_VALUE);
            check(false, "overflowing plusYears must throw DateTimeException");
        } catch (DateTimeException e) {
            // expected
        }
    }

    public static void main(String[] args) {
        testZeroReturnsSameInstance();
        testSimpleAddition();
        testNegativeAddition();
        testUpperBoundExceededThrows();
        testLowerBoundExceededThrows();
        testOverflowThrows();
        if (failures > 0) {
            throw new RuntimeException(failures + " check(s) failed");
        }
    }
}
