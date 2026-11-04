/*
 * @test
 * @summary Test Spliterators.tryAdvance(Consumer) for null action and normal iteration
 * @run main SpliteratorsWitupTest
 */

import java.util.Spliterators;
import java.util.function.Consumer;

public class SpliteratorsWitupTest {

    public static void main(String[] args) {
        testTryAdvanceNullAction();
        testTryAdvanceNormal();
    }

    private static void testTryAdvanceNullAction() {
        Spliterators.ArraySpliterator<String> spliterator = new Spliterators.ArraySpliterator<>(
                new String[] {"a", "b"}, 0, 2);
        try {
            spliterator.tryAdvance(null);
            throw new Error("Expected NullPointerException for null action");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    private static void testTryAdvanceNormal() {
        String[] data = {"x", "y"};
        Spliterators.ArraySpliterator<String> spliterator = new Spliterators.ArraySpliterator<>(data, 0, data.length);
        final StringBuilder sb = new StringBuilder();
        Consumer<String> consumer = new Consumer<String>() {
            public void accept(String s) {
                sb.append(s);
            }
        };

        boolean advanced1 = spliterator.tryAdvance(consumer);
        if (!advanced1) {
            throw new Error("Expected tryAdvance to return true on first element");
        }
        if (!sb.toString().equals("x")) {
            throw new Error("Consumer did not receive first element correctly");
        }

        boolean advanced2 = spliterator.tryAdvance(consumer);
        if (!advanced2) {
            throw new Error("Expected tryAdvance to return true on second element");
        }
        if (!sb.toString().equals("xy")) {
            throw new Error("Consumer did not receive second element correctly");
        }

        boolean advanced3 = spliterator.tryAdvance(consumer);
        if (advanced3) {
            throw new Error("Expected tryAdvance to return false after all elements consumed");
        }
    }
}
