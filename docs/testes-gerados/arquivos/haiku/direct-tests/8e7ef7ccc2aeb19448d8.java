/*
 * @test
 * @summary Tests Duration.toNanos conversions and ArithmeticException on overflow
 * @run main DurationWitupTest
 */

import java.time.Duration;
import java.time.temporal.ChronoUnit;

public class DurationWitupTest {

    public static void main(String[] args) {
        check(Duration.ZERO.toNanos() == 0L, "zero");
        check(Duration.of(1, ChronoUnit.SECONDS).toNanos() == 1000000000L, "one second");
        check(Duration.of(-1, ChronoUnit.SECONDS).toNanos() == -1000000000L, "minus one second");
        check(Duration.of(1, ChronoUnit.NANOS).toNanos() == 1L, "one nano");
        check(Duration.of(-1, ChronoUnit.NANOS).toNanos() == -1L, "minus one nano");
        check(Duration.of(9223372036L, ChronoUnit.SECONDS).toNanos() == 9223372036000000000L, "max seconds that fit");
        check(Duration.of(-9223372036L, ChronoUnit.SECONDS).toNanos() == -9223372036000000000L, "min seconds that fit");
        check(Duration.of(Long.MIN_VALUE, ChronoUnit.NANOS).toNanos() == Long.MIN_VALUE, "Long.MIN_VALUE nanos");

        boolean thrown = false;
        try {
            Duration.of(Long.MAX_VALUE, ChronoUnit.SECONDS).toNanos();
        } catch (ArithmeticException e) {
            thrown = true;
        }
        check(thrown, "overflow for Long.MAX_VALUE seconds");

        thrown = false;
        try {
            Duration.of(Long.MIN_VALUE, ChronoUnit.SECONDS).toNanos();
        } catch (ArithmeticException e) {
            thrown = true;
        }
        check(thrown, "overflow for Long.MIN_VALUE seconds");
    }

    private static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException("Failed: " + message);
        }
    }
}
