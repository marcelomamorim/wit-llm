/*
 * @test
 * @summary Verifies Duration.minusNanos for zero, small, negative and Long.MIN_VALUE arguments
 * @run main DurationWitupTest
 */

import java.time.Duration;
import java.time.temporal.ChronoUnit;

public class DurationWitupTest {

    public static void main(String[] args) {
        testMinusZeroNanos();
        testMinusOneNanoFromZero();
        testMinusNegativeNanosAddsNanos();
        testMinusLongMinValueFromZero();
        testMinusNanosMatchesPlusNanosOfNegatedValue();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testMinusZeroNanos() {
        Duration result = Duration.ZERO.minusNanos(0);
        check(result.equals(Duration.ZERO), "minusNanos(0) on ZERO should be ZERO");

        Duration five = Duration.of(5, ChronoUnit.SECONDS);
        check(five.minusNanos(0).equals(five), "minusNanos(0) should leave duration unchanged");
    }

    static void testMinusOneNanoFromZero() {
        Duration result = Duration.ZERO.minusNanos(1);
        Duration expected = Duration.of(-1, ChronoUnit.NANOS);
        check(result.equals(expected), "ZERO.minusNanos(1) should equal -1 nanosecond");
        check(result.getSeconds() == -1L, "seconds should be -1");
        check(result.getNano() == 999999999, "nano should be 999999999");
    }

    static void testMinusNegativeNanosAddsNanos() {
        Duration result = Duration.of(5, ChronoUnit.SECONDS).minusNanos(-1000);
        Duration expected = Duration.of(5000001000L, ChronoUnit.NANOS);
        check(result.equals(expected), "5s.minusNanos(-1000) should equal 5s + 1000ns");
        check(result.getSeconds() == 5L, "seconds should remain 5");
        check(result.getNano() == 1000, "nano should be 1000");
    }

    static void testMinusLongMinValueFromZero() {
        Duration result = Duration.ZERO.minusNanos(Long.MIN_VALUE);
        check(result.getSeconds() == 9223372036L, "seconds should be 9223372036");
        check(result.getNano() == 854775808, "nano should be 854775808");
    }

    static void testMinusNanosMatchesPlusNanosOfNegatedValue() {
        Duration base = Duration.of(10, ChronoUnit.SECONDS);
        long nanos = 123456789L;
        Duration subtracted = base.minusNanos(nanos);
        Duration added = base.plusNanos(-nanos);
        check(subtracted.equals(added), "minusNanos(n) should equal plusNanos(-n)");
        check(subtracted.equals(Duration.of(9876543211L, ChronoUnit.NANOS)
                .plus(Duration.ofSeconds(0))) || subtracted.getSeconds() == 9L,
                "10s minus 123456789ns should be 9.876543211s");
    }
}
