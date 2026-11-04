/*
 * @test
 * @summary Test java.util.AbstractList.subList(int, int) for exception and normal cases
 * @run main AbstractListWitupTest
 */

import java.util.AbstractList;
import java.util.List;
import java.util.RandomAccess;

public class AbstractListWitupTest {

    // Minimal concrete subclass of AbstractList to allow testing
    static class TestList extends AbstractList<Integer> {
        private final int size;

        TestList(int size) {
            this.size = size;
        }

        @Override
        public Integer get(int index) {
            if (index < 0 || index >= size) {
                throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
            }
            return index;
        }

        @Override
        public int size() {
            return size;
        }
    }

    public static void main(String[] args) {
        testSubListValidRange();
        testSubListFromIndexNegative();
        testSubListToIndexGreaterThanSize();
        testSubListFromIndexGreaterThanToIndex();
    }

    private static void testSubListValidRange() {
        TestList list = new TestList(5);
        List<Integer> sub = list.subList(1, 4);
        if (sub.size() != 3) {
            throw new AssertionError("Expected sublist size 3 but got " + sub.size());
        }
        for (int i = 0; i < sub.size(); i++) {
            if (sub.get(i) != i + 1) {
                throw new AssertionError("Expected element " + (i + 1) + " but got " + sub.get(i));
            }
        }
    }

    private static void testSubListFromIndexNegative() {
        TestList list = new TestList(5);
        try {
            list.subList(-1, 3);
            throw new AssertionError("Expected IndexOutOfBoundsException for fromIndex < 0");
        } catch (IndexOutOfBoundsException expected) {
            // expected
        }
    }

    private static void testSubListToIndexGreaterThanSize() {
        TestList list = new TestList(5);
        try {
            list.subList(1, 6);
            throw new AssertionError("Expected IndexOutOfBoundsException for toIndex > size");
        } catch (IndexOutOfBoundsException expected) {
            // expected
        }
    }

    private static void testSubListFromIndexGreaterThanToIndex() {
        TestList list = new TestList(5);
        try {
            list.subList(4, 2);
            throw new AssertionError("Expected IllegalArgumentException for fromIndex > toIndex");
        } catch (IllegalArgumentException expected) {
            // expected
        }
    }
}
