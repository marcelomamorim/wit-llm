/*
 * @test
 * @summary Test java.time.Duration.plusSeconds(long) for normal and overflow cases
 * @run main DurationWitupTest
 */

import java.time.Duration;

public class DurationWitupTest {
    public static void main(String[] args) {
        testPlusSecondsNormal();
        testPlusSecondsOverflow();
    }

    private static void testPlusSecondsNormal() {
        Duration d = Duration.ofSeconds(10, 0);
        Duration result = d.plusSeconds(5);
        if (result.getSeconds() != 15 || result.getNano() != 0) {
            throw new AssertionError("Expected 15 seconds, got " + result);
        }
        // Adding zero seconds should return equal duration
        Duration same = d.plusSeconds(0);
        if (!same.equals(d)) {
            throw new AssertionError("Adding zero seconds should return equal duration");
        }
    }

    private static void testPlusSecondsOverflow() {
        // Test overflow behavior: adding seconds that cause overflow in plus
        // The plus method internally uses Math.addExact which throws ArithmeticException on overflow
        Duration max = Duration.ofSeconds(Long.MAX_VALUE, 0);
        try {
            max.plusSeconds(1);
            throw new AssertionError("Expected ArithmeticException due to overflow not thrown");
        } catch (ArithmeticException expected) {
            // Expected exception
        }

        Duration min = Duration.ofSeconds(Long.MIN_VALUE, 0);
        try {
            min.plusSeconds(-1);
            throw new AssertionError("Expected ArithmeticException due to underflow not thrown");
        } catch (ArithmeticException expected) {
            // Expected exception
        }
    }
}
