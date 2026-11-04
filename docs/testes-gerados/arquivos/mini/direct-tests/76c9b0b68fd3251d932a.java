/*
 * @test
 * @summary Test ModuleHashesBuilder.reverse method with Consumer action
 * @run main ModuleHashesBuilderWitupTest
 */

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.function.Consumer;

public class ModuleHashesBuilderWitupTest {

    public static void main(String[] args) {
        testReverseWithConsumer();
    }

    private static void testReverseWithConsumer() {
        // Create a Deque with known elements
        Deque<String> deque = new ArrayDeque<>();
        deque.add("one");
        deque.add("two");
        deque.add("three");

        // Create an instance of the tested class with the deque as 'result'
        ModuleHashesBuilderWitup<String> builder = new ModuleHashesBuilderWitup<>(deque);

        // Collect elements passed to the Consumer in a StringBuilder
        final StringBuilder sb = new StringBuilder();
        Consumer<String> consumer = new Consumer<String>() {
            @Override
            public void accept(String s) {
                sb.append(s).append(",");
            }
        };

        // Call reverse method
        builder.reverse(consumer);

        // The expected order is reverse of insertion: three,two,one,
        String expected = "three,two,one,";
        if (!sb.toString().equals(expected)) {
            throw new AssertionError("Expected: " + expected + ", but got: " + sb.toString());
        }
    }

    // Minimal subclass to expose the reverse method with a Deque field
    private static class ModuleHashesBuilderWitup<T> {
        private final Deque<T> result;

        ModuleHashesBuilderWitup(Deque<T> result) {
            this.result = result;
        }

        public void reverse(Consumer<T> action) {
            result.descendingIterator().forEachRemaining(action);
        }
    }
}
