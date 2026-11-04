/*
 * @test
 * @summary Verifies Duration.get(TemporalUnit) for SECONDS, NANOS and unsupported units
 * @run main DurationWitupTest
 */

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.time.temporal.UnsupportedTemporalTypeException;

public class DurationWitupTest {

    public static void main(String[] args) {
        check(Duration.ZERO.get(ChronoUnit.SECONDS) == 0L, "zero seconds");
        check(Duration.ZERO.get(ChronoUnit.NANOS) == 0L, "zero nanos");

        Duration fiveSeconds = Duration.of(5, ChronoUnit.SECONDS);
        check(fiveSeconds.get(ChronoUnit.SECONDS) == 5L, "seconds of 5s");
        check(fiveSeconds.get(ChronoUnit.NANOS) == 0L, "nanos of 5s");

        Duration sevenNanos = Duration.of(7, ChronoUnit.NANOS);
        check(sevenNanos.get(ChronoUnit.SECONDS) == 0L, "seconds of 7ns");
        check(sevenNanos.get(ChronoUnit.NANOS) == 7L, "nanos of 7ns");

        // -1500 ms is stored as seconds = -2 and nanos = 500000000
        Duration negative = Duration.of(-1500, ChronoUnit.MILLIS);
        check(negative.get(ChronoUnit.SECONDS) == -2L, "negative seconds");
        check(negative.get(ChronoUnit.NANOS) == 500_000_000L, "negative nanos");

        expectUnsupported(ChronoUnit.DAYS);
        expectUnsupported(ChronoUnit.MINUTES);
        expectUnsupported(ChronoUnit.MILLIS);
        expectUnsupported(null);
    }

    static void expectUnsupported(ChronoUnit unit) {
        try {
            Duration.ZERO.get(unit);
        } catch (UnsupportedTemporalTypeException e) {
            return;
        }
        throw new RuntimeException("No exception for unit: " + unit);
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
