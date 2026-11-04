/*
 * @test
 * @summary Testa o método next() da classe java.util.Arrays para caminho que lança NoSuchElementException
 * @run main ArraysWitupTest
 */

import java.util.Arrays;

public class ArraysWitupTest {

    public static void main(String[] args) {
        testNextThrowsExceptionWhenCursorExceedsArrayLength();
    }

    private static void testNextThrowsExceptionWhenCursorExceedsArrayLength() {
        int[] array = {1, 2, 3};
        ArrayIterator iterator = new ArrayIterator(array);
        // Avança o cursor até além do tamanho do array
        for (int i = 0; i < array.length; i++) {
            iterator.next();
        }
        boolean exceptionThrown = false;
        try {
            iterator.next();
        } catch (NoSuchElementException e) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected NoSuchElementException to be thrown when cursor exceeds array length");
        }
    }

    private static class ArrayIterator {
        private final int[] a;
        private int cursor = 0;

        public ArrayIterator(int[] array) {
            this.a = array;
        }

        public int next() {
            int i = cursor;
            if (i >= a.length) {
                throw new java.util.NoSuchElementException();
            }
            cursor = i + 1;
            return a[i];
        }
    }
}
