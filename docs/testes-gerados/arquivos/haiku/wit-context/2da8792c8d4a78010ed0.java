/*
 * @test
 * @summary Tests LocalTime.withSecond(int) for same-value identity, valid updates and DateTimeException on invalid seconds
 * @run main LocalTimeWitupTest
 */

import java.time.DateTimeException;
import java.time.LocalTime;

public class LocalTimeWitupTest {

    interface Action {
        void run();
    }

    public static void main(String[] args) {
        LocalTime t = LocalTime.of(10, 20, 30, 40);

        LocalTime same = t.withSecond(30);
        if (same != t) {
            throw new RuntimeException("withSecond with same second must return this");
        }

        LocalTime changed = t.withSecond(5);
        check(changed.getHour() == 10, "hour changed");
        check(changed.getMinute() == 20, "minute changed");
        check(changed.getSecond() == 5, "second not updated");
        check(changed.getNano() == 40, "nano changed");

        check(t.withSecond(0).getSecond() == 0, "lower bound 0");
        check(t.withSecond(59).getSecond() == 59, "upper bound 59");

        expectDateTimeException(() -> t.withSecond(60));
        expectDateTimeException(() -> t.withSecond(-1));
        expectDateTimeException(() -> t.withSecond(Integer.MIN_VALUE));
        expectDateTimeException(() -> t.withSecond(Integer.MAX_VALUE));
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void expectDateTimeException(Action action) {
        try {
            action.run();
        } catch (DateTimeException expected) {
            return;
        }
        throw new RuntimeException("Expected DateTimeException was not thrown");
    }
}
