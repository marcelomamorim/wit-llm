/*
 * @test
 * @summary Test java.util.Spliterators.tryAdvance(Consumer) for null action and normal iteration
 * @run main SpliteratorsWitupTest
 */

import java.util.Spliterators;
import java.util.function.Consumer;
import java.util.ArrayList;
import java.util.List;

public class SpliteratorsWitupTest {

    public static void main(String[] args) {
        testTryAdvanceNullAction();
        testTryAdvanceNormalIteration();
    }

    private static void testTryAdvanceNullAction() {
        // Create a Spliterators.IteratorSpliterator via a List and test tryAdvance(null) throws NPE
        List<String> list = new ArrayList<>();
        list.add("a");
        Spliterators.IteratorSpliterator<String> spliterator = new Spliterators.IteratorSpliterator<>(list, 0);

        try {
            spliterator.tryAdvance(null);
            throw new AssertionError("Expected NullPointerException for null action");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    private static void testTryAdvanceNormalIteration() {
        List<String> list = new ArrayList<>();
        list.add("one");
        list.add("two");
        Spliterators.IteratorSpliterator<String> spliterator = new Spliterators.IteratorSpliterator<>(list, 0);

        final StringBuilder sb = new StringBuilder();
        Consumer<String> consumer = new Consumer<String>() {
            public void accept(String s) {
                sb.append(s);
            }
        };

        boolean advanced1 = spliterator.tryAdvance(consumer);
        if (!advanced1) {
            throw new AssertionError("Expected tryAdvance to return true on first element");
        }
        if (!"one".equals(sb.toString())) {
            throw new AssertionError("Expected consumer to receive 'one', got: " + sb.toString());
        }

        boolean advanced2 = spliterator.tryAdvance(consumer);
        if (!advanced2) {
            throw new AssertionError("Expected tryAdvance to return true on second element");
        }
        if (!"onetwo".equals(sb.toString())) {
            throw new AssertionError("Expected consumer to receive 'two' appended, got: " + sb.toString());
        }

        boolean advanced3 = spliterator.tryAdvance(consumer);
        if (advanced3) {
            throw new AssertionError("Expected tryAdvance to return false after all elements consumed");
        }
    }
}
