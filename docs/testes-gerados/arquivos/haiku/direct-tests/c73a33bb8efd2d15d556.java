/*
 * @test
 * @summary Verifies Duration.plus(Duration) delegates to seconds/nanos addition, including nano carry, negative values, zero and error cases
 * @run main DurationWitupTest
 */

import java.time.Duration;

public class DurationWitupTest {

    public static void main(String[] args) {
        checkSimpleAddition();
        checkNanoCarry();
        checkNegativeNanoBorrow();
        checkNegativeOperands();
        checkZeroIdentity();
        checkNullArgument();
        checkOverflow();
    }

    static void checkSimpleAddition() {
        check(Duration.ofSeconds(1).plus(Duration.ofSeconds(2)), 3L, 0, "1s + 2s");
    }

    static void checkNanoCarry() {
        Duration a = Duration.ofSeconds(1, 500_000_000L);
        check(a.plus(a), 3L, 0, "1.5s + 1.5s");
    }

    static void checkNegativeNanoBorrow() {
        check(Duration.ofSeconds(1).plus(Duration.ofNanos(-1)), 0L, 999_999_999, "1s + (-1ns)");
    }

    static void checkNegativeOperands() {
        check(Duration.ofSeconds(5).plus(Duration.ofSeconds(-7)), -2L, 0, "5s + (-7s)");
    }

    static void checkZeroIdentity() {
        Duration d = Duration.ofSeconds(42, 123_456_789L);
        check(d.plus(Duration.ZERO), 42L, 123_456_789, "d + ZERO");
        check(Duration.ZERO.plus(Duration.ZERO), 0L, 0, "ZERO + ZERO");
    }

    static void checkNullArgument() {
        boolean thrown = false;
        try {
            Duration.ofSeconds(1).plus((Duration) null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("expected NullPointerException for null argument");
        }
    }

    static void checkOverflow() {
        boolean thrown = false;
        try {
            Duration.ofSeconds(Long.MAX_VALUE).plus(Duration.ofSeconds(1));
        } catch (ArithmeticException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("expected ArithmeticException on seconds overflow");
        }
    }

    static void check(Duration d, long expectedSeconds, int expectedNanos, String label) {
        if (d.getSeconds() != expectedSeconds || d.getNano() != expectedNanos) {
            throw new RuntimeException(label + ": expected " + expectedSeconds + "s " + expectedNanos
                    + "ns but was " + d.getSeconds() + "s " + d.getNano() + "ns");
        }
    }
}
