/*
 * @test
 * @summary Verifies YearMonth.lengthOfMonth for leap, non-leap and boundary years
 * @run main YearMonthWitupTest
 */
import java.time.LocalDate;
import java.time.Month;
import java.time.YearMonth;

public class YearMonthWitupTest {

    public static void main(String[] args) {
        int[] expected2019 = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
        int[] expected2020 = {31, 29, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};

        for (int m = 1; m <= 12; m++) {
            check(YearMonth.of(2019, m).lengthOfMonth(), expected2019[m - 1], "2019-" + m);
            check(YearMonth.of(2020, m).lengthOfMonth(), expected2020[m - 1], "2020-" + m);
        }

        check(YearMonth.of(1900, 2).lengthOfMonth(), 28, "1900-02");
        check(YearMonth.of(2000, 2).lengthOfMonth(), 29, "2000-02");
        check(YearMonth.of(2100, 2).lengthOfMonth(), 28, "2100-02");
        check(YearMonth.of(0, 2).lengthOfMonth(), 29, "0000-02");
        check(YearMonth.of(2020, Month.FEBRUARY).lengthOfMonth(), 29, "enum 2020-02");
        check(YearMonth.from(LocalDate.of(2024, 2, 10)).lengthOfMonth(), 29, "from 2024-02-10");
        check(YearMonth.from(LocalDate.of(2023, 12, 1)).lengthOfMonth(), 31, "from 2023-12-01");
    }

    private static void check(int actual, int expected, String label) {
        if (actual != expected) {
            throw new AssertionError(label + ": expected " + expected + " but was " + actual);
        }
    }
}
