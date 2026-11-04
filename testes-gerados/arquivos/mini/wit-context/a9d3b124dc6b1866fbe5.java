/*
 * @test
 * @summary Test Collections.EmptyIterator.forEachRemaining throws NPE on null action
 * @run main CollectionsWitupTest
 */

import java.util.Collections;
import java.util.function.Consumer;
import java.util.Objects;

public class CollectionsWitupTest {
    public static void main(String[] args) {
        // Create an EmptyIterator instance from Collections
        // EmptyIterator is package-private, but we can get it via Collections.emptyIterator()
        java.util.Iterator<Object> emptyIt = Collections.emptyIterator();

        // Test that forEachRemaining throws NullPointerException when action is null
        try {
            // The EmptyIterator returned by Collections.emptyIterator() has forEachRemaining method
            emptyIt.forEachRemaining(null);
            throw new RuntimeException("Expected NullPointerException not thrown");
        } catch (NullPointerException expected) {
            // Expected exception
        }

        // Test that forEachRemaining with a non-null Consumer does not throw
        Consumer<Object> dummyConsumer = new Consumer<Object>() {
            public void accept(Object o) {
                // no-op
            }
        };
        emptyIt.forEachRemaining(dummyConsumer);

        // If reached here, test passed
        System.out.println("Test passed");
    }
}
