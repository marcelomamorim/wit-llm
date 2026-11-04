/*
 * @test
 * @summary Witup-derived tests for Instant.plusMillis normal results and range exceptions
 * @run main InstantWitupTest
 */

import java.time.DateTimeException;
import java.time.Instant;

public class InstantWitupTest {

    public static void main(String[] args) {
        check(Instant.ofEpochSecond(0).plusMillis(1500), 1, 500_000_000);
        check(Instant.ofEpochSecond(10).plusMillis(-1), 9, 999_000_000);
        check(Instant.ofEpochSecond(5, 123).plusMillis(0), 5, 123);
        check(Instant.ofEpochSecond(-1).plusMillis(2001), 1, 1_000_000);
        check(Instant.ofEpochSecond(0, 500).plusMillis(-1500), -2, 500_000_500);

        expectThrows(DateTimeException.class,
                () -> Instant.MAX.plusMillis(1));
        expectThrows(DateTimeException.class,
                () -> Instant.MIN.plusMillis(-1));
        expectThrows(DateTimeException.class,
                () -> Instant.ofEpochSecond(0).plusMillis(Long.MAX_VALUE));
    }

    private static void check(Instant actual, long expectedSeconds, long expectedNanos) {
        if (actual.getEpochSecond() != expectedSeconds || actual.getNano() != expectedNanos) {
            throw new RuntimeException("Unexpected result: " + actual
                    + " expected seconds=" + expectedSeconds + " nanos=" + expectedNanos);
        }
    }

    private interface Action {
        void run();
    }

    private static void expectThrows(Class<? extends Throwable> type, Runnable action) {
        try {
            action.run();
        } catch (Throwable t) {
            if (type.isInstance(t)) {
                return;
            }
            throw new RuntimeException("Unexpected exception type: " + t, t);
        }
        throw new RuntimeException("Expected " + type.getName() + " was not thrown");
    }
}
