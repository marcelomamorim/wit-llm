/* @test    @summary Test Random.tryAdvance with non-null DoubleConsumer    @run main RandomWitupTest */

import java.util.Random;
import java.util.function.DoubleConsumer;

public class RandomWitupTest {

    public static void main(String[] args) {
        testTryAdvanceWithValidConsumer();
        testTryAdvanceWithNullConsumer();
        testTryAdvanceWhenIndexReachesFence();
    }

    static void testTryAdvanceWithValidConsumer() {
        Random rand = new Random(12345L);
        DoubleConsumer consumer = new DoubleConsumer() {
            int count = 0;
            double lastValue = 0.0;
            @Override
            public void accept(double value) {
                lastValue = value;
                count++;
            }
        };
        boolean result = rand.tryAdvance(consumer);
        assert result : "Expected tryAdvance to return true when index < fence";
    }

    static void testTryAdvanceWithNullConsumer() {
        Random rand = new Random(12345L);
        try {
            rand.tryAdvance(null);
            assert false : "Expected NullPointerException when consumer is null";
        } catch (NullPointerException e) {
            // expected
        }
    }

    static void testTryAdvanceWhenIndexReachesFence() {
        Random rand = new Random(12345L);
        // Manually set index to fence to simulate index >= fence
        // Since index and fence are private, we cannot set them directly.
        // Instead, we can invoke tryAdvance until it returns false.
        DoubleConsumer consumer = new DoubleConsumer() {
            @Override
            public void accept(double value) {
                // do nothing
            }
        };
        // Loop until tryAdvance returns false
        boolean result;
        do {
            result = rand.tryAdvance(consumer);
        } while (result);
        // Now, tryAdvance should return false
        boolean finalResult = rand.tryAdvance(consumer);
        assert !finalResult : "Expected tryAdvance to return false when index >= fence";
    }
}
