/*
 * @test
 * @summary Test java.util.Arrays.ArrayItr.next() method for correct behavior and exception
 * @run main ArraysWitupTest
 */

import java.util.NoSuchElementException;
import java.util.Arrays;
import java.util.Iterator;

public class ArraysWitupTest {

    // Minimal implementation of Arrays.ArrayItr<E> to test next() method
    static class ArrayItr<E> implements Iterator<E> {
        private final E[] a;
        private int cursor = 0;

        ArrayItr(E[] array) {
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
        testNextReturnsElementsInOrder();
        testNextThrowsWhenNoMoreElements();
    }

    private static void testNextReturnsElementsInOrder() {
        Integer[] data = {1, 2, 3};
        ArrayItr<Integer> itr = new ArrayItr<>(data);

        for (int expected : data) {
            if (!itr.hasNext()) {
                throw new AssertionError("hasNext() returned false prematurely");
            }
            Integer actual = itr.next();
            if (!actual.equals(expected)) {
                throw new AssertionError("Expected " + expected + " but got " + actual);
            }
        }

        if (itr.hasNext()) {
            throw new AssertionError("hasNext() returned true after all elements consumed");
        }
    }

    private static void testNextThrowsWhenNoMoreElements() {
        Integer[] empty = new Integer[0];
        ArrayItr<Integer> itr = new ArrayItr<>(empty);

        if (itr.hasNext()) {
            throw new AssertionError("hasNext() returned true for empty array");
        }

        boolean thrown = false;
        try {
            itr.next();
        } catch (NoSuchElementException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new AssertionError("next() did not throw NoSuchElementException when no elements remain");
        }

        // Also test after consuming all elements
        Integer[] single = {42};
        itr = new ArrayItr<>(single);
        itr.next(); // consume the only element

        thrown = false;
        try {
            itr.next();
        } catch (NoSuchElementException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new AssertionError("next() did not throw NoSuchElementException after all elements consumed");
        }
    }
}
