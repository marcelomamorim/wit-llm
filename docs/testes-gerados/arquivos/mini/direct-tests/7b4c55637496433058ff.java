/*
 * @test
 * @summary Test java.util.Spliterators.forEachRemaining(IntConsumer) method
 * @run main SpliteratorsWitupTest
 */

import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.IntConsumer;

public class SpliteratorsWitupTest {

    static class TestSpliterator extends Spliterators.AbstractIntSpliterator {
        private final int[] data;
        private int index = 0;

        TestSpliterator(int[] data) {
            super(data.length, Spliterator.ORDERED | Spliterator.SIZED | Spliterator.SUBSIZED);
            this.data = data;
        }

        @Override
        public boolean tryAdvance(java.util.function.IntConsumer action) {
            if (index < data.length) {
                action.accept(data[index++]);
                return true;
            }
            return false;
        }
    }

    public static void main(String[] args) {
        testForEachRemainingNormal();
        testForEachRemainingNullAction();
    }

    private static void testForEachRemainingNormal() {
        final int[] input = {1, 2, 3};
        final StringBuilder sb = new StringBuilder();
        TestSpliterator spliterator = new TestSpliterator(input);

        Spliterators.ForEachRemainingIntConsumer forEachRemainingWrapper = new Spliterators.ForEachRemainingIntConsumer(spliterator);

        IntConsumer action = new IntConsumer() {
            public void accept(int value) {
                sb.append(value);
            }
        };

        forEachRemainingWrapper.forEachRemaining(action);

        // After forEachRemaining, all elements should be consumed
        if (!sb.toString().equals("123")) {
            throw new AssertionError("Expected concatenated string '123' but got '" + sb.toString() + "'");
        }

        // tryAdvance should now return false
        if (spliterator.tryAdvance(action)) {
            throw new AssertionError("Expected tryAdvance to return false after forEachRemaining");
        }
    }

    private static void testForEachRemainingNullAction() {
        TestSpliterator spliterator = new TestSpliterator(new int[] {1});
        Spliterators.ForEachRemainingIntConsumer forEachRemainingWrapper = new Spliterators.ForEachRemainingIntConsumer(spliterator);

        try {
            forEachRemainingWrapper.forEachRemaining(null);
            throw new AssertionError("Expected NullPointerException when action is null");
        } catch (NullPointerException expected) {
            // Expected exception
        }
    }

    // Helper class to expose the forEachRemaining method from the provided source
    static class ForEachRemainingIntConsumer {
        private final Spliterator.OfInt it;

        ForEachRemainingIntConsumer(Spliterator.OfInt it) {
            this.it = it;
        }

        public void forEachRemaining(IntConsumer action) {
            if (action == null) throw new NullPointerException();
            it.forEachRemaining(action);
        }
    }
}
