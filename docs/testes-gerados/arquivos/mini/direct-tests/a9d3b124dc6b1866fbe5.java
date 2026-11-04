/*
 * @test
 * @summary Test forEachRemaining method in Collections inner class
 * @run main CollectionsWitupTest
 */

import java.util.Objects;
import java.util.function.Consumer;

public class CollectionsWitupTest {

    static class TestForEachRemaining<E> {
        public void forEachRemaining(Consumer<? super E> action) {
            Objects.requireNonNull(action);
        }
    }

    public static void main(String[] args) {
        TestForEachRemaining<String> test = new TestForEachRemaining<>();

        // Test with a valid Consumer - should not throw
        Consumer<String> consumer = new Consumer<String>() {
            public void accept(String s) {
                // no-op
            }
        };
        test.forEachRemaining(consumer);

        // Test with null Consumer - should throw NullPointerException
        boolean npeThrown = false;
        try {
            test.forEachRemaining(null);
        } catch (NullPointerException e) {
            npeThrown = true;
        }
        if (!npeThrown) {
            throw new AssertionError("Expected NullPointerException when passing null to forEachRemaining");
        }
    }
}
