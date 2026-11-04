/*
 * @test
 * @summary Test java.util.ArrayList.retainAll(Collection<?>) for exception and normal behavior
 * @run main ArrayListWitupTest
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.ConcurrentModificationException;
import java.util.List;
import java.util.Objects;

public class ArrayListWitupTest {
    public static void main(String[] args) {
        testRetainAllNullCollection();
        testRetainAllConcurrentModification();
        testRetainAllNormalBehavior();
    }

    private static void testRetainAllNullCollection() {
        ArrayList<String> list = new ArrayList<>();
        list.add("a");
        try {
            list.retainAll(null);
            throw new RuntimeException("Expected NullPointerException not thrown");
        } catch (NullPointerException expected) {
            // Expected exception
        }
    }

    private static void testRetainAllConcurrentModification() {
        final ArrayList<String> list = new ArrayList<>();
        list.add("a");
        list.add("b");

        // Create a subList to trigger ConcurrentModificationException on retainAll
        List<String> subList = list.subList(0, 2);

        // Modify the backing list structurally to cause modCount mismatch
        list.add("c");

        try {
            subList.retainAll(Arrays.asList("a"));
            throw new RuntimeException("Expected ConcurrentModificationException not thrown");
        } catch (ConcurrentModificationException expected) {
            // Expected exception
        }
    }

    private static void testRetainAllNormalBehavior() {
        ArrayList<String> list = new ArrayList<>(Arrays.asList("a", "b", "c", "d"));
        Collection<String> toRetain = Arrays.asList("b", "d", "e");

        boolean changed = list.retainAll(toRetain);

        if (!changed) {
            throw new RuntimeException("Expected list to be changed");
        }

        if (list.size() != 2 || !list.contains("b") || !list.contains("d")) {
            throw new RuntimeException("List contents incorrect after retainAll");
        }

        // Test retainAll with collection that retains all elements (no change)
        changed = list.retainAll(Arrays.asList("b", "d"));
        if (changed) {
            throw new RuntimeException("Expected no change when retaining all elements");
        }
    }
}
