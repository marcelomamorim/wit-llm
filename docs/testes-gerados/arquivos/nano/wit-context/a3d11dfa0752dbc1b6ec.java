/*
 * @test
 * @summary Teste do método plusYears de java.time.YearMonth
 * @run main YearMonthWitupTest
 */

import java.time.YearMonth;
import static java.time.temporal.ChronoField.YEAR;

public class YearMonthWitupTest {

    public static void main(String[] args) {
        testPlusYearsZero();
        testPlusYearsPositive();
        testPlusYearsNegative();
        testPlusYearsOverflow();
    }

    static void testPlusYearsZero() {
        YearMonth ym = YearMonth.of(2020, 5);
        YearMonth result = ym.plusYears(0);
        if (result.equals(ym)) {
            System.out.println("PASS: plusYears(0)");
        } else {
            System.out.println("FAIL: plusYears(0)");
        }
    }

    static void testPlusYearsPositive() {
        YearMonth ym = YearMonth.of(2020, 5);
        YearMonth result = ym.plusYears(5);
        YearMonth expected = YearMonth.of(2025, 5);
        if (result.equals(expected)) {
            System.out.println("PASS: plusYears(5)");
        } else {
            System.out.println("FAIL: plusYears(5)");
        }
    }

    static void testPlusYearsNegative() {
        YearMonth ym = YearMonth.of(2020, 5);
        YearMonth result = ym.plusYears(-3);
        YearMonth expected = YearMonth.of(2017, 5);
        if (result.equals(expected)) {
            System.out.println("PASS: plusYears(-3)");
        } else {
            System.out.println("FAIL: plusYears(-3)");
        }
    }

    static void testPlusYearsOverflow() {
        try {
            YearMonth ym = YearMonth.of(YearMonth.MAX_YEAR, 12);
            ym.plusYears(Long.MAX_VALUE);
            System.out.println("FAIL: plusYears(Long.MAX_VALUE) did not throw");
        } catch (Exception e) {
            System.out.println("PASS: plusYears(Long.MAX_VALUE) threw " + e.getClass().getSimpleName());
        }
        try {
            YearMonth ym = YearMonth.of(YearMonth.MIN_YEAR, 1);
            ym.plusYears(Long.MIN_VALUE);
            System.out.println("FAIL: plusYears(Long.MIN_VALUE) did not throw");
        } catch (Exception e) {
            System.out.println("PASS: plusYears(Long.MIN_VALUE) threw " + e.getClass().getSimpleName());
        }
    }
}
