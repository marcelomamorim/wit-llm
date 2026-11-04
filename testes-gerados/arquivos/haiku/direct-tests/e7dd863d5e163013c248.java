/*
 * @test
 * @summary Verify YearMonth.isValidDay boundaries for leap and non-leap months
 * @run main YearMonthWitupTest
 */

import java.time.YearMonth;

public class YearMonthWitupTest {

    public static void main(String[] args) {
        testDayZeroAndNegativeAreInvalid();
        testFirstAndLastDayOfNonLeapFebruary();
        testFirstAndLastDayOfLeapFebruary();
        testThirtyDayMonthBoundary();
        testThirtyOneDayMonthBoundary();
        testDayAboveMonthLengthIsInvalid();
    }

    static void testDayZeroAndNegativeAreInvalid() {
        YearMonth ym = YearMonth.of(2019, 1);
        check(!ym.isValidDay(0), "day 0 must be invalid");
        check(!ym.isValidDay(-1), "day -1 must be invalid");
        check(!ym.isValidDay(Integer.MIN_VALUE), "Integer.MIN_VALUE must be invalid");
    }

    static void testFirstAndLastDayOfNonLeapFebruary() {
        YearMonth ym = YearMonth.of(2019, 2);
        check(ym.isValidDay(1), "day 1 of Feb 2019 must be valid");
        check(ym.isValidDay(28), "day 28 of Feb 2019 must be valid");
        check(!ym.isValidDay(29), "day 29 of Feb 2019 must be invalid");
    }

    static void testFirstAndLastDayOfLeapFebruary() {
        YearMonth ym = YearMonth.of(2000, 2);
        check(ym.isValidDay(1), "day 1 of Feb 2000 must be valid");
        check(ym.isValidDay(29), "day 29 of Feb 2000 must be valid");
        check(!ym.isValidDay(30), "day 30 of Feb 2000 must be invalid");
    }

    static void testThirtyDayMonthBoundary() {
        YearMonth ym = YearMonth.of(2019, 4);
        check(ym.isValidDay(30), "day 30 of April must be valid");
        check(!ym.isValidDay(31), "day 31 of April must be invalid");
    }

    static void testThirtyOneDayMonthBoundary() {
        YearMonth ym = YearMonth.of(2019, 1);
        check(ym.isValidDay(31), "day 31 of January must be valid");
        check(!ym.isValidDay(32), "day 32 of January must be invalid");
    }

    static void testDayAboveMonthLengthIsInvalid() {
        YearMonth ym = YearMonth.of(2019, 6);
        check(!ym.isValidDay(Integer.MAX_VALUE), "Integer.MAX_VALUE must be invalid");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
