/*
 * @test
 * @summary Test CopyOnWriteArrayList.listIterator(int) boundary and normal cases
 * @run main CopyOnWriteArrayListWitupTest_P5f462f147e6c
 */

import java.util.ListIterator;
import java.util.concurrent.CopyOnWriteArrayList;

public class CopyOnWriteArrayListWitupTest_P5f462f147e6c {
    public static void main(String[] args) {
        CopyOnWriteArrayList<String> list = new CopyOnWriteArrayList<>();

        // Test listIterator on empty list with index 0 (valid)
        ListIterator<String> it0 = list.listIterator(0);
        if (it0.hasNext()) {
            throw new RuntimeException("Iterator on empty list should have no next");
        }

        // Add elements
        list.add("a");
        list.add("b");
        list.add("c");

        // Test listIterator with index 0 (start)
        ListIterator<String> itStart = list.listIterator(0);
        if (!itStart.hasNext() || !"a".equals(itStart.next())) {
            throw new RuntimeException("Iterator at index 0 should return first element 'a'");
        }

        // Test listIterator with index in middle
        ListIterator<String> itMid = list.listIterator(1);
        if (!itMid.hasNext() || !"b".equals(itMid.next())) {
            throw new RuntimeException("Iterator at index 1 should return element 'b'");
        }

        // Test listIterator with index at size (end)
        ListIterator<String> itEnd = list.listIterator(list.size());
        if (itEnd.hasNext()) {
            throw new RuntimeException("Iterator at index size should have no next");
        }

        // Test listIterator with negative index throws IndexOutOfBoundsException
        try {
            list.listIterator(-1);
            throw new RuntimeException("Expected IndexOutOfBoundsException for negative index");
        } catch (IndexOutOfBoundsException e) {
            // expected
        }

        // Test listIterator with index greater than size throws IndexOutOfBoundsException
        try {
            list.listIterator(list.size() + 1);
            throw new RuntimeException("Expected IndexOutOfBoundsException for index > size");
        } catch (IndexOutOfBoundsException e) {
            // expected
        }
    }
}
