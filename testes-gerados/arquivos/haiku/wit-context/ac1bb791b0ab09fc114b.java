/*
 * @test
 * @summary WITUP-derived tests for YearMonth.of(int, Month)
 * @run main YearMonthWitupTest
 */

import java.time.DateTimeException;
import java.time.Month;
import java.time.YearMonth;

public class YearMonthWitupTest {

    public static void main(String[] args) {
        testNullMonthThrowsNPE();
        testValidMonthMatchesIntFactory();
        testYearOutOfRangeThrowsDateTimeException();
    }

    static void testNullMonthThrowsNPE() {
        boolean thrown = false;
        try {
            YearMonth.of(2020, (Month) null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("expected NullPointerException for null month");
        }
    }

    static void testValidMonthMatchesIntFactory() {
        YearMonth fromEnum = YearMonth.of(2020, Month.FEBRUARY);
        YearMonth fromInt = YearMonth.of(2020, 2);
        if (!fromEnum.equals(fromInt)) {
            throw new RuntimeException("of(int, Month) must equal of(int, int) for same values");
        }
        if (fromEnum.getYear() != 2020 || fromEnum.getMonthValue() != 2) {
            throw new RuntimeException("unexpected year or month value");
        }
    }

    static void testYearOutOfRangeThrowsDateTimeException() {
        checkYearRejected(1000000000);
        checkYearRejected(-1000000000);
    }

    static void checkYearRejected(int year) {
        boolean thrown = false;
        try {
            YearMonth.of(year, Month.JANUARY);
        } catch (DateTimeException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("expected DateTimeException for year " + year);
        }
    }
}
