/*
 * @test
 * @summary Test StreamSpliterators.tryAdvance method behavior
 * @run main StreamSpliteratorsWitupTest
 */

import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class StreamSpliteratorsWitupTest {

    static class TestSpliterator<T> {
        private final Supplier<T> s;

        TestSpliterator(Supplier<T> s) {
            this.s = s;
        }

        public boolean tryAdvance(Consumer<? super T> action) {
            Objects.requireNonNull(action);
            action.accept(s.get());
            return true;
        }
    }

    public static void main(String[] args) {
        // Test normal tryAdvance with a simple supplier and consumer
        final StringBuilder sb = new StringBuilder();
        TestSpliterator<String> spliterator = new TestSpliterator<>(new Supplier<String>() {
            private int count = 0;
            public String get() {
                count++;
                return "value" + count;
            }
        });

        Consumer<String> consumer = new Consumer<String>() {
            public void accept(String s) {
                sb.append(s);
            }
        };

        boolean result = spliterator.tryAdvance(consumer);
        if (!result) {
            throw new RuntimeException("tryAdvance should return true");
        }
        if (!"value1".equals(sb.toString())) {
            throw new RuntimeException("Consumer did not receive expected value: " + sb.toString());
        }

        // Test that passing null to tryAdvance throws NullPointerException
        boolean npeThrown = false;
        try {
            spliterator.tryAdvance(null);
        } catch (NullPointerException e) {
            npeThrown = true;
        }
        if (!npeThrown) {
            throw new RuntimeException("tryAdvance should throw NullPointerException when action is null");
        }

        // Test multiple calls produce expected values
        sb.setLength(0);
        boolean r1 = spliterator.tryAdvance(consumer);
        boolean r2 = spliterator.tryAdvance(consumer);
        if (!r1 || !r2) {
            throw new RuntimeException("tryAdvance should always return true");
        }
        if (!"value2value3".equals(sb.toString())) {
            throw new RuntimeException("Consumer did not receive expected values on multiple calls: " + sb.toString());
        }
    }
}
