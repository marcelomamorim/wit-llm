/*
 * @test
 * @summary Witup-derived checks for Duration.get(TemporalUnit): SECONDS and NANOS are supported, other units throw UnsupportedTemporalTypeException
 * @run main DurationWitupTest
 */

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.time.temporal.UnsupportedTemporalTypeException;

public class DurationWitupTest {

    public static void main(String[] args) {
        testSecondsAndNanosObservable();
        testNormalizedNegativeDuration();
        testUnsupportedUnitsThrow();
        testNullUnitThrows();
    }

    static void testSecondsAndNanosObservable() {
        Duration d = Duration.of(1500, ChronoUnit.MILLIS);
        check(d.get(ChronoUnit.SECONDS) == 1L, "seconds of 1500ms");
        check(d.get(ChronoUnit.NANOS) == 500_000_000L, "nanos of 1500ms");

        Duration s = Duration.of(2, ChronoUnit.SECONDS);
        check(s.get(ChronoUnit.SECONDS) == 2L, "seconds of 2s");
        check(s.get(ChronoUnit.NANOS) == 0L, "nanos of 2s");
    }

    static void testNormalizedNegativeDuration() {
        Duration d = Duration.of(-1, ChronoUnit.SECONDS);
        check(d.get(ChronoUnit.SECONDS) == -1L, "seconds of -1s");
        check(d.get(ChronoUnit.NANOS) == 0L, "nanos of -1s");
    }

    static void testUnsupportedUnitsThrow() {
        Duration d = Duration.of(3, ChronoUnit.SECONDS);
        expectUnsupported(d, ChronoUnit.DAYS);
        expectUnsupported(d, ChronoUnit.HOURS);
        expectUnsupported(d, ChronoUnit.MILLIS);
    }

    static void testNullUnitThrows() {
        Duration d = Duration.of(3, ChronoUnit.SECONDS);
        expectUnsupported(d, null);
    }

    static void expectUnsupported(Duration d, ChronoUnit unit) {
        try {
            d.get(unit);
        } catch (UnsupportedTemporalTypeException e) {
            return;
        }
        throw new RuntimeException("Expected UnsupportedTemporalTypeException for unit: " + unit);
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException("Check failed: " + message);
        }
    }
}
