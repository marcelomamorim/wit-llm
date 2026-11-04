/*
 * @test
 * @summary Verifies Duration.minusSeconds observable behavior, including the Long.MIN_VALUE branch
 * @run main DurationWitupTest
 */

import java.time.Duration;
import java.time.temporal.ChronoUnit;

public class DurationWitupTest {

    static int failures = 0;

    static void check(boolean condition, String message) {
        if (!condition) {
            failures++;
            System.err.println("FAILED: " + message);
        }
    }

    static void testSimpleSubtraction() {
        Duration d = Duration.of(10, ChronoUnit.SECONDS).minusSeconds(5);
        check(d.getSeconds() == 5, "10s - 5s should be 5s, got " + d.getSeconds());
        check(d.getNano() == 0, "nanos should be 0");
    }

    static void testSubtractZero() {
        Duration d = Duration.of(7, ChronoUnit.SECONDS).minusSeconds(0);
        check(d.getSeconds() == 7, "subtracting 0 should keep 7s, got " + d.getSeconds());
        check(d.getNano() == 0, "nanos should remain 0");
    }

    static void testSubtractNegative() {
        Duration d = Duration.of(10, ChronoUnit.SECONDS).minusSeconds(-3);
        check(d.getSeconds() == 13, "10s - (-3s) should be 13s, got " + d.getSeconds());
    }

    static void testNanosPreserved() {
        Duration d = Duration.of(5, ChronoUnit.NANOS).minusSeconds(2);
        check(d.getSeconds() == -2, "seconds should be -2, got " + d.getSeconds());
        check(d.getNano() == 5, "nanos should be preserved as 5, got " + d.getNano());
    }

    static void testMinValueBoundary() {
        Duration d = Duration.of(-1, ChronoUnit.SECONDS).minusSeconds(Long.MIN_VALUE);
        check(d.getSeconds() == Long.MAX_VALUE, "-1s - MIN_VALUE should be MAX_VALUE seconds, got " + d.getSeconds());
        check(d.getNano() == 0, "nanos should be 0 at boundary");
    }

    static void testMinValueOverflow() {
        boolean thrown = false;
        try {
            Duration.of(0, ChronoUnit.SECONDS).minusSeconds(Long.MIN_VALUE);
        } catch (ArithmeticException e) {
            thrown = true;
        }
        check(thrown, "0s - MIN_VALUE should overflow with ArithmeticException");
    }

    public static void main(String[] args) {
        testSimpleSubtraction();
        testSubtractZero();
        testSubtractNegative();
        testNanosPreserved();
        testMinValueBoundary();
        testMinValueOverflow();
        if (failures > 0) {
            throw new RuntimeException(failures + " check(s) failed");
        }
    }
}
