/*
 * @test
 * @summary Test java.util.Random.tryAdvance(DoubleConsumer) for correct behavior and NPE on null
 * @run main RandomWitupTest
 */

import java.util.Random;
import java.util.function.DoubleConsumer;

public class RandomWitupTest {
    public static void main(String[] args) {
        testTryAdvanceWithValidConsumer();
        testTryAdvanceWithNullConsumer();
    }

    private static void testTryAdvanceWithValidConsumer() {
        Random random = new Random(12345L);
        final double[] captured = new double[1];
        DoubleConsumer consumer = new DoubleConsumer() {
            public void accept(double value) {
                captured[0] = value;
            }
        };

        boolean result = random.tryAdvance(consumer);

        if (!result) {
            throw new AssertionError("tryAdvance should return true when elements remain");
        }
        // The captured value should be within [0.0, 1.0) since internalNextDouble returns a double in that range
        if (captured[0] < 0.0 || captured[0] >= 1.0) {
            throw new AssertionError("Consumed value " + captured[0] + " out of expected range [0.0, 1.0)");
        }
    }

    private static void testTryAdvanceWithNullConsumer() {
        Random random = new Random(12345L);
        try {
            random.tryAdvance(null);
            throw new AssertionError("tryAdvance(null) should throw NullPointerException");
        } catch (NullPointerException expected) {
            // Expected exception
        }
    }
}
