/*
 * @test
 * @summary Test YearMonth.getMonth() returns correct Month enum
 * @run main YearMonthWitupTest
 */

import java.time.YearMonth;
import java.time.Month;

public class YearMonthWitupTest {
    public static void main(String[] args) {
        testGetMonthReturnsCorrectMonth();
    }

    private static void testGetMonthReturnsCorrectMonth() {
        // Test with January
        YearMonth ymJan = YearMonth.of(2020, 1);
        Month mJan = ymJan.getMonth();
        if (mJan != Month.JANUARY) {
            throw new AssertionError("Expected JANUARY but got " + mJan);
        }

        // Test with December
        YearMonth ymDec = YearMonth.of(1999, 12);
        Month mDec = ymDec.getMonth();
        if (mDec != Month.DECEMBER) {
            throw new AssertionError("Expected DECEMBER but got " + mDec);
        }

        // Test with a middle month (June)
        YearMonth ymJun = YearMonth.of(2000, 6);
        Month mJun = ymJun.getMonth();
        if (mJun != Month.JUNE) {
            throw new AssertionError("Expected JUNE but got " + mJun);
        }
    }
}
