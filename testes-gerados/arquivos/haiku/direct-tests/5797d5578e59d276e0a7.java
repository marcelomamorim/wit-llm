/*
 * @test
 * @summary Behavioral tests for Duration.withNanos(int) on the public API
 * @run main DurationWitupTest
 */

import java.time.Duration;
import java.time.temporal.ChronoUnit;

public class DurationWitupTest {

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testReplacesNanosKeepingSeconds() {
        Duration d = Duration.of(5, ChronoUnit.SECONDS).withNanos(123);
        check(d.getSeconds() == 5, "seconds should be preserved, was " + d.getSeconds());
        check(d.getNano() == 123, "nano should be 123, was " + d.getNano());
    }

    static void testBoundaryMaxNanos() {
        Duration d = Duration.of(5, ChronoUnit.SECONDS).withNanos(999999999);
        check(d.getSeconds() == 5, "seconds should be 5");
        check(d.getNano() == 999999999, "nano should be 999999999, was " + d.getNano());
    }

    static void testBoundaryZeroNanos() {
        Duration d = Duration.of(7, ChronoUnit.SECONDS).withNanos(0);
        check(d.getSeconds() == 7, "seconds should be 7");
        check(d.getNano() == 0, "nano should be 0");
        check(d.equals(Duration.of(7, ChronoUnit.SECONDS)), "withNanos(0) should equal whole-second duration");
    }

    static void testNegativeSecondsWithPositiveNanos() {
        Duration d = Duration.of(-1, ChronoUnit.SECONDS).withNanos(500);
        check(d.getSeconds() == -1, "seconds should be -1, was " + d.getSeconds());
        check(d.getNano() == 500, "nano should be 500, was " + d.getNano());
    }

    static void testZeroSecondsDuration() {
        Duration d = Duration.of(0, ChronoUnit.SECONDS).withNanos(1);
        check(d.getSeconds() == 0, "seconds should be 0");
        check(d.getNano() == 1, "nano should be 1");
    }

    static void testReturnsNewInstance() {
        Duration original = Duration.of(3, ChronoUnit.SECONDS);
        Duration changed = original.withNanos(42);
        check(original.getNano() == 0, "original must not be modified");
        check(changed.getNano() == 42, "new instance should hold 42 nanos");
    }

    static void expectInvalidNanos(int nanos) {
        try {
            Duration.of(1, ChronoUnit.SECONDS).withNanos(nanos);
        } catch (RuntimeException e) {
            check("DateTimeException".equals(e.getClass().getSimpleName()),
                  "expected DateTimeException for " + nanos + " but got " + e.getClass().getName());
            return;
        }
        throw new RuntimeException("withNanos(" + nanos + ") should have failed");
    }

    static void testRejectsOutOfRangeNanos() {
        expectInvalidNanos(-1);
        expectInvalidNanos(1000000000);
        expectInvalidNanos(Integer.MIN_VALUE);
        expectInvalidNanos(Integer.MAX_VALUE);
    }

    public static void main(String[] args) {
        testReplacesNanosKeepingSeconds();
        testBoundaryMaxNanos();
        testBoundaryZeroNanos();
        testNegativeSecondsWithPositiveNanos();
        testZeroSecondsDuration();
        testReturnsNewInstance();
        testRejectsOutOfRangeNanos();
    }
}
