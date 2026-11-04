/*
 * @test
 * @summary Checks observable results of LocalDateTime.minusMonths, including the Long.MIN_VALUE path
 * @run main LocalDateTimeWitupTest
 */

import java.time.DateTimeException;
import java.time.LocalDateTime;

public class LocalDateTimeWitupTest {

    public static void main(String[] args) {
        LocalDateTime base = LocalDateTime.of(2020, 5, 15, 10, 30, 45, 123);

        check(base.minusMonths(0).equals(base), "zero months returns equal value");
        check(base.minusMonths(1).equals(LocalDateTime.of(2020, 4, 15, 10, 30, 45, 123)), "one month back");
        check(base.minusMonths(12).equals(LocalDateTime.of(2019, 5, 15, 10, 30, 45, 123)), "twelve months back");
        check(base.minusMonths(-12).equals(LocalDateTime.of(2021, 5, 15, 10, 30, 45, 123)), "negative months move forward");
        check(base.minusMonths(-1).equals(base.plusMonths(1)), "minusMonths(-n) equals plusMonths(n)");

        check(LocalDateTime.of(2020, 3, 31, 0, 0).minusMonths(1)
                .equals(LocalDateTime.of(2020, 2, 29, 0, 0)), "day clamped to leap February");
        check(LocalDateTime.of(2020, 1, 1, 0, 0).minusMonths(1)
                .equals(LocalDateTime.of(2019, 12, 1, 0, 0)), "year boundary crossed backwards");

        boolean thrown = false;
        try {
            base.minusMonths(Long.MIN_VALUE);
        } catch (DateTimeException e) {
            thrown = true;
        }
        check(thrown, "minusMonths(Long.MIN_VALUE) rejects out-of-range year");
    }

    private static void check(boolean condition, String name) {
        if (!condition) {
            throw new RuntimeException("Check failed: " + name);
        }
    }
}
