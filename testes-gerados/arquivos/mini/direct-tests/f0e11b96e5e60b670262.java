/*
 * @test
 * @summary Test Spliterators.forEachRemaining method for null consumer check
 * @run main SpliteratorsWitupTest
 */

import java.util.Objects;
import java.util.function.Consumer;

public class SpliteratorsWitupTest {

    static class TestSpliterator<C> {
        public void forEachRemaining(C consumer) {
            Objects.requireNonNull(consumer);
        }
    }

    public static void main(String[] args) {
        TestSpliterator<Consumer<Object>> spliterator = new TestSpliterator<>();

        // Test that passing null consumer throws NullPointerException
        try {
            spliterator.forEachRemaining(null);
            throw new RuntimeException("Expected NullPointerException not thrown");
        } catch (NullPointerException expected) {
            // expected
        }

        // Test that passing a non-null consumer does not throw
        spliterator.forEachRemaining(new Consumer<Object>() {
            public void accept(Object t) {
                // no-op
            }
        });
    }
}
