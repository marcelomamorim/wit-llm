/*
 * @test
 * @summary Verifies Instant.minusNanos for small values, borrow across seconds, negative arguments and Long.MIN_VALUE
 * @run main InstantWitupTest
 */

import java.time.Instant;

public class InstantWitupTest {
    public static void main(String[] args) {
        Instant base = Instant.ofEpochSecond(10L, 500L);
        check(base.minusNanos(200L).equals(Instant.ofEpochSecond(10L, 300L)), minus small value);
        check(base.minusNanos(0L).equals(base), minus zero must return equal instant);
        check(base.minusNanos(600L).equals(Instant.ofEpochSecond(9L, 999999900L)), minus with borrow from seconds);
        check(Instant.EPOCH.minusNanos(1000000000L).equals(Instant.ofEpochSecond(-1L, 0L)), minus one second worth of nanos);
        check(Instant.EPOCH.minusNanos(-1L).equals(Instant.ofEpochSecond(0L, 1L)), minus negative value must add);
        check(base.minusNanos(12345L).plusNanos(12345L).equals(base), minus followed by plus must round trip);
        Instant r = Instant.EPOCH.minusNanos(Long.MIN_VALUE);
        check(r.getEpochSecond() == 9223372036L, Long.MIN_VALUE seconds mismatch);
        check(r.getNano() == 854775808, Long.MIN_VALUE nanos mismatch);
        check(r.equals(Instant.ofEpochSecond(9223372036L, 854775808L)), Long.MIN_VALUE result mismatch);
    }

    private static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
