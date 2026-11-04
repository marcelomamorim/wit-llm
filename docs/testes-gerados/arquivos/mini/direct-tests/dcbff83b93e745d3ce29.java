/*
 * @test
 * @summary Test LinkedList.set(E) method behavior
 * @run main LinkedListWitupTest
 */

import java.util.LinkedList;
import java.util.ListIterator;

public class LinkedListWitupTest {
    public static void main(String[] args) {
        testSetAfterNext();
        testSetWithoutNext();
    }

    private static void testSetAfterNext() {
        LinkedList<String> list = new LinkedList<>();
        list.add("a");
        list.add("b");

        ListIterator<String> it = list.listIterator();
        String first = it.next();
        if (!"a".equals(first)) {
            throw new Error("Expected first element 'a', got: " + first);
        }

        it.set("z");

        // Verify that the element was replaced
        String replaced = list.get(0);
        if (!"z".equals(replaced)) {
            throw new Error("Expected replaced element 'z', got: " + replaced);
        }
    }

    private static void testSetWithoutNext() {
        LinkedList<String> list = new LinkedList<>();
        list.add("a");

        ListIterator<String> it = list.listIterator();
        boolean threw = false;
        try {
            it.set("z");
        } catch (IllegalStateException e) {
            threw = true;
        }
        if (!threw) {
            throw new Error("Expected IllegalStateException when calling set() before next()");
        }
    }
}
