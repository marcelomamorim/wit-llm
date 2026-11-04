/*
 * @test
 * @summary Witup-derived checks for Duration.plusNanos(long) normal and overflow behavior
 * @run main DurationWitupTest
 */

import java.time.Duration;
import java.time.temporal.ChronoUnit;

public class DurationWitupTest {

    public static void main(String[] args) {
        testZeroPlusZeroNanos();
        testPlusNanosOneSecond();
        testPlusNanosNegativeBorrowsSecond();
        testOverflowThrowsArithmeticException();
    }

    static void testZeroPlusZeroNanos() {
        Duration zero = Duration.of(0, ChronoUnit.SECONDS);
        Duration result = zero.plusNanos(0L);
        check(result.equals(zero), "zero.plusNanos(0) should equal zero");
    }

    static void testPlusNanosOneSecond() {
        Duration zero = Duration.of(0, ChronoUnit.SECONDS);
        Duration result = zero.plusNanos(1_000_000_000L);
        check(result.equals(Duration.of(1, ChronoUnit.SECONDS)),
              "zero.plusNanos(1e9) should equal 1 second");
    }

    static void testPlusNanosNegativeBorrowsSecond() {
        Duration oneSecond = Duration.of(1, ChronoUnit.SECONDS);
        Duration result = oneSecond.plusNanos(-1L);
        check(result.equals(Duration.of(999_999_999L, ChronoUnit.NANOS)),
              "1s.plusNanos(-1) should equal 999999999 nanos");
    }

    static void testOverflowThrowsArithmeticException() {
        Duration max = Duration.of(Long.MAX_VALUE, ChronoUnit.SECONDS);
        boolean thrown = false;
        try {
            max.plusNanos(1_000_000_000L);
        } catch (ArithmeticException e) {
            thrown = true;
        }
        check(thrown, "Long.MAX_VALUE seconds plusNanos(1e9) should throw ArithmeticException");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
