/*
 * @test
 * @summary Witup-style tests for Duration.plusSeconds observable behavior
 * @run main DurationWitupTest
 */

import java.time.Duration;

public class DurationWitupTest {

    public static void main(String[] args) {
        check(Duration.ZERO.plusSeconds(5).equals(Duration.ofSeconds(5)), "ZERO plus 5s");
        check(Duration.ofSeconds(10).plusSeconds(-3).equals(Duration.ofSeconds(7)), "10s plus -3s");
        check(Duration.ofSeconds(1, 500_000_000L).plusSeconds(2)
                .equals(Duration.ofSeconds(3, 500_000_000L)), "1.5s plus 2s");
        check(Duration.ofSeconds(-5).plusSeconds(5).equals(Duration.ZERO), "-5s plus 5s is zero");
        check(Duration.ofSeconds(42).plusSeconds(0).equals(Duration.ofSeconds(42)), "plus 0s is identity");
        check(Duration.ofSeconds(-1, 999_999_999L).plusSeconds(1)
                .equals(Duration.ofNanos(999_999_999L)), "negative fractional plus 1s");
        check(Duration.ofSeconds(Long.MAX_VALUE - 1).plusSeconds(1)
                .equals(Duration.ofSeconds(Long.MAX_VALUE)), "max boundary without overflow");
        check(Duration.ofSeconds(3).plusSeconds(1).getSeconds() == 4L, "getSeconds after plusSeconds");

        expectArithmetic(new Runnable() {
            public void run() {
                Duration.ofSeconds(Long.MAX_VALUE).plusSeconds(1);
            }
        }, "overflow at Long.MAX_VALUE");

        expectArithmetic(new Runnable() {
            public void run() {
                Duration.ofSeconds(Long.MIN_VALUE).plusSeconds(-1);
            }
        }, "underflow at Long.MIN_VALUE");
    }

    private static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException("Failed: " + message);
        }
    }

    private static void expectArithmetic(Runnable action, String message) {
        try {
            action.run();
        } catch (ArithmeticException expected) {
            return;
        }
        throw new RuntimeException("Expected ArithmeticException: " + message);
    }
}
