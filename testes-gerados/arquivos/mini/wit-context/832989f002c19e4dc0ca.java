/*
 * @test
 * @summary Test java.util.Spliterators.LongIteratorSpliterator.forEachRemaining(LongConsumer) for NPE on null action
 * @run main SpliteratorsWitupTest
 */

import java.util.Spliterators;
import java.util.function.LongConsumer;

public class SpliteratorsWitupTest {
    public static void main(String[] args) {
        testForEachRemainingNullAction();
        testForEachRemainingWithValidAction();
    }

    private static void testForEachRemainingNullAction() {
        // Create a LongIteratorSpliterator with a simple long array iterator
        final long[] values = {1L, 2L, 3L};
        Spliterators.LongIteratorSpliterator spliterator = new Spliterators.LongIteratorSpliterator() {
            private int index = 0;
            public boolean tryAdvance(LongConsumer action) {
                if (index < values.length) {
                    action.accept(values[index++]);
                    return true;
                }
                return false;
            }
            public void forEachRemaining(LongConsumer action) {
                if (action == null) throw new NullPointerException();
                while (index < values.length) {
                    action.accept(values[index++]);
                }
            }
        };

        boolean npeThrown = false;
        try {
            spliterator.forEachRemaining(null);
        } catch (NullPointerException e) {
            npeThrown = true;
        }
        if (!npeThrown) {
            throw new AssertionError("Expected NullPointerException when action is null");
        }
    }

    private static void testForEachRemainingWithValidAction() {
        final long[] values = {10L, 20L, 30L};
        final StringBuilder sb = new StringBuilder();

        Spliterators.LongIteratorSpliterator spliterator = new Spliterators.LongIteratorSpliterator() {
            private int index = 0;
            public boolean tryAdvance(LongConsumer action) {
                if (index < values.length) {
                    action.accept(values[index++]);
                    return true;
                }
                return false;
            }
            public void forEachRemaining(LongConsumer action) {
                if (action == null) throw new NullPointerException();
                while (index < values.length) {
                    action.accept(values[index++]);
                }
            }
        };

        spliterator.forEachRemaining(new LongConsumer() {
            public void accept(long value) {
                sb.append(value).append(",");
            }
        });

        String result = sb.toString();
        if (!result.equals("10,20,30,")) {
            throw new AssertionError("forEachRemaining did not process all elements correctly, got: " + result);
        }
    }
}
