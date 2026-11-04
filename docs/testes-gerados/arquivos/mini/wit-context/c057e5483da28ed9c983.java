/*
 * @test
 * @summary Test ArrayList.size() method including fail-fast behavior on concurrent modification
 * @run main ArrayListWitupTest
 */

import java.util.ArrayList;
import java.util.ConcurrentModificationException;

public class ArrayListWitupTest {
    public static void main(String[] args) {
        testSizeReturnsCorrectValue();
        testSizeThrowsOnConcurrentModification();
    }

    private static void testSizeReturnsCorrectValue() {
        ArrayList<String> list = new ArrayList<>();
        if (list.size() != 0) {
            throw new AssertionError("Expected size 0 for new list");
        }
        list.add("a");
        list.add("b");
        if (list.size() != 2) {
            throw new AssertionError("Expected size 2 after adding two elements");
        }
    }

    private static void testSizeThrowsOnConcurrentModification() {
        final ArrayList<String> list = new ArrayList<>();
        list.add("x");
        list.add("y");

        // Create a SubList to trigger modCount checks
        java.util.List<String> subList = list.subList(0, 2);

        // Modify the root list structurally to cause concurrent modification
        list.add("z");

        try {
            // Calling size() on subList should throw ConcurrentModificationException
            subList.size();
            throw new AssertionError("Expected ConcurrentModificationException not thrown");
        } catch (ConcurrentModificationException expected) {
            // Expected exception
        }
    }
}
