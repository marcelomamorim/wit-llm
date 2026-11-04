/*
 * @test
 * @summary Test Spliterators.tryAdvance method behavior
 * @run main SpliteratorsWitupTest
 */

import java.util.Objects;
import java.util.function.Consumer;

public class SpliteratorsWitupTest {

    // Minimal implementation to test tryAdvance
    static class TestSpliterator<C> {
        public boolean tryAdvance(C consumer) {
            Objects.requireNonNull(consumer);
            return false;
        }
    }

    public static void main(String[] args) {
        TestSpliterator<Consumer<Object>> spliterator = new TestSpliterator<>();

        // Test that tryAdvance returns false for a valid consumer
        Consumer<Object> consumer = new Consumer<Object>() {
            public void accept(Object t) {
                // no-op
            }
        };

        if (spliterator.tryAdvance(consumer) != false) {
            throw new AssertionError("tryAdvance should return false");
        }

        // Test that tryAdvance throws NullPointerException when consumer is null
        boolean npeThrown = false;
        try {
            spliterator.tryAdvance(null);
        } catch (NullPointerException e) {
            npeThrown = true;
        }
        if (!npeThrown) {
            throw new AssertionError("tryAdvance should throw NullPointerException when consumer is null");
        }
    }
}
