/*
 * @test
 * @summary Test deterministic behavior of java.util.ArrayList.add(E e) including fail-fast on concurrent modification
 * @run main ArrayListWitupTest
 */

import java.util.ArrayList;
import java.util.ConcurrentModificationException;

public class ArrayListWitupTest {
    public static void main(String[] args) {
        testAddBasic();
        testAddConcurrentModificationException();
    }

    private static void testAddBasic() {
        ArrayList<String> list = new ArrayList<>();
        list.add("first");
        list.add("second");
        if (list.size() != 2) {
            throw new AssertionError("Expected size 2 but got " + list.size());
        }
        if (!"first".equals(list.get(0))) {
            throw new AssertionError("Expected first element to be 'first'");
        }
        if (!"second".equals(list.get(1))) {
            throw new AssertionError("Expected second element to be 'second'");
        }
    }

    private static void testAddConcurrentModificationException() {
        final ArrayList<String> list = new ArrayList<>();
        list.add("a");
        list.add("b");

        // Create a SubList to simulate the context of the expath
        java.util.List<String> subList = list.subList(0, list.size());

        // We will try to add via the subList's iterator add method to trigger ConcurrentModificationException
        java.util.ListIterator<String> it = subList.listIterator();

        // Move iterator forward once
        it.next();

        // Modify the root list structurally to cause modCount mismatch
        list.add("c");

        boolean cmeThrown = false;
        try {
            it.add("d"); // This should throw ConcurrentModificationException
        } catch (ConcurrentModificationException ex) {
            cmeThrown = true;
        }

        if (!cmeThrown) {
            throw new AssertionError("Expected ConcurrentModificationException was not thrown");
        }
    }
}
