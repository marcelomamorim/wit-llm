/*
 * @test
 * @summary Witup-derived tests for Duration.plusSeconds overflow and basic observable behaviour
 * @run main DurationWitupTest
 */
import java.time.Duration;

public class DurationWitupTest {

    public static void main(String[] args) {
        check(Duration.ofSeconds(10).plusSeconds(-3).getSeconds() == 7L, "10 plus -3 seconds");
        check(Duration.ofSeconds(5).plusSeconds(0).equals(Duration.ofSeconds(5)), "plus zero seconds");
        check(Duration.ZERO.plusSeconds(Long.MAX_VALUE).getSeconds() == Long.MAX_VALUE, "zero plus max seconds");
        check(Duration.ofSeconds(-1).plusSeconds(Long.MAX_VALUE).getSeconds() == Long.MAX_VALUE - 1L, "negative plus max seconds");
        expectArithmetic(() -> Duration.ofSeconds(Long.MAX_VALUE).plusSeconds(1), "positive overflow");
        expectArithmetic(() -> Duration.ofSeconds(Long.MIN_VALUE).plusSeconds(-1), "negative overflow");
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
