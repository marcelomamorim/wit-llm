/*
 * @test
 * @summary Test forEachRemaining method delegation to backing iterator
 * @run main CollectionsWitupTest
 */

import java.util.Collections;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.function.Consumer;

public class CollectionsWitupTest {

    static class TestIterator<E> implements Iterator<E> {
        private final E[] elements;
        private int index = 0;

        TestIterator(E[] elements) {
            this.elements = elements;
        }

        public boolean hasNext() {
            return index < elements.length;
        }

        public E next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            return elements[index++];
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

        public void forEachRemaining(Consumer<? super E> action) {
            while (hasNext()) {
                action.accept(next());
            }
        }
    }

    static class TestForEachRemaining<E> extends Collections {
        private final Iterator<E> i;

        TestForEachRemaining(Iterator<E> i) {
            this.i = i;
        }

        public void forEachRemaining(Consumer<? super E> action) {
            // Use backing collection version
            i.forEachRemaining(action);
        }
    }

    public static void main(String[] args) {
        final String[] data = {"a", "b", "c"};
        TestIterator<String> backingIterator = new TestIterator<>(data);
        TestForEachRemaining<String> test = new TestForEachRemaining<>(backingIterator);

        final StringBuilder sb = new StringBuilder();
        Consumer<String> consumer = new Consumer<String>() {
            public void accept(String s) {
                sb.append(s);
            }
        };

        test.forEachRemaining(consumer);

        if (!sb.toString().equals("abc")) {
            throw new AssertionError("forEachRemaining did not process all elements correctly: " + sb);
        }

        // Test that calling forEachRemaining on empty iterator does nothing
        TestIterator<String> emptyIterator = new TestIterator<>(new String[0]);
        TestForEachRemaining<String> testEmpty = new TestForEachRemaining<>(emptyIterator);
        final StringBuilder sbEmpty = new StringBuilder();
        testEmpty.forEachRemaining(new Consumer<String>() {
            public void accept(String s) {
                sbEmpty.append(s);
            }
        });
        if (sbEmpty.length() != 0) {
            throw new AssertionError("forEachRemaining on empty iterator should not call consumer");
        }
    }
}
