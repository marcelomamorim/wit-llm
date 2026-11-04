/*
 * @test
 * @summary Test CopyOnWriteArrayList.COWIterator.next() for NoSuchElementException and normal behavior
 * @run main CopyOnWriteArrayListWitupTest
 */

import java.util.NoSuchElementException;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.Iterator;

public class CopyOnWriteArrayListWitupTest {
    public static void main(String[] args) {
        testNextNormal();
        testNextThrowsNoSuchElementException();
    }

    private static void testNextNormal() {
        CopyOnWriteArrayList<String> list = new CopyOnWriteArrayList<>(new String[] {"a", "b", "c"});
        Iterator<String> it = list.iterator();
        int count = 0;
        while (it.hasNext()) {
            String val = it.next();
            if (count == 0) {
                if (!"a".equals(val)) {
                    throw new AssertionError("Expected 'a' at first element but got: " + val);
                }
            } else if (count == 1) {
                if (!"b".equals(val)) {
                    throw new AssertionError("Expected 'b' at second element but got: " + val);
                }
            } else if (count == 2) {
                if (!"c".equals(val)) {
                    throw new AssertionError("Expected 'c' at third element but got: " + val);
                }
            }
            count++;
        }
        if (count != 3) {
            throw new AssertionError("Iterator did not iterate over all elements, count=" + count);
        }
    }

    private static void testNextThrowsNoSuchElementException() {
        CopyOnWriteArrayList<String> list = new CopyOnWriteArrayList<>();
        Iterator<String> it = list.iterator();
        if (it.hasNext()) {
            throw new AssertionError("Iterator should have no next element on empty list");
        }
        boolean thrown = false;
        try {
            it.next();
        } catch (NoSuchElementException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new AssertionError("Expected NoSuchElementException when calling next() on empty iterator");
        }
    }
}
