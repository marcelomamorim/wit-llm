/*
 * @test
 * @summary Witup-derived checks for LocalDateTime.minusSeconds overflow and boundary behavior
 * @run main LocalDateTimeWitupTest
 */

import java.time.DateTimeException;
import java.time.LocalDateTime;

public class LocalDateTimeWitupTest {

    public static void main(String[] args) {
        LocalDateTime base = LocalDateTime.of(2020, 1, 1, 0, 0);

        check(base.minusSeconds(0).equals(base), "minusSeconds(0) must return an equal value");
        check(base.minusSeconds(1).equals(LocalDateTime.of(2019, 12, 31, 23, 59, 59)),
              "minusSeconds(1) must step back one second across midnight");
        check(base.minusSeconds(86400L).equals(LocalDateTime.of(2019, 12, 31, 0, 0)),
              "minusSeconds(86400) must step back one day");
        check(base.minusSeconds(-1).equals(LocalDateTime.of(2020, 1, 1, 0, 0, 1)),
              "minusSeconds(-1) must add one second");

        expectDateTimeException(() -> LocalDateTime.MIN.minusSeconds(1),
              "minusSeconds(1) from LocalDateTime.MIN must overflow");
        expectDateTimeException(() -> LocalDateTime.MAX.minusSeconds(-1),
              "minusSeconds(-1) from LocalDateTime.MAX must overflow");
        expectDateTimeException(() -> base.minusSeconds(Long.MAX_VALUE),
              "minusSeconds(Long.MAX_VALUE) must overflow the supported range");
    }

    private interface Action {
        void run();
    }

    private static void expectDateTimeException(Action action, String message) {
        try {
            action.run();
        } catch (DateTimeException e) {
            return;
        }
        throw new RuntimeException(message);
    }

    private static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
