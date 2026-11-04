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
        testNextThrowsNoSuchElementException();
    }

    static void testNextReturnsElementsInOrder() {
        CopyOnWriteArrayList<String> list = new CopyOnWriteArrayList<>(new String[] {"a", "b", "c"});
        Iterator<String> it = list.iterator();

        String first = it.next();
        if (!"a".equals(first)) {
            throw new AssertionError("Expected first element 'a', got: " + first);
        }

        String second = it.next();
        if (!"b".equals(second)) {
            throw new AssertionError("Expected second element 'b', got: " + second);
        }

        String third = it.next();
        if (!"c".equals(third)) {
            throw new AssertionError("Expected third element 'c', got: " + third);
        }
    }

    static void testNextThrowsNoSuchElementException() {
        CopyOnWriteArrayList<String> list = new CopyOnWriteArrayList<>();
        Iterator<String> it = list.iterator();

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
