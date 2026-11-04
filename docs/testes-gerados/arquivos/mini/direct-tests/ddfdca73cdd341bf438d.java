/*
 * @test
 * @summary Test java.util.Spliterators.tryAdvance method behavior
 * @run main SpliteratorsWitupTest
 */

import java.util.function.Consumer;
import java.util.Spliterators;

public class SpliteratorsWitupTest {

    static class TestSpliterator<T> extends Spliterators.AbstractSpliterator<T> {
        final Object[] array;
        int index = 0;
        final int fence;

        TestSpliterator(Object[] array) {
            super(array.length, 0);
            this.array = array;
            this.fence = array.length;
        }

        public boolean tryAdvance(Consumer<? super T> action) {
            if (action == null)
                throw new NullPointerException();
            if (index >= 0 && index < fence) {
                @SuppressWarnings("unchecked") T e = (T) array[index++];
                action.accept(e);
                return true;
            }
            return false;
        }
    }

    public static void main(String[] args) {
        testTryAdvanceNormal();
        testTryAdvanceNullAction();
        testTryAdvanceExhaustion();
    }

    private static void testTryAdvanceNormal() {
        Object[] data = {"a", "b", "c"};
        TestSpliterator<String> spliterator = new TestSpliterator<>(data);
        final StringBuilder sb = new StringBuilder();

        Consumer<String> consumer = new Consumer<String>() {
            public void accept(String s) {
                sb.append(s);
            }
        };

        boolean result1 = spliterator.tryAdvance(consumer);
        if (!result1 || !sb.toString().equals("a")) {
            throw new AssertionError("tryAdvance failed first element");
        }

        boolean result2 = spliterator.tryAdvance(consumer);
        if (!result2 || !sb.toString().equals("ab")) {
            throw new AssertionError("tryAdvance failed second element");
        }

        boolean result3 = spliterator.tryAdvance(consumer);
        if (!result3 || !sb.toString().equals("abc")) {
            throw new AssertionError("tryAdvance failed third element");
        }
    }

    private static void testTryAdvanceNullAction() {
        Object[] data = {"x"};
        TestSpliterator<String> spliterator = new TestSpliterator<>(data);
        try {
            spliterator.tryAdvance(null);
            throw new AssertionError("Expected NullPointerException for null action");
        } catch (NullPointerException e) {
            // expected
        }
    }

    private static void testTryAdvanceExhaustion() {
        Object[] data = {"only"};
        TestSpliterator<String> spliterator = new TestSpliterator<>(data);
        Consumer<String> consumer = new Consumer<String>() {
            public void accept(String s) {}
        };

        boolean first = spliterator.tryAdvance(consumer);
        if (!first) {
            throw new AssertionError("tryAdvance should return true for first element");
        }

        boolean second = spliterator.tryAdvance(consumer);
        if (second) {
            throw new AssertionError("tryAdvance should return false after elements exhausted");
        }
    }
}
