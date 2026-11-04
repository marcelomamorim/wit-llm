/*
 * @test
 * @summary Test for YearMonth.lengthOfMonth() method.
 * @run main YearMonthWitupTest
 */

import java.time.YearMonth;
import java.time.Month;

public class YearMonthWitupTest {

    public static void main(String[] args) {
        testLengthOfMonthForJanuaryLeapYear();
        testLengthOfMonthForFebruaryNonLeapYear();
        testLengthOfMonthForApril();
        testLengthOfMonthForDecember();
    }

    static void testLengthOfMonthForJanuaryLeapYear() {
        YearMonth ym = YearMonth.of(2020, Month.JANUARY);
        int length = ym.lengthOfMonth();
        assert length == 31 : "Expected 31, but got " + length;
    }

    static void testLengthOfMonthForFebruaryNonLeapYear() {
        YearMonth ym = YearMonth.of(2019, Month.FEBRUARY);
        int length = ym.lengthOfMonth();
        assert length == 28 : "Expected 28, but got " + length;
    }

    static void testLengthOfMonthForApril() {
        YearMonth ym = YearMonth.of(2021, Month.APRIL);
        int length = ym.lengthOfMonth();
        assert length == 30 : "Expected 30, but got " + length;
    }

    static void testLengthOfMonthForDecember() {
        YearMonth ym = YearMonth.of(2022, Month.DECEMBER);
        int length = ym.lengthOfMonth();
        assert length == 31 : "Expected 31, but got " + length;
    }
}
