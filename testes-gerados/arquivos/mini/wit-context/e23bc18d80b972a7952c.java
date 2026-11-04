/*
 * @test
 * @summary Test Spliterators.tryAdvance(C consumer) for null consumer and normal behavior
 * @run main SpliteratorsWitupTest
 */

import java.util.Objects;
import java.util.Spliterators;
import java.util.function.Consumer;

public class SpliteratorsWitupTest {
    public static void main(String[] args) {
        testTryAdvanceWithNullConsumer();
        testTryAdvanceWithNonNullConsumer();
    }

    private static void testTryAdvanceWithNullConsumer() {
        Spliterators spliterators = new Spliterators() { };
        try {
            spliterators.tryAdvance(null);
            throw new RuntimeException("Expected NullPointerException not thrown");
        } catch (NullPointerException expected) {
            // Expected exception
        }
    }

    private static void testTryAdvanceWithNonNullConsumer() {
        Spliterators spliterators = new Spliterators() { };
        Consumer<Object> consumer = new Consumer<Object>() {
            public void accept(Object t) {
                throw new RuntimeException("Should not be called");
            }
        };
        boolean result = spliterators.tryAdvance(consumer);
        if (result != false) {
            throw new RuntimeException("Expected tryAdvance to return false");
        }
    }
}
