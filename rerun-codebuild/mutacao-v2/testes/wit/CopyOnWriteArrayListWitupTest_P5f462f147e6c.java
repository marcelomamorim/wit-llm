/*
 * @test
 * @summary Test CopyOnWriteArrayList.listIterator(int) for valid and invalid indices
 * @run main CopyOnWriteArrayListWitupTest_P5f462f147e6c
 */

import java.util.ListIterator;
import java.util.concurrent.CopyOnWriteArrayList;

public class CopyOnWriteArrayListWitupTest_P5f462f147e6c {
    public static void main(String[] args) {
        testListIteratorValidIndex();
        testListIteratorInvalidIndexNegative();
        testListIteratorInvalidIndexTooLarge();
    }

    private static void testListIteratorValidIndex() {
        Integer[] data = {1, 2, 3};
        CopyOnWriteArrayList<Integer> list = new CopyOnWriteArrayList<>(data);
        ListIterator<Integer> it = list.listIterator(1);
        if (!it.hasNext()) {
            throw new AssertionError("Iterator should have next element at index 1");
        }
        Integer val = it.next();
        if (!val.equals(2)) {
            throw new AssertionError("Expected element 2 at index 1, got " + val);
        }
    }

    private static void testListIteratorInvalidIndexNegative() {
        CopyOnWriteArrayList<Integer> list = new CopyOnWriteArrayList<>();
        try {
            list.listIterator(-1);
            throw new AssertionError("Expected IndexOutOfBoundsException for negative index");
        } catch (IndexOutOfBoundsException e) {
            // expected
        }
    }

    private static void testListIteratorInvalidIndexTooLarge() {
        Integer[] data = {1, 2, 3};
        CopyOnWriteArrayList<Integer> list = new CopyOnWriteArrayList<>(data);
        try {
            list.listIterator(4); // index > size
            throw new AssertionError("Expected IndexOutOfBoundsException for index > size");
        } catch (IndexOutOfBoundsException e) {
            // expected
        }
    }
}
