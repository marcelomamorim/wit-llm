/*
 * @test
 * @summary Testa o método next() de CopyOnWriteArrayList para comportamento normal e exceções.
 * @run main CopyOnWriteArrayListWitupTest
 */

import java.util.concurrent.CopyOnWriteArrayList;
import java.util.NoSuchElementException;

public class CopyOnWriteArrayListWitupTest {

    public static void main(String[] args) {
        testNextNormal();
        testNextThrows();
    }

    static void testNextNormal() {
        CopyOnWriteArrayList<String> list = new CopyOnWriteArrayList<>();
        list.add("a");
        list.add("b");
        list.add("c");
        java.util.Iterator<String> it = list.iterator();
        try {
            while (it.hasNext()) {
                String s = it.next();
                System.out.println("Next element: " + s);
            }
        } catch (Exception e) {
            throw new RuntimeException("Unexpected exception in normal iteration", e);
        }
    }

    static void testNextThrows() {
        CopyOnWriteArrayList<String> list = new CopyOnWriteArrayList<>();
        list.add("x");
        list.add("y");
        java.util.Iterator<String> it = list.iterator();
        // Consume all elements
        while (it.hasNext()) {
            it.next();
        }
        // Now iterator is exhausted; next() should throw NoSuchElementException
        try {
            it.next();
            throw new AssertionError("Expected NoSuchElementException not thrown");
        } catch (NoSuchElementException e) {
            // Expected exception
        } catch (Exception e) {
            throw new RuntimeException("Unexpected exception in exhausted iterator", e);
        }
    }
}
