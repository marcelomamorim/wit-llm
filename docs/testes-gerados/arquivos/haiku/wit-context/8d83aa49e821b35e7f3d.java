/*
 * @test
 * @summary Tests LocalTime.ofNanoOfDay boundaries and out-of-range rejection
 * @run main LocalTimeWitupTest
 */

import java.time.DateTimeException;
import java.time.LocalTime;

public class LocalTimeWitupTest {
    private static final long NANOS_PER_SECOND = 1_000_000_000L;
    private static final long NANOS_PER_DAY = 86_400L * NANOS_PER_SECOND;

    public static void main(String[] args) {
        check(LocalTime.ofNanoOfDay(0L).equals(LocalTime.of(0, 0)), "midnight");
        check(LocalTime.ofNanoOfDay(NANOS_PER_DAY - 1L).equals(LocalTime.of(23, 59, 59, 999_999_999)), "last nano of day");
        check(LocalTime.ofNanoOfDay(3600L * NANOS_PER_SECOND).equals(LocalTime.of(1, 0)), "one hour");
        check(LocalTime.ofNanoOfDay(90061L * NANOS_PER_SECOND + 123_456_789L)
                .equals(LocalTime.of(1, 1, 1, 123_456_789)), "mixed fields");
        check(LocalTime.ofNanoOfDay(1L).getNano() == 1, "one nano");

        long[] samples = {0L, 1L, NANOS_PER_SECOND, NANOS_PER_DAY - 1L, 12_345_678_901_234L};
        for (long v : samples) {
            check(LocalTime.ofNanoOfDay(v).toNanoOfDay() == v, "round trip " + v);
        }

        expectDateTimeException(-1L);
        expectDateTimeException(NANOS_PER_DAY);
        expectDateTimeException(Long.MIN_VALUE);
        expectDateTimeException(Long.MAX_VALUE);
    }

    private static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException("Failed: " + message);
        }
    }

    private static void expectDateTimeException(long nanoOfDay) {
        try {
            LocalTime t = LocalTime.ofNanoOfDay(nanoOfDay);
            throw new RuntimeException("No exception for " + nanoOfDay + ": " + t);
        } catch (DateTimeException expected) {
            // expected
        }
    }
}
