/*
 * @test
 * @summary Test YearMonth.of(int, Month) method for valid and null inputs
 * @run main YearMonthWitupTest
 */

import java.time.YearMonth;
import java.time.Month;
import java.util.Objects;

public class YearMonthWitupTest {

    public static void main(String[] args) {
        testOfValidInputs();
        testOfNullMonth();
    }

    static void testOfValidInputs() {
        int year = 2023;
        Month month = Month.AUGUST;
        YearMonth ym = YearMonth.of(year, month);
        assert ym.getYear() == year : "Year mismatch";
        assert ym.getMonth() == month : "Month mismatch";
    }

    static void testOfNullMonth() {
        int year = 2023;
        try {
            YearMonth.of(year, null);
            assert false : "Expected NullPointerException";
        } catch (NullPointerException e) {
            // expected
        }
    }
}
