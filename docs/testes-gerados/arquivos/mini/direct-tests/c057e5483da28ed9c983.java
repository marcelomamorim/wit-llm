/*
 * @test
 * @summary Test java.util.ArrayList.size() method correctness and fail-fast behavior
 * @run main java.util.ArrayListWitupTest
 */

import java.util.ArrayList;
import java.util.ConcurrentModificationException;

public class ArrayListWitupTest {
    public static void main(String[] args) {
        testSizeOnEmptyList();
        testSizeAfterAdditions();
        testFailFastOnSize();
    }

    private static void testSizeOnEmptyList() {
        ArrayList<Object> list = new ArrayList<>();
        int sz = list.size();
        if (sz != 0) {
            throw new AssertionError("Expected size 0 for empty list, got " + sz);
        }
    }

    private static void testSizeAfterAdditions() {
        ArrayList<String> list = new ArrayList<>();
        list.add("a");
        list.add("b");
        list.add("c");
        int sz = list.size();
        if (sz != 3) {
            throw new AssertionError("Expected size 3 after adding 3 elements, got " + sz);
        }
    }

    private static void testFailFastOnSize() {
        ArrayList<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);

        // Create an iterator to cause a modCount snapshot
        java.util.Iterator<Integer> it = list.iterator();

        // Modify list structurally to cause concurrent modification
        list.add(3);

        try {
            list.size(); // should throw ConcurrentModificationException due to checkForComodification
            throw new AssertionError("Expected ConcurrentModificationException on size() after structural modification");
        } catch (ConcurrentModificationException expected) {
            // expected
        }

        // Also test that calling iterator.next() after modification throws CME
        try {
            it.next();
            throw new AssertionError("Expected ConcurrentModificationException on iterator.next() after modification");
        } catch (ConcurrentModificationException expected) {
            // expected
        }
    }
}
