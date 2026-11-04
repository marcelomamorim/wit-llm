/*
 * @test
 * @summary Witup-derived checks for Duration.plusMillis(long): overflow and normal behaviour
 * @run main DurationWitupTest
 */

import java.time.Duration;

public class DurationWitupTest {

    public static void main(String[] args) {
        testOverflowThrowsArithmeticException();
        testPositiveMillisAddsCorrectly();
        testNegativeMillisSubtractsCorrectly();
        testZeroMillisReturnsEquivalentDuration();
    }

    static void testOverflowThrowsArithmeticException() {
        Duration max = Duration.ofSeconds(Long.MAX_VALUE);
        boolean thrown = false;
        try {
            max.plusMillis(1000);
        } catch (ArithmeticException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("expected ArithmeticException on seconds overflow");
        }
    }

    static void testPositiveMillisAddsCorrectly() {
        Duration result = Duration.ofSeconds(1).plusMillis(1500);
        Duration expected = Duration.ofMillis(2500);
        if (!expected.equals(result)) {
            throw new RuntimeException("unexpected result for positive millis: " + result);
        }
    }

    static void testNegativeMillisSubtractsCorrectly() {
        Duration result = Duration.ofSeconds(5).plusMillis(-1);
        if (result.toMillis() != 4999L) {
            throw new RuntimeException("unexpected result for negative millis: " + result);
        }
    }

    static void testZeroMillisReturnsEquivalentDuration() {
        Duration result = Duration.ofSeconds(3).plusMillis(0);
        if (!Duration.ofSeconds(3).equals(result)) {
            throw new RuntimeException("zero millis changed duration: " + result);
        }
    }
}
