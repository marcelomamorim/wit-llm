/*
 * @test
 * @summary WITUP-derived behavioral tests for Duration.toNanos overflow and boundary handling
 * @run main DurationWitupTest
 */

import java.time.Duration;
import java.time.temporal.ChronoUnit;

public class DurationWitupTest {

    public static void main(String[] args) {
        check(Duration.of(0, ChronoUnit.NANOS).toNanos() == 0L, "zero duration");
        check(Duration.of(1, ChronoUnit.SECONDS).toNanos() == 1000000000L, "one second");
        check(Duration.of(-1, ChronoUnit.SECONDS).toNanos() == -1000000000L, "negative one second");
        check(Duration.of(1500, ChronoUnit.NANOS).toNanos() == 1500L, "nanos only");
        check(Duration.of(-1500, ChronoUnit.NANOS).toNanos() == -1500L, "negative nanos only");

        check(Duration.of(Long.MAX_VALUE, ChronoUnit.NANOS).toNanos() == Long.MAX_VALUE,
              "Long.MAX_VALUE nanos boundary must fit");
        check(Duration.of(Long.MIN_VALUE, ChronoUnit.NANOS).toNanos() == Long.MIN_VALUE,
              "Long.MIN_VALUE nanos boundary must fit");

        assertArithmetic(Duration.of(Long.MAX_VALUE, ChronoUnit.SECONDS),
                         "seconds multiplication overflow must throw ArithmeticException");
        assertArithmetic(Duration.of(Long.MIN_VALUE, ChronoUnit.SECONDS),
                         "negative seconds multiplication overflow must throw ArithmeticException");
        assertArithmetic(Duration.of(9223372036L * 1000000000L + 999999999L, ChronoUnit.NANOS),
                         "addition of nanos overflow must throw ArithmeticException");
        assertArithmetic(Duration.of(-9223372037L, ChronoUnit.SECONDS),
                         "negative branch addition overflow must throw ArithmeticException");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void assertArithmetic(Duration d, String message) {
        try {
            d.toNanos();
        } catch (ArithmeticException e) {
            return;
        }
        throw new RuntimeException(message);
    }
}
