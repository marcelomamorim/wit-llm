/*
 * @test
 * @summary Test Duration.minusSeconds method for correct subtraction behavior
 * @run main DurationWitupTest
 */

import java.time.Duration;

public class DurationWitupTest {

    public static void main(String[] args) {
        testMinusSecondsNormal();
        testMinusSecondsEdgeMax();
        testMinusSecondsEdgeMin();
    }

    static void testMinusSecondsNormal() {
        Duration dur = Duration.ofSeconds(1000);
        Duration result = dur.minusSeconds(300);
        Duration expected = Duration.ofSeconds(700);
        if (!result.equals(expected)) {
            throw new AssertionError("Expected " + expected + " but got " + result);
        }
    }

    static void testMinusSecondsEdgeMax() {
        Duration dur = Duration.ofSeconds(Long.MAX_VALUE);
        Duration result = dur.minusSeconds(1);
        Duration expected = Duration.ofSeconds(Long.MAX_VALUE - 1);
        if (!result.equals(expected)) {
            throw new AssertionError("Expected " + expected + " but got " + result);
        }
    }

    static void testMinusSecondsEdgeMin() {
        Duration dur = Duration.ofSeconds(Long.MIN_VALUE);
        Duration result = dur.minusSeconds(1);
        Duration expected = Duration.ofSeconds(Long.MIN_VALUE + 1);
        if (!result.equals(expected)) {
            throw new AssertionError("Expected " + expected + " but got " + result);
        }
    }
}
