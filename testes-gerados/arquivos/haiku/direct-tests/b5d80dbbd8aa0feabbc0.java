/*
 * @test
 * @summary Checks LocalDate.minusMonths for month clamping, year rollover, zero and negative arguments
 * @run main LocalDateWitupTest
 */

import java.time.LocalDate;

public class LocalDateWitupTest {

    public static void main(String[] args) {
        LocalDate r1 = LocalDate.of(2020, 3, 31).minusMonths(1);
        check(r1.equals(LocalDate.of(2020, 2, 29)), "2020-03-31 minus 1 month must clamp to 2020-02-29, got " + r1);

        LocalDate r2 = LocalDate.of(2020, 1, 15).minusMonths(1);
        check(r2.equals(LocalDate.of(2019, 12, 15)), "2020-01-15 minus 1 month must be 2019-12-15, got " + r2);

        LocalDate r3 = LocalDate.of(2020, 1, 15).minusMonths(13);
        check(r3.equals(LocalDate.of(2018, 12, 15)), "2020-01-15 minus 13 months must be 2018-12-15, got " + r3);

        LocalDate base = LocalDate.of(2020, 5, 10);
        check(base.minusMonths(0).equals(base), "minusMonths(0) must return an equal date");

        check(base.minusMonths(-2).equals(LocalDate.of(2020, 7, 10)), "minusMonths(-2) must add two months");
        check(base.minusMonths(-2).equals(base.plusMonths(2)), "minusMonths(-n) must equal plusMonths(n)");

        long[] samples = {1L, 2L, 11L, 12L, 13L, 24L, 100L, -1L, -12L};
        for (long n : samples) {
            check(base.minusMonths(n).equals(base.plusMonths(-n)),
                  "minusMonths(" + n + ") must equal plusMonths(" + (-n) + ")");
        }

        LocalDate r4 = LocalDate.of(2000, 3, 1).minusMonths(1);
        check(r4.equals(LocalDate.of(2000, 2, 1)), "2000-03-01 minus 1 month must be 2000-02-01, got " + r4);

        LocalDate r5 = LocalDate.of(2021, 3, 31).minusMonths(1);
        check(r5.getDayOfMonth() == 28 && r5.getMonthValue() == 2 && r5.getYear() == 2021,
              "2021-03-31 minus 1 month must be 2021-02-28, got " + r5);
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
