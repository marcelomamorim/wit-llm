/*
 * @test
 * @summary Test java.util.Spliterators.next() method behavior
 * @run main SpliteratorsWitupTest
 */

import java.util.NoSuchElementException;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.Consumer;

public class SpliteratorsWitupTest {

    static class TestSpliterator<T> extends Spliterators.AbstractSpliterator<T> {
        private final T[] elements;
        private int index = 0;

        TestSpliterator(T[] elements) {
            super(elements.length, Spliterator.SIZED | Spliterator.SUBSIZED);
            this.elements = elements;
        }

        @Override
        public boolean tryAdvance(Consumer<? super T> action) {
            if (index < elements.length) {
                action.accept(elements[index++]);
                return true;
            }
            return false;
        }
    }

    public static void main(String[] args) {
        testNextReturnsElements();
        testNextThrowsWhenNoMoreElements();
    }

    private static void testNextReturnsElements() {
        String[] data = {"a", "b"};
        TestSpliterator<String> spliterator = new TestSpliterator<>(data);

        // Use Spliterators.iterator to get Iterator with next() method
        java.util.Iterator<String> it = Spliterators.iterator(spliterator);

        // First call to next() should return "a"
        String first = it.next();
        if (!"a".equals(first)) {
            throw new AssertionError("Expected first element 'a', got: " + first);
        }

        // Second call to next() should return "b"
        String second = it.next();
        if (!"b".equals(second)) {
            throw new AssertionError("Expected second element 'b', got: " + second);
        }
    }

    private static void testNextThrowsWhenNoMoreElements() {
        String[] data = {};
        TestSpliterator<String> spliterator = new TestSpliterator<>(data);

        java.util.Iterator<String> it = Spliterators.iterator(spliterator);

        boolean threw = false;
        try {
            it.next();
        } catch (NoSuchElementException e) {
            threw = true;
        }
        if (!threw) {
            throw new AssertionError("Expected NoSuchElementException when calling next() on empty iterator");
        }
    }
}
