/*
 * @test
 * @summary Tests Instant.minusMillis for normal values, Long.MIN_VALUE and range overflow
 * @run main InstantWitupTest
 */

import java.time.DateTimeException;
import java.time.Instant;

public class InstantWitupTest {

    public static void main(String[] args) {
        check(Instant.EPOCH.minusMillis(1000L), Instant.ofEpochSecond(-1L), "epoch minus 1s");
        check(Instant.EPOCH.minusMillis(-1000L), Instant.ofEpochSecond(1L), "epoch minus -1s");
        check(Instant.ofEpochSecond(10L, 500_000_000L).minusMillis(500L),
              Instant.ofEpochSecond(10L), "fractional seconds");
        check(Instant.ofEpochSecond(0L, 1_000_000L).minusMillis(1L),
              Instant.EPOCH, "sub-second result");
        check(Instant.EPOCH.minusMillis(Long.MIN_VALUE),
              Instant.ofEpochSecond(9223372036854775L, 808000000L), "Long.MIN_VALUE argument");
        checkThrows(() -> Instant.MIN.minusMillis(1L), "underflow below Instant.MIN");
        checkThrows(() -> Instant.MAX.minusMillis(-1L), "overflow above Instant.MAX");
    }

    private static void check(Instant actual, Instant expected, String label) {
        if (!expected.equals(actual)) {
            throw new RuntimeException(label + ": expected " + expected + " but was " + actual);
        }
    }

    private static void checkThrows(Runnable r, String label) {
        try {
            r.run();
        } catch (DateTimeException e) {
            return;
        }
        throw new RuntimeException(label + ": DateTimeException expected");
    }
}
