/* @test    @summary Test for CopyOnWriteArrayList.listIterator(int) with valid and invalid indices    @run main CopyOnWriteArrayListWitupTest_P7fb41260609a */

import java.util.ListIterator;
import java.util.concurrent.CopyOnWriteArrayList;

public class CopyOnWriteArrayListWitupTest_P7fb41260609a {

    public static void main(String[] args) {
        testListIteratorValidIndex();
        testListIteratorNegativeIndex();
        testListIteratorIndexGreaterThanSize();
    }

    static void testListIteratorValidIndex() {
        CopyOnWriteArrayList<String> list = new CopyOnWriteArrayList<>();
        list.add("a");
        list.add("b");
        list.add("c");
        try {
            ListIterator<String> it = list.listIterator(1);
            if (it == null) {
                throw new AssertionError("listIterator returned null for valid index");
            }
            // Verify that the iterator points to the correct position
            if (!it.hasNext()) {
                throw new AssertionError("Iterator has no next element at valid index");
            }
            String nextElem = it.next();
            if (!"b".equals(nextElem)) {
                throw new AssertionError("Expected 'b', got: " + nextElem);
            }
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception for valid index: " + e);
        }
    }

    static void testListIteratorNegativeIndex() {
        CopyOnWriteArrayList<String> list = new CopyOnWriteArrayList<>();
        list.add("a");
        list.add("b");
        list.add("c");
        try {
            list.listIterator(-1);
            throw new AssertionError("Expected IndexOutOfBoundsException for negative index");
        } catch (IndexOutOfBoundsException e) {
            // Expected exception
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception type: " + e);
        }
    }

    static void testListIteratorIndexGreaterThanSize() {
        CopyOnWriteArrayList<String> list = new CopyOnWriteArrayList<>();
        list.add("a");
        list.add("b");
        list.add("c");
        int size = list.size();
        try {
            list.listIterator(size + 1);
            throw new AssertionError("Expected IndexOutOfBoundsException for index greater than size");
        } catch (IndexOutOfBoundsException e) {
            // Expected exception
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception type: " + e);
        }
    }
}
