/*
 * @test
 * @summary Witup-generated behavioral checks for LocalDateTime.minusMonths
 * @run main LocalDateTimeWitupTest
 */

import java.time.DateTimeException;
import java.time.LocalDateTime;

public class LocalDateTimeWitupTest {

    public static void main(String[] args) {
        LocalDateTime base = LocalDateTime.of(2024, 3, 15, 10, 20, 30, 123);

        check(base.minusMonths(1).equals(LocalDateTime.of(2024, 2, 15, 10, 20, 30, 123)), "minus one month");
        check(base.minusMonths(0).equals(base), "minus zero months");
        check(base.minusMonths(-1).equals(LocalDateTime.of(2024, 4, 15, 10, 20, 30, 123)), "minus negative months");
        check(base.minusMonths(12).equals(LocalDateTime.of(2023, 3, 15, 10, 20, 30, 123)), "minus twelve months");
        check(LocalDateTime.of(2024, 1, 15, 0, 0).minusMonths(1).equals(LocalDateTime.of(2023, 12, 15, 0, 0)), "year rollover");
        check(LocalDateTime.of(2024, 3, 31, 0, 0).minusMonths(1).equals(LocalDateTime.of(2024, 2, 29, 0, 0)), "day clamp in leap February");

        expectDateTimeException(() -> LocalDateTime.of(-999999999, 1, 1, 0, 0).minusMonths(1), "below minimum year");
        expectDateTimeException(() -> base.minusMonths(Long.MIN_VALUE), "Long.MIN_VALUE months");
    }

    private interface Action {
        void run();
    }

    private static void expectDateTimeException(Action action, String what) {
        try {
            action.run();
        } catch (DateTimeException expected) {
            return;
        }
        throw new RuntimeException("Expected DateTimeException for: " + what);
    }

    private static void check(boolean condition, String what) {
        if (!condition) {
            throw new RuntimeException("Check failed: " + what);
        }
    }
}
