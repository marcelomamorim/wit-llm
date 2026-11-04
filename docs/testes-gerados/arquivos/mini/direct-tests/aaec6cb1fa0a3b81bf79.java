/*
 * @test
 * @summary Test java.util.Spliterators.tryAdvance method behavior
 * @run main SpliteratorsWitupTest
 */

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.function.Consumer;

public class SpliteratorsWitupTest {

    static class TestSpliterator<T> {
        private final Collection<T> collection;
        private Iterator<T> it;
        private long est;

        TestSpliterator(Collection<T> c) {
            this.collection = c;
        }

        public boolean tryAdvance(Consumer<? super T> action) {
            if (action == null) throw new NullPointerException();
            if (it == null) {
                it = collection.iterator();
                est = (long) collection.size();
            }
            if (it.hasNext()) {
                action.accept(it.next());
                return true;
            }
            return false;
        }
    }

    public static void main(String[] args) {
        testNullActionThrows();
        testTryAdvanceReturnsTrueAndFalse();
    }

    private static void testNullActionThrows() {
        TestSpliterator<String> spliterator = new TestSpliterator<>(new ArrayList<String>());
        try {
            spliterator.tryAdvance(null);
            throw new RuntimeException("Expected NullPointerException not thrown");
        } catch (NullPointerException e) {
            // expected
        }
    }

    private static void testTryAdvanceReturnsTrueAndFalse() {
        ArrayList<String> list = new ArrayList<>();
        list.add("a");
        list.add("b");
        list.add("c");

        TestSpliterator<String> spliterator = new TestSpliterator<>(list);

        final StringBuilder sb = new StringBuilder();
        Consumer<String> consumer = new Consumer<String>() {
            public void accept(String s) {
                sb.append(s);
            }
        };

        boolean result1 = spliterator.tryAdvance(consumer);
        if (!result1) throw new RuntimeException("tryAdvance should return true when element available");
        if (!sb.toString().equals("a")) throw new RuntimeException("Consumer did not accept first element correctly");

        boolean result2 = spliterator.tryAdvance(consumer);
        if (!result2) throw new RuntimeException("tryAdvance should return true when element available");
        if (!sb.toString().equals("ab")) throw new RuntimeException("Consumer did not accept second element correctly");

        boolean result3 = spliterator.tryAdvance(consumer);
        if (!result3) throw new RuntimeException("tryAdvance should return true when element available");
        if (!sb.toString().equals("abc")) throw new RuntimeException("Consumer did not accept third element correctly");

        boolean result4 = spliterator.tryAdvance(consumer);
        if (result4) throw new RuntimeException("tryAdvance should return false when no elements available");
    }
}
