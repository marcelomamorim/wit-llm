/*
 * @test
 * @summary Test YearMonth.withYear(int year) method for correctness and regressions
 * @run main YearMonthWitupTest
 */

import java.time.YearMonth;

public class YearMonthWitupTest {

    public static void main(String[] args) {
        testWithYearValid();
        testWithYearBoundary();
        testWithYearInvalid();
    }

    static void testWithYearValid() {
        YearMonth ym = YearMonth.of(2020, 5);
        YearMonth result = ym.withYear(2022);
        assert result.getYear() == 2022 : "Expected year 2022, got " + result.getYear();
        assert result.getMonthValue() == 5 : "Expected month 5, got " + result.getMonthValue();
    }

    static void testWithYearBoundary() {
        YearMonth ym = YearMonth.of(2000, 1);
        YearMonth result = ym.withYear(0);
        assert result.getYear() == 0 : "Expected year 0, got " + result.getYear();
        assert result.getMonthValue() == 1 : "Expected month 1, got " + result.getMonthValue();
    }

    static void testWithYearInvalid() {
        YearMonth ym = YearMonth.of(2020, 1);
        try {
            YearMonth result = ym.withYear(Integer.MIN_VALUE - 1); // invalid year
            // Expecting an exception or invalid behavior, but since checkValidValue throws, test for exception
            assert false : "Expected exception for invalid year";
        } catch (Exception e) {
            // Expected exception, test passes
        }
    }
}
