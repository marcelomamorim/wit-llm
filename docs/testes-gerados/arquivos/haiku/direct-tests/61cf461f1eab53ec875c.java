/*
 * @test
 * @summary Vector.listIterator(int) accepts indices 0..size, rejects others with IndexOutOfBoundsException, and positions the iterator correctly
 * @run main VectorWitupTest
 */

import java.util.ListIterator;
import java.util.Vector;

public class VectorWitupTest {

    public static void main(String[] args) {
        testValidIndices();
        testNegativeIndex();
        testIndexBeyondSize();
        testEmptyVector();
        testIteratorPositionAndTraversal();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static Vector<String> sample() {
        Vector<String> v = new Vector<>();
        v.add("a");
        v.add("b");
        v.add("c");
        return v;
    }

    static void expectIndexOutOfBounds(Vector<String> v, int index) {
        try {
            v.listIterator(index);
        } catch (IndexOutOfBoundsException e) {
            check(("Index: " + index).equals(e.getMessage()),
                  "unexpected message: " + e.getMessage());
            return;
        }
        throw new RuntimeException("no IndexOutOfBoundsException for index " + index);
    }

    static void testValidIndices() {
        Vector<String> v = sample();
        for (int i = 0; i <= v.size(); i++) {
            ListIterator<String> it = v.listIterator(i);
            check(it.nextIndex() == i, "nextIndex mismatch at " + i);
            check(it.previousIndex() == i - 1, "previousIndex mismatch at " + i);
        }
    }

    static void testNegativeIndex() {
        expectIndexOutOfBounds(sample(), -1);
    }

    static void testIndexBeyondSize() {
        expectIndexOutOfBounds(sample(), 4);
    }

    static void testEmptyVector() {
        Vector<String> v = new Vector<>();
        ListIterator<String> it = v.listIterator(0);
        check(!it.hasNext(), "empty vector iterator should have no next");
        check(!it.hasPrevious(), "empty vector iterator should have no previous");
        expectIndexOutOfBounds(v, 1);
    }

    static void testIteratorPositionAndTraversal() {
        Vector<String> v = sample();
        ListIterator<String> it = v.listIterator(1);
        check("b".equals(it.next()), "expected b at index 1");
        check("c".equals(it.next()), "expected c at index 2");
        check(!it.hasNext(), "iterator should be exhausted");

        ListIterator<String> end = v.listIterator(v.size());
        check(!end.hasNext(), "iterator at size should have no next");
        check(end.hasPrevious(), "iterator at size should have previous");
        check("c".equals(end.previous()), "expected c when stepping back from size");
    }
}
