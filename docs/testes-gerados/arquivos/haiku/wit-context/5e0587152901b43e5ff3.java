/*
 * @test
 * @summary Witup-generated checks for Duration.minusSeconds(long) boundary and overflow behavior
 * @run main DurationWitupTest
 */

import java.time.Duration;
import java.time.temporal.ChronoUnit;

public class DurationWitupTest {

    public static void main(String[] args) {
        Duration d1 = Duration.of(5, ChronoUnit.SECONDS).minusSeconds(3);
        check(d1.getSeconds() == 2 && d1.getNano() == 0, "5s - 3s should be 2s");

        Duration d2 = Duration.of(-2, ChronoUnit.SECONDS).minusSeconds(Long.MIN_VALUE);
        check(d2.getSeconds() == Long.MAX_VALUE - 1 && d2.getNano() == 0,
              "-2s - Long.MIN_VALUE should be Long.MAX_VALUE - 1 seconds");

        Duration d3 = Duration.of(Long.MIN_VALUE, ChronoUnit.SECONDS).minusSeconds(Long.MIN_VALUE);
        check(d3.getSeconds() == 0 && d3.getNano() == 0,
              "Long.MIN_VALUE - Long.MIN_VALUE seconds should be zero");

        Duration d4 = Duration.of(1500, ChronoUnit.MILLIS).minusSeconds(3);
        check(d4.getSeconds() == -2 && d4.getNano() == 500000000,
              "1.5s - 3s should be -2s + 0.5s nanos");

        Duration d5 = Duration.of(7, ChronoUnit.SECONDS).minusSeconds(0);
        check(d5.getSeconds() == 7 && d5.getNano() == 0, "subtracting zero should keep value");

        expectArithmetic(Duration.ZERO, Long.MIN_VALUE);
        expectArithmetic(Duration.of(Long.MIN_VALUE, ChronoUnit.SECONDS), 1L);
        expectArithmetic(Duration.of(Long.MAX_VALUE, ChronoUnit.SECONDS), -1L);
    }

    private static void expectArithmetic(Duration d, long secondsToSubtract) {
        try {
            d.minusSeconds(secondsToSubtract);
        } catch (ArithmeticException e) {
            return;
        }
        throw new RuntimeException("expected ArithmeticException for minusSeconds(" + secondsToSubtract + ")");
    }

    private static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
