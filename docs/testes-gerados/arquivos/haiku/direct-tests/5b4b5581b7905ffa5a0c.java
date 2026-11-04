/*
 * @test
 * @summary Behavioral tests for Duration.of(long, TemporalUnit)
 * @run main DurationWitupTest
 */

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.time.temporal.UnsupportedTemporalTypeException;

public class DurationWitupTest {

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testSecondsUnit() {
        Duration d = Duration.of(5, ChronoUnit.SECONDS);
        check(d.getSeconds() == 5L, "seconds: expected 5 but was " + d.getSeconds());
        check(d.getNano() == 0, "nano: expected 0 but was " + d.getNano());
    }

    static void testNegativeSeconds() {
        Duration d = Duration.of(-3, ChronoUnit.SECONDS);
        check(d.getSeconds() == -3L, "negative seconds: expected -3 but was " + d.getSeconds());
        check(d.getNano() == 0, "negative nano: expected 0 but was " + d.getNano());
    }

    static void testNanosUnit() {
        Duration d = Duration.of(1, ChronoUnit.NANOS);
        check(d.getSeconds() == 0L, "nanos seconds: expected 0 but was " + d.getSeconds());
        check(d.getNano() == 1, "nanos nano: expected 1 but was " + d.getNano());
    }

    static void testMillisUnit() {
        Duration d = Duration.of(1500, ChronoUnit.MILLIS);
        check(d.getSeconds() == 1L, "millis seconds: expected 1 but was " + d.getSeconds());
        check(d.getNano() == 500_000_000, "millis nano: expected 500000000 but was " + d.getNano());
    }

    static void testDaysUnit() {
        Duration d = Duration.of(1, ChronoUnit.DAYS);
        check(d.getSeconds() == 86400L, "days seconds: expected 86400 but was " + d.getSeconds());
        check(d.getNano() == 0, "days nano: expected 0 but was " + d.getNano());
    }

    static void testZeroAmount() {
        Duration d = Duration.of(0, ChronoUnit.HOURS);
        check(d.isZero(), "zero amount should yield zero duration");
        check(d.equals(Duration.ZERO), "zero amount should equal Duration.ZERO");
    }

    static void testEquivalentValues() {
        Duration a = Duration.of(60, ChronoUnit.SECONDS);
        Duration b = Duration.of(1, ChronoUnit.MINUTES);
        check(a.equals(b), "60 seconds should equal 1 minute");
    }

    static void testEstimatedUnitRejected() {
        boolean thrown = false;
        try {
            Duration.of(1, ChronoUnit.MONTHS);
        } catch (UnsupportedTemporalTypeException e) {
            thrown = true;
        }
        check(thrown, "MONTHS (estimated duration) should throw UnsupportedTemporalTypeException");
    }

    static void testDaysOverflow() {
        boolean thrown = false;
        try {
            Duration.of(Long.MAX_VALUE, ChronoUnit.DAYS);
        } catch (ArithmeticException e) {
            thrown = true;
        }
        check(thrown, "Long.MAX_VALUE DAYS should overflow with ArithmeticException");
    }

    public static void main(String[] args) {
        testSecondsUnit();
        testNegativeSeconds();
        testNanosUnit();
        testMillisUnit();
        testDaysUnit();
        testZeroAmount();
        testEquivalentValues();
        testEstimatedUnitRejected();
        testDaysOverflow();
    }
}
