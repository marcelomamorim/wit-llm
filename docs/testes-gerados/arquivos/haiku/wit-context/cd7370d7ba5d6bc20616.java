/*
 * @test
 * @summary Verifies YearMonth.lengthOfMonth for regular, leap, and century years
 * @run main YearMonthWitupTest
 */

import java.time.Month;
import java.time.YearMonth;

public class YearMonthWitupTest {

    public static void main(String[] args) {
        check(YearMonth.of(2000, 1).lengthOfMonth(), 31, "jan 2000");
        check(YearMonth.of(2023, 4).lengthOfMonth(), 30, "apr 2023");
        check(YearMonth.of(2023, 12).lengthOfMonth(), 31, "dec 2023");
        check(YearMonth.of(2000, 2).lengthOfMonth(), 29, "feb 2000 leap century");
        check(YearMonth.of(2024, 2).lengthOfMonth(), 29, "feb 2024 leap");
        check(YearMonth.of(2023, 2).lengthOfMonth(), 28, "feb 2023 common");
        check(YearMonth.of(1900, 2).lengthOfMonth(), 28, "feb 1900 non-leap century");
        check(YearMonth.of(0, 2).lengthOfMonth(), 29, "feb year 0 proleptic leap");
        check(YearMonth.of(2023, Month.SEPTEMBER).lengthOfMonth(), 30, "sep via Month");
        check(YearMonth.of(2024, Month.FEBRUARY).lengthOfMonth(), 29, "feb 2024 via Month");
    }

    private static void check(int actual, int expected, String label) {
        if (actual != expected) {
            throw new RuntimeException("Unexpected length for " + label + ": expected " + expected + " but was " + actual);
        }
    }
}
