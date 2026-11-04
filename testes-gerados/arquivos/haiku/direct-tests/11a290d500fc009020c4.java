/*
 * @test
 * @summary Verify Duration.dividedBy truncation, sign handling, zero divisor, null divisor and overflow
 * @run main DurationWitupTest
 */

import java.time.Duration;

public class DurationWitupTest {

    public static void main(String[] args) {
        check(Duration.ofSeconds(10).dividedBy(Duration.ofSeconds(3)) == 3L, "10s / 3s");
        check(Duration.ofSeconds(-10).dividedBy(Duration.ofSeconds(3)) == -3L, "-10s / 3s truncates toward zero");
        check(Duration.ofSeconds(10).dividedBy(Duration.ofSeconds(-3)) == -3L, "10s / -3s");
        check(Duration.ofMillis(1500).dividedBy(Duration.ofMillis(500)) == 3L, "1500ms / 500ms");
        check(Duration.ofMillis(1000).dividedBy(Duration.ofMillis(1500)) == 0L, "1000ms / 1500ms truncates to 0");
        check(Duration.ZERO.dividedBy(Duration.ofSeconds(1)) == 0L, "0 / 1s");
        check(Duration.ofSeconds(7).dividedBy(Duration.ofSeconds(7)) == 1L, "7s / 7s");

        expectNpe();
        expectArithmetic(Duration.ofSeconds(1), Duration.ZERO, "division by zero");
        expectArithmetic(Duration.ofSeconds(Long.MAX_VALUE, 999999999L), Duration.ofNanos(1L), "quotient exceeds long");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException("Failed: " + message);
        }
    }

    static void expectNpe() {
        try {
            Duration.ofSeconds(1).dividedBy(null);
        } catch (NullPointerException e) {
            return;
        }
        throw new RuntimeException("Expected NullPointerException for null divisor");
    }

    static void expectArithmetic(Duration dividend, Duration divisor, String message) {
        try {
            dividend.dividedBy(divisor);
        } catch (ArithmeticException e) {
            return;
        }
        throw new RuntimeException("Expected ArithmeticException: " + message);
    }
}
