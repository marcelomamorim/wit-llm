/*
 * @test
 * @summary Test CopyOnWriteArrayList next() method behavior
 * @run main CopyOnWriteArrayListWitupTest
 */

import java.util.NoSuchElementException;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.Iterator;

public class CopyOnWriteArrayListWitupTest {
    public static void main(String[] args) {
        testNextReturnsElementsInOrder();
        testNextThrowsWhenNoMoreElements();
    }

    private static void testNextReturnsElementsInOrder() {
        CopyOnWriteArrayList<String> list = new CopyOnWriteArrayList<>(new String[] {"a", "b", "c"});
        Iterator<String> it = list.iterator();

        StringBuilder sb = new StringBuilder();
        while (it.hasNext()) {
            sb.append(it.next());
        }

        if (!"abc".equals(sb.toString())) {
            throw new AssertionError("Iterator next() did not return elements in order");
        }
    }

    private static void testNextThrowsWhenNoMoreElements() {
        CopyOnWriteArrayList<String> list = new CopyOnWriteArrayList<>();
        Iterator<String> it = list.iterator();

        boolean thrown = false;
        try {
            it.next();
        } catch (NoSuchElementException e) {
            thrown = true;
        }

        if (!thrown) {
            throw new AssertionError("Iterator next() did not throw NoSuchElementException when no elements");
        }
    }
}
