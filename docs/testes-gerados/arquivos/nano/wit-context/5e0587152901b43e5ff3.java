/*
 * @test
 * @summary Teste do método Duration.minusSeconds(long)
 * @run main DurationWitupTest
 */

import java.time.Duration;

public class DurationWitupTest {

    public static void main(String[] args) {
        testMinusSecondsNormal();
        testMinusSecondsLongMin();
        testMinusSecondsArithmeticException();
    }

    static void testMinusSecondsNormal() {
        Duration d = Duration.ofSeconds(1000);
        Duration result = d.minusSeconds(10);
        if (!result.equals(Duration.ofSeconds(990))) {
            throw new AssertionError("Expected 990 seconds, got " + result.getSeconds());
        }
    }

    static void testMinusSecondsLongMin() {
        Duration d = Duration.ofSeconds(1000);
        Duration result = d.minusSeconds(Long.MIN_VALUE);
        Duration expected = Duration.ofSeconds(Long.MAX_VALUE).plusSeconds(1);
        if (!result.equals(expected)) {
            throw new AssertionError("Expected Long.MAX_VALUE + 1 seconds, got " + result.getSeconds());
        }
    }

    static void testMinusSecondsArithmeticException() {
        Duration d = Duration.ofSeconds(Long.MAX_VALUE);
        try {
            d.minusSeconds(Long.MAX_VALUE);
            throw new AssertionError("Expected ArithmeticException not thrown");
        } catch (ArithmeticException e) {
            // Expected exception
        }
    }
}
