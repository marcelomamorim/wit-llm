/*
 * @test
 * @summary Test java.util.Arrays inner class iterator next() method behavior
 * @run main ArraysWitupTest
 */

import java.util.NoSuchElementException;
import java.util.Iterator;

public class ArraysWitupTest {

    static class DummyIterator<E> implements Iterator<E> {
        private final E[] a;
        private int cursor = 0;

        DummyIterator(E[] array) {
            this.a = array;
        }

        public boolean hasNext() {
            return cursor < a.length;
        }

        public E next() {
            int i = cursor;
            if (i >= a.length) {
                throw new NoSuchElementException();
            }
            cursor = i + 1;
            return a[i];
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    public static void main(String[] args) {
        // Test next() returns elements in order
        String[] data = new String[] {"a", "b", "c"};
        DummyIterator<String> it = new DummyIterator<>(data);

        if (!it.hasNext()) throw new RuntimeException("Expected hasNext true initially");

        String first = it.next();
        if (!"a".equals(first)) throw new RuntimeException("Expected first element 'a', got: " + first);

        String second = it.next();
        if (!"b".equals(second)) throw new RuntimeException("Expected second element 'b', got: " + second);

        String third = it.next();
        if (!"c".equals(third)) throw new RuntimeException("Expected third element 'c', got: " + third);

        if (it.hasNext()) throw new RuntimeException("Expected hasNext false after last element");

        // Test next() throws NoSuchElementException when no elements remain
        boolean threw = false;
        try {
            it.next();
        } catch (NoSuchElementException e) {
            threw = true;
        }
        if (!threw) throw new RuntimeException("Expected NoSuchElementException when calling next() past end");

        // Test empty array iterator
        DummyIterator<Integer> emptyIt = new DummyIterator<>(new Integer[0]);
        if (emptyIt.hasNext()) throw new RuntimeException("Expected hasNext false for empty iterator");
        threw = false;
        try {
            emptyIt.next();
        } catch (NoSuchElementException e) {
            threw = true;
        }
        if (!threw) throw new RuntimeException("Expected NoSuchElementException for next() on empty iterator");
    }
}
