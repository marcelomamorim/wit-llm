/*
 * @test
 * @summary Test LinkedList.listIterator(int) for valid and invalid indices
 * @run main java.util.LinkedListWitupTest
 */

import java.util.LinkedList;
import java.util.ListIterator;
import java.lang.IndexOutOfBoundsException;

public class LinkedListWitupTest {
    public static void main(String[] args) {
        LinkedList<String> list = new LinkedList<>();
        list.add("a");
        list.add("b");
        list.add("c");

        // Test valid indices: 0 (start), size/2 (middle), size (end)
        ListIterator<String> it0 = list.listIterator(0);
        if (!it0.hasNext() || !"a".equals(it0.next())) {
            throw new AssertionError("listIterator(0) did not return iterator at start");
        }

        ListIterator<String> it1 = list.listIterator(1);
        if (!it1.hasNext() || !"b".equals(it1.next())) {
            throw new AssertionError("listIterator(1) did not return iterator at index 1");
        }

        ListIterator<String> it3 = list.listIterator(3);
        if (it3.hasNext()) {
            throw new AssertionError("listIterator(size) should be at end with no next element");
        }

        // Test invalid indices: negative and greater than size
        try {
            list.listIterator(-1);
            throw new AssertionError("listIterator(-1) did not throw IndexOutOfBoundsException");
        } catch (IndexOutOfBoundsException expected) {
            // expected
        }

        try {
            list.listIterator(4); // size is 3
            throw new AssertionError("listIterator(4) did not throw IndexOutOfBoundsException");
        } catch (IndexOutOfBoundsException expected) {
            // expected
        }
    }
}
