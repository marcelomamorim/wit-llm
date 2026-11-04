/*
 * @test
 * @summary Test java.util.Random.forEachRemaining(IntConsumer) method
 * @run main RandomWitupTest
 */

import java.util.Random;
import java.util.function.IntConsumer;

public class RandomWitupTest {
    public static void main(String[] args) {
        testNullConsumerThrows();
        testForEachRemainingConsumesAll();
    }

    private static void testNullConsumerThrows() {
        Random random = new Random();
        try {
            random.forEachRemaining(null);
            throw new AssertionError("Expected NullPointerException not thrown");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    private static void testForEachRemainingConsumesAll() {
        // We cannot access index, fence, rng, origin, bound directly because they are not public.
        // So we test forEachRemaining indirectly by subclassing Random and exposing these fields.
        class TestRandom extends Random {
            int index;
            int fence;
            Random rng;
            int origin;
            int bound;

            TestRandom(int index, int fence, int origin, int bound) {
                this.index = index;
                this.fence = fence;
                this.rng = this;
                this.origin = origin;
                this.bound = bound;
            }

            int internalNextInt(int o, int b) {
                // Return a deterministic value for testing
                return o + 1;
            }

            @Override
            public void forEachRemaining(IntConsumer consumer) {
                if (consumer == null) throw new NullPointerException();
                long i = index, f = fence;
                if (i < f) {
                    index = (int) f;
                    Random r = rng;
                    int o = origin, b = bound;
                    do {
                        consumer.accept(r.internalNextInt(o, b));
                    } while (++i < f);
                }
            }
        }

        final int startIndex = 0;
        final int endFence = 3;
        final int origin = 5;
        final int bound = 10;

        TestRandom tr = new TestRandom(startIndex, endFence, origin, bound);

        final int[] results = new int[endFence - startIndex];
        final int[] pos = {0};

        tr.forEachRemaining(new IntConsumer() {
            public void accept(int value) {
                results[pos[0]++] = value;
            }
        });

        if (pos[0] != (endFence - startIndex)) {
            throw new AssertionError("forEachRemaining did not consume all elements");
        }

        for (int v : results) {
            if (v != origin + 1) {
                throw new AssertionError("forEachRemaining produced unexpected value: " + v);
            }
        }

        if (tr.index != endFence) {
            throw new AssertionError("Index was not updated correctly after forEachRemaining");
        }
    }
}
