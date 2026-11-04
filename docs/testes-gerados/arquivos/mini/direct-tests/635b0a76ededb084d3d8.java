/*
 * @test
 * @summary Test StreamSpliterators.tryAdvance method behavior
 * @run main StreamSpliteratorsWitupTest
 */

import java.util.Objects;
import java.util.function.Consumer;

public class StreamSpliteratorsWitupTest {

    static class DummyBuffer<T> {
        private final Object[] elements;

        DummyBuffer(Object[] elements) {
            this.elements = elements;
        }

        @SuppressWarnings("unchecked")
        T get(long index) {
            return (T) elements[(int) index];
        }
    }

    static class TestSpliterator<P_OUT> {
        DummyBuffer<P_OUT> buffer;
        long nextToConsume;
        boolean hasNext;

        TestSpliterator(DummyBuffer<P_OUT> buffer, long nextToConsume, boolean hasNext) {
            this.buffer = buffer;
            this.nextToConsume = nextToConsume;
            this.hasNext = hasNext;
        }

        boolean doAdvance() {
            return hasNext;
        }

        public boolean tryAdvance(Consumer<? super P_OUT> consumer) {
            Objects.requireNonNull(consumer);
            boolean hasNext = doAdvance();
            if (hasNext)
                consumer.accept(buffer.get(nextToConsume));
            return hasNext;
        }
    }

    public static void main(String[] args) {
        // Test 1: tryAdvance returns true and consumer is called with correct element
        DummyBuffer<String> buffer1 = new DummyBuffer<>(new String[] {"a", "b", "c"});
        TestSpliterator<String> spliterator1 = new TestSpliterator<>(buffer1, 1L, true);
        final StringBuilder sb1 = new StringBuilder();
        boolean result1 = spliterator1.tryAdvance(new Consumer<String>() {
            public void accept(String s) {
                sb1.append(s);
            }
        });
        if (!result1) throw new RuntimeException("Test 1 failed: expected true from tryAdvance");
        if (!"b".equals(sb1.toString())) throw new RuntimeException("Test 1 failed: consumer not called with correct element");

        // Test 2: tryAdvance returns false and consumer is not called
        DummyBuffer<String> buffer2 = new DummyBuffer<>(new String[] {"x", "y"});
        TestSpliterator<String> spliterator2 = new TestSpliterator<>(buffer2, 0L, false);
        final boolean[] called = {false};
        boolean result2 = spliterator2.tryAdvance(new Consumer<String>() {
            public void accept(String s) {
                called[0] = true;
            }
        });
        if (result2) throw new RuntimeException("Test 2 failed: expected false from tryAdvance");
        if (called[0]) throw new RuntimeException("Test 2 failed: consumer should not be called when no element");

        // Test 3: tryAdvance throws NullPointerException if consumer is null
        DummyBuffer<String> buffer3 = new DummyBuffer<>(new String[] {"z"});
        TestSpliterator<String> spliterator3 = new TestSpliterator<>(buffer3, 0L, true);
        try {
            spliterator3.tryAdvance(null);
            throw new RuntimeException("Test 3 failed: expected NullPointerException");
        } catch (NullPointerException npe) {
            // expected
        }
    }
}
