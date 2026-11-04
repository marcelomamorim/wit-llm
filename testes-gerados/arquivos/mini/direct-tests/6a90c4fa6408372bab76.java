/*
 * @test
 * @summary Test LinkedList.listIterator(int) for valid and boundary indices
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

        // Test listIterator at index 0 (start)
        ListIterator<String> it0 = list.listIterator(0);
        if (it0.nextIndex() != 0) {
            throw new AssertionError("listIterator(0) nextIndex should be 0");
        }
        if (!"a".equals(it0.next())) {
            throw new AssertionError("listIterator(0) first element should be 'a'");
        }

        // Test listIterator at middle index
        ListIterator<String> it1 = list.listIterator(1);
        if (it1.nextIndex() != 1) {
            throw new AssertionError("listIterator(1) nextIndex should be 1");
        }
        if (!"b".equals(it1.next())) {
            throw new AssertionError("listIterator(1) first element should be 'b'");
        }

        // Test listIterator at last index (size)
        ListIterator<String> it3 = list.listIterator(3);
        if (it3.nextIndex() != 3) {
            throw new AssertionError("listIterator(size) nextIndex should be size");
        }
        if (it3.hasNext()) {
            throw new AssertionError("listIterator(size) should have no next element");
        }

        // Test listIterator with negative index - expect IndexOutOfBoundsException
        try {
            list.listIterator(-1);
            throw new AssertionError("listIterator(-1) should throw IndexOutOfBoundsException");
        } catch (IndexOutOfBoundsException expected) {
        }

        // Test listIterator with index greater than size - expect IndexOutOfBoundsException
        try {
            list.listIterator(4);
            throw new AssertionError("listIterator(size+1) should throw IndexOutOfBoundsException");
        } catch (IndexOutOfBoundsException expected) {
        }
    }
}
