/*
 * @test
 * @summary Witup-generated tests for Duration.plus(Duration), covering normal sums, nanosecond carry and seconds overflow
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

    static void testNormalSum() {
        Duration a = Duration.of(1, ChronoUnit.SECONDS).plus(Duration.of(500_000_000L, ChronoUnit.NANOS));
        Duration b = Duration.of(2, ChronoUnit.SECONDS).plus(Duration.of(600_000_000L, ChronoUnit.NANOS));
        Duration sum = a.plus(b);
        check(sum.getSeconds() == 4L, "seconds expected 4 but was " + sum.getSeconds());
        check(sum.getNano() == 100_000_000, "nanos expected 100000000 but was " + sum.getNano());
    }

    static void testNanoCarryIntoSeconds() {
        Duration a = Duration.of(0, ChronoUnit.SECONDS).plus(Duration.of(600_000_000L, ChronoUnit.NANOS));
        Duration b = Duration.of(0, ChronoUnit.SECONDS).plus(Duration.of(700_000_000L, ChronoUnit.NANOS));
        Duration sum = a.plus(b);
        check(sum.getSeconds() == 1L, "seconds expected 1 but was " + sum.getSeconds());
        check(sum.getNano() == 300_000_000, "nanos expected 300000000 but was " + sum.getNano());
    }

    static void testZeroIdentity() {
        Duration d = Duration.of(42, ChronoUnit.SECONDS);
        check(d.plus(Duration.ZERO).equals(d), "adding ZERO must return an equal duration");
        check(Duration.ZERO.plus(Duration.ZERO).equals(Duration.ZERO), "ZERO plus ZERO must equal ZERO");
    }

    static void testNegativeSum() {
        Duration a = Duration.of(-5, ChronoUnit.SECONDS);
        Duration b = Duration.of(2, ChronoUnit.SECONDS);
        Duration sum = a.plus(b);
        check(sum.getSeconds() == -3L, "seconds expected -3 but was " + sum.getSeconds());
        check(sum.getNano() == 0, "nanos expected 0 but was " + sum.getNano());
    }

    static void testOverflowWithNanosInArgument() {
        Duration max = Duration.of(Long.MAX_VALUE, ChronoUnit.SECONDS);
        Duration arg = Duration.of(1_000_000_001L, ChronoUnit.NANOS);
        boolean thrown = false;
        try {
            max.plus(arg);
        } catch (ArithmeticException e) {
            thrown = true;
        }
        check(thrown, "expected ArithmeticException on seconds overflow with nanos in argument");
    }

    static void testOverflowSecondsOnly() {
        Duration max = Duration.of(Long.MAX_VALUE, ChronoUnit.SECONDS);
        Duration one = Duration.of(1, ChronoUnit.SECONDS);
        boolean thrown = false;
        try {
            max.plus(one);
        } catch (ArithmeticException e) {
            thrown = true;
        }
        check(thrown, "expected ArithmeticException on positive seconds overflow");
    }

    static void testNegativeOverflow() {
        Duration min = Duration.of(Long.MIN_VALUE, ChronoUnit.SECONDS);
        Duration minusOne = Duration.of(-1, ChronoUnit.SECONDS);
        boolean thrown = false;
        try {
            min.plus(minusOne);
        } catch (ArithmeticException e) {
            thrown = true;
        }
        check(thrown, "expected ArithmeticException on negative seconds overflow");
    }

    public static void main(String[] args) {
        testNormalSum();
        testNanoCarryIntoSeconds();
        testZeroIdentity();
        testNegativeSum();
        testOverflowWithNanosInArgument();
        testOverflowSecondsOnly();
        testNegativeOverflow();
    }
}
