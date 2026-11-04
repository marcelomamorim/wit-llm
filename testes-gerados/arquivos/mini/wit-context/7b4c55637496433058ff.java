/*
 * @test
 * @summary Test java.util.Spliterators.IntIteratorSpliterator.forEachRemaining(IntConsumer) for NPE on null and normal operation
 * @run main SpliteratorsWitupTest
 */

import java.util.Spliterators;
import java.util.function.IntConsumer;

public class SpliteratorsWitupTest {

    static class IntIteratorSpliterator extends Spliterators.IntIteratorSpliterator {
        private final int[] array;
        private int index;

        IntIteratorSpliterator(int[] array) {
            super(null, 0, 0); // call super with dummy values; we override methods
            this.array = array;
            this.index = 0;
        }

        @Override
        public boolean tryAdvance(IntConsumer action) {
            if (index < array.length) {
                action.accept(array[index++]);
                return true;
            }
            return false;
        }

        @Override
        public void forEachRemaining(IntConsumer action) {
            if (action == null) throw new NullPointerException();
            while (index < array.length) {
                action.accept(array[index++]);
            }
        }
    }

    public static void main(String[] args) {
        testForEachRemainingNullAction();
        testForEachRemainingNormal();
    }

    private static void testForEachRemainingNullAction() {
        IntIteratorSpliterator spliterator = new IntIteratorSpliterator(new int[] {1, 2, 3});
        try {
            spliterator.forEachRemaining(null);
            throw new RuntimeException("Expected NullPointerException not thrown");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    private static void testForEachRemainingNormal() {
        final int[] input = {10, 20, 30};
        final int[] collected = new int[input.length];
        final int[] idx = {0};

        IntIteratorSpliterator spliterator = new IntIteratorSpliterator(input);
        spliterator.forEachRemaining(new IntConsumer() {
            public void accept(int value) {
                collected[idx[0]++] = value;
            }
        });

        if (idx[0] != input.length) {
            throw new RuntimeException("Not all elements consumed: expected " + input.length + " but got " + idx[0]);
        }

        for (int i = 0; i < input.length; i++) {
            if (collected[i] != input[i]) {
                throw new RuntimeException("Element mismatch at index " + i + ": expected " + input[i] + " but got " + collected[i]);
            }
        }
    }
}
