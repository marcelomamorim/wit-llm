/* @test    @summary Test for Arrays.next() method to ensure correct element retrieval and exception handling.    @run main ArraysWitupTest */

import java.util.Arrays;
import java.util.NoSuchElementException;

public class ArraysWitupTest {

    public static void main(String[] args) {
        testNextReturnsCorrectElement();
        testNextThrowsExceptionWhenExceeding();
    }

    private static void testNextReturnsCorrectElement() {
        Integer[] array = {1, 2, 3};
        Arrays<Integer> arrayInstance = new Arrays<>(array);
        try {
            Integer first = arrayInstance.next();
            assert first.equals(1) : "Expected first element to be 1";
            Integer second = arrayInstance.next();
            assert second.equals(2) : "Expected second element to be 2";
            Integer third = arrayInstance.next();
            assert third.equals(3) : "Expected third element to be 3";
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception during normal iteration", e);
        }
    }

    private static void testNextThrowsExceptionWhenExceeding() {
        Integer[] array = {4};
        Arrays<Integer> arrayInstance = new Arrays<>(array);
        try {
            arrayInstance.next();
            arrayInstance.next(); // Should throw
            throw new AssertionError("Expected NoSuchElementException was not thrown");
        } catch (NoSuchElementException e) {
            // Expected exception
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception type", e);
        }
    }

    private static class Arrays<E> {
        private final E[] a;
        private int cursor = 0;

        public Arrays(E[] array) {
            this.a = array;
        }

        public E next() {
            int i = cursor;
            if (i >= a.length) {
                throw new NoSuchElementException();
            }
            cursor = i + 1;
            return a[i];
        }
    }
}
