/*
 * @test
 * @summary Tests for YearMonth.withYear(int) observable behavior
 * @run main YearMonthWitupTest
 */

import java.time.DateTimeException;
import java.time.YearMonth;

public class YearMonthWitupTest {

    public static void main(String[] args) {
        testChangesYearKeepsMonth();
        testSameYearReturnsEqualValue();
        testBoundaryYearsAccepted();
        testOutOfRangeYearsRejected();
        testOriginalIsImmutable();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testChangesYearKeepsMonth() {
        YearMonth ym = YearMonth.of(2000, 5);
        YearMonth result = ym.withYear(2010);
        check(result.getYear() == 2010, "year should be 2010");
        check(result.getMonthValue() == 5, "month should remain 5");
        check(result.equals(YearMonth.of(2010, 5)), "result should equal 2010-05");
    }

    static void testSameYearReturnsEqualValue() {
        YearMonth ym = YearMonth.of(1999, 12);
        YearMonth result = ym.withYear(1999);
        check(result.equals(ym), "same year should yield equal YearMonth");
        check(result.getMonthValue() == 12, "month should remain 12");
    }

    static void testBoundaryYearsAccepted() {
        YearMonth min = YearMonth.of(2000, 1).withYear(-999999999);
        check(min.getYear() == -999999999, "minimum year should be accepted");
        check(min.getMonthValue() == 1, "month should remain 1 at minimum year");

        YearMonth max = YearMonth.of(2000, 12).withYear(999999999);
        check(max.getYear() == 999999999, "maximum year should be accepted");
        check(max.getMonthValue() == 12, "month should remain 12 at maximum year");

        YearMonth zero = YearMonth.of(2000, 6).withYear(0);
        check(zero.getYear() == 0, "year zero should be accepted");
    }

    static void testOutOfRangeYearsRejected() {
        try {
            YearMonth.of(2000, 5).withYear(1000000000);
            throw new RuntimeException("expected DateTimeException for year above maximum");
        } catch (DateTimeException expected) {
            // expected
        }
        try {
            YearMonth.of(2000, 5).withYear(-1000000000);
            throw new RuntimeException("expected DateTimeException for year below minimum");
        } catch (DateTimeException expected) {
            // expected
        }
    }

    static void testOriginalIsImmutable() {
        YearMonth ym = YearMonth.of(2001, 7);
        YearMonth changed = ym.withYear(2002);
        check(ym.getYear() == 2001, "original year must not change");
        check(changed.getYear() == 2002, "new instance must have new year");
        check(changed != ym, "withYear should return a new instance when year differs");
    }
}
