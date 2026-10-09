/* @test
   @summary Tests the listIterator(int index) method of CopyOnWriteArrayList.
   @run main CopyOnWriteArrayListWitupTest_P49ca2d7dd395
 */
import java.util.ListIterator;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.Objects;

public class CopyOnWriteArrayListWitupTest_P49ca2d7dd395 {
    public static void main(String[] args) {
        testListIteratorAtValidIndex();
        testListIteratorAtNegativeIndex();
        testListIteratorAtIndexGreaterThanSize();
        testListIteratorAtEmptyList();
    }

    private static void testListIteratorAtValidIndex() {
        CopyOnWriteArrayList<String> list = new CopyOnWriteArrayList<>();
        list.add("a");
        list.add("b");
        list.add("c");
        int index = 1;
        ListIterator<String> it = list.listIterator(index);
        Objects.requireNonNull(it, "listIterator should not return null");
        // Verify that the iterator's next element is at position index
        if (it.hasNext()) {
            String nextElem = it.next();
            if (!"b".equals(nextElem)) {
                throw new AssertionError("Expected 'b', got: " + nextElem);
            }
        } else {
            throw new AssertionError("Expected next element at index " + index);
        }
        // Verify previous element
        if (it.hasPrevious()) {
            String prevElem = it.previous();
            if (!"b".equals(prevElem)) {
                throw new AssertionError("Expected previous 'b', got: " + prevElem);
            }
        } else {
            throw new AssertionError("Expected previous element at index " + index);
        }
    }

    private static void testListIteratorAtNegativeIndex() {
        CopyOnWriteArrayList<String> list = new CopyOnWriteArrayList<>();
        list.add("x");
        list.add("y");
        list.add("z");
        try {
            list.listIterator(-1);
            throw new AssertionError("Expected IndexOutOfBoundsException for negative index");
        } catch (IndexOutOfBoundsException e) {
            // expected
        }
    }

    private static void testListIteratorAtIndexGreaterThanSize() {
        CopyOnWriteArrayList<String> list = new CopyOnWriteArrayList<>();
        list.add("m");
        list.add("n");
        int size = list.size();
        try {
            list.listIterator(size + 1);
            throw new AssertionError("Expected IndexOutOfBoundsException for index > size");
        } catch (IndexOutOfBoundsException e) {
            // expected
        }
    }

    private static void testListIteratorAtEmptyList() {
        CopyOnWriteArrayList<String> list = new CopyOnWriteArrayList<>();
        try {
            list.listIterator(0);
            // Should succeed, iterator at start of empty list
        } catch (IndexOutOfBoundsException e) {
            throw new AssertionError("Did not expect IndexOutOfBoundsException at index 0 on empty list");
        }
        try {
            list.listIterator(1);
            throw new AssertionError("Expected IndexOutOfBoundsException for index > size on empty list");
        } catch (IndexOutOfBoundsException e) {
            // expected
        }
        try {
            list.listIterator(-1);
            throw new AssertionError("Expected IndexOutOfBoundsException for negative index on empty list");
        } catch (IndexOutOfBoundsException e) {
            // expected
        }
    }
}
