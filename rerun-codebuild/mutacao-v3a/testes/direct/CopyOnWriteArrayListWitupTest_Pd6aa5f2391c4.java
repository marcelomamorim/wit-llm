/*
 * @test
 * @summary Tests the listIterator(int index) method of CopyOnWriteArrayList.
 * @run main CopyOnWriteArrayListWitupTest_Pd6aa5f2391c4
 */

import java.util.concurrent.CopyOnWriteArrayList;
import java.util.ListIterator;

public class CopyOnWriteArrayListWitupTest_Pd6aa5f2391c4 {

    public static void main(String[] args) {
        testListIteratorWithValidIndex();
        testListIteratorWithNegativeIndex();
        testListIteratorWithIndexEqualToSize();
        testListIteratorWithIndexGreaterThanSize();
    }

    static void testListIteratorWithValidIndex() {
        CopyOnWriteArrayList<String> list = new CopyOnWriteArrayList<>();
        list.add("a");
        list.add("b");
        list.add("c");
        ListIterator<String> it = list.listIterator(1);
        boolean hasNext = it.hasNext();
        String nextElem = null;
        if (hasNext) {
            nextElem = it.next();
        }
        assert hasNext : "Expected hasNext() to be true";
        assert "b".equals(nextElem) : "Expected next element to be 'b'";
    }

    static void testListIteratorWithNegativeIndex() {
        CopyOnWriteArrayList<String> list = new CopyOnWriteArrayList<>();
        list.add("x");
        try {
            list.listIterator(-1);
            assert false : "Expected IndexOutOfBoundsException for negative index";
        } catch (IndexOutOfBoundsException e) {
            // expected
        }
    }

    static void testListIteratorWithIndexEqualToSize() {
        CopyOnWriteArrayList<String> list = new CopyOnWriteArrayList<>();
        list.add("x");
        list.add("y");
        int size = list.size();
        ListIterator<String> it = list.listIterator(size);
        boolean hasNext = it.hasNext();
        assert !hasNext : "Expected hasNext() to be false at index equal to size";
    }

    static void testListIteratorWithIndexGreaterThanSize() {
        CopyOnWriteArrayList<String> list = new CopyOnWriteArrayList<>();
        list.add("x");
        int size = list.size();
        try {
            list.listIterator(size + 1);
            assert false : "Expected IndexOutOfBoundsException for index greater than size";
        } catch (IndexOutOfBoundsException e) {
            // expected
        }
    }
}
