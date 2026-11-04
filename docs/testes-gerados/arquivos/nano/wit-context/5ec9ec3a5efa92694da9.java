/* @test    @summary Test for Random.tryAdvance with null consumer to verify NullPointerException is thrown    @run main RandomWitupTest */

import java.util.Random;
import java.util.function.IntConsumer;

public class RandomWitupTest {

    public static void main(String[] args) {
        testTryAdvanceNullConsumer();
        testTryAdvanceValidConsumer();
    }

    static void testTryAdvanceNullConsumer() {
        Random rand = new Random();
        try {
            rand.tryAdvance(null);
            System.err.println("Expected NullPointerException was not thrown");
            System.exit(1);
        } catch (NullPointerException e) {
            // Expected exception
        }
    }

    static void testTryAdvanceValidConsumer() {
        Random rand = new Random(12345L);
        final int[] result = {0};
        IntConsumer consumer = new IntConsumer() {
            @Override
            public void accept(int value) {
                result[0] = value;
            }
        };
        boolean hasNext = rand.tryAdvance(consumer);
        if (!hasNext) {
            System.err.println("tryAdvance returned false unexpectedly");
            System.exit(1);
        }
        if (result[0] == 0) {
            System.err.println("Consumer did not accept any value");
            System.exit(1);
        }
    }
}
