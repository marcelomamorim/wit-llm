/* @test    @summary Test Random.tryAdvance with non-null consumer and null consumer    @run main RandomWitupTest */

import java.util.Random;
import java.util.function.IntConsumer;

public class RandomWitupTest {

    public static void main(String[] args) {
        testTryAdvanceWithValidConsumer();
        testTryAdvanceWithNullConsumer();
    }

    static void testTryAdvanceWithValidConsumer() {
        Random rand = new Random(12345L);
        final boolean[] called = {false};
        IntConsumer consumer = new IntConsumer() {
            @Override
            public void accept(int value) {
                called[0] = true;
                // Optionally, check the value if needed
            }
        };
        boolean result = rand.tryAdvance(consumer);
        assert result : "tryAdvance should return true when consumer is accepted";
        assert called[0] : "Consumer should be called";
    }

    static void testTryAdvanceWithNullConsumer() {
        Random rand = new Random(12345L);
        try {
            rand.tryAdvance(null);
            assert false : "tryAdvance with null consumer should throw NullPointerException";
        } catch (NullPointerException e) {
            // Expected exception
        }
    }
}
