/*
 * @test
 * @summary Test for CopyOnWriteArrayList.next() method, including exception path.
 * @run main CopyOnWriteArrayListWitupTest
 */

import java.util.concurrent.CopyOnWriteArrayList;
import java.util.NoSuchElementException;

public class CopyOnWriteArrayListWitupTest {

    public static void main(String[] args) {
        testNextWithEmptyList();
        testNextWithValidElement();
        testNextThrowsException();
    }

    static void testNextWithEmptyList() {
        CopyOnWriteArrayList<String> list = new CopyOnWriteArrayList<>();
        // Create iterator snapshot
        list.add("a");
        CopyOnWriteArrayList<String>.COWIterator iterator = list.new COWIterator();
        // Advance cursor to end
        while (iterator.hasNext()) {
            iterator.next();
        }
        // Now cursor is at end
        try {
            iterator.next();
            // Should throw NoSuchElementException
            throw new AssertionError("Expected NoSuchElementException not thrown");
        } catch (NoSuchElementException e) {
            // Expected exception
        }
    }

    static void testNextWithValidElement() {
        CopyOnWriteArrayList<String> list = new CopyOnWriteArrayList<>();
        list.add("first");
        list.add("second");
        CopyOnWriteArrayList<String>.COWIterator iterator = list.new COWIterator();
        String first = iterator.next();
        if (!"first".equals(first)) {
            throw new AssertionError("Expected 'first', got: " + first);
        }
        String second = iterator.next();
        if (!"second".equals(second)) {
            throw new AssertionError("Expected 'second', got: " + second);
        }
    }

    static void testNextThrowsException() {
        CopyOnWriteArrayList<String> list = new CopyOnWriteArrayList<>();
        list.add("only");
        CopyOnWriteArrayList<String>.COWIterator iterator = list.new COWIterator();
        // Move to end
        while (iterator.hasNext()) {
            iterator.next();
        }
        // Now cursor is at end
        try {
            iterator.next();
            throw new AssertionError("Expected NoSuchElementException not thrown");
        } catch (NoSuchElementException e) {
            // Expected
        }
    }
}
