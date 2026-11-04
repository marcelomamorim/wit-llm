/*
 * @test
 * @summary Test java.util.Random.RandomIntsSpliterator.tryAdvance(IntConsumer) for NPE and normal behavior
 * @run main RandomWitupTest
 */

import java.util.Random;
import java.util.function.IntConsumer;

public class RandomWitupTest {
    public static void main(String[] args) {
        testTryAdvanceNullConsumer();
        testTryAdvanceNormalBehavior();
    }

    private static void testTryAdvanceNullConsumer() {
        Random random = new Random(12345L);
        java.util.Random.RandomIntsSpliterator spliterator = new Random.RandomIntsSpliterator(random, 0, 10, 0, 100);
        boolean npeThrown = false;
        try {
            spliterator.tryAdvance(null);
        } catch (NullPointerException e) {
            npeThrown = true;
        }
        if (!npeThrown) {
            throw new AssertionError("Expected NullPointerException when consumer is null");
        }
    }

    private static void testTryAdvanceNormalBehavior() {
        Random random = new Random(12345L);
        int origin = 0;
        int bound = 10;
        int start = 0;
        int end = 5;
        java.util.Random.RandomIntsSpliterator spliterator = new Random.RandomIntsSpliterator(random, start, end, origin, bound);

        final int[] results = new int[end - start];
        final int[] count = {0};
        IntConsumer consumer = new IntConsumer() {
            public void accept(int value) {
                results[count[0]++] = value;
            }
        };

        int calls = 0;
        while (spliterator.tryAdvance(consumer)) {
            calls++;
        }

        if (calls != (end - start)) {
            throw new AssertionError("tryAdvance should be called exactly " + (end - start) + " times, but was " + calls);
        }

        for (int i = 0; i < calls; i++) {
            int val = results[i];
            if (val < origin || val >= bound) {
                throw new AssertionError("Generated value " + val + " out of bounds [" + origin + ", " + bound + ")");
            }
        }
    }
}
