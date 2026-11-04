/*
 * @test
 * @summary Test CopyOnWriteArrayList.removeAll(Collection) for null argument and basic behavior
 * @run main CopyOnWriteArrayListWitupTest
 */

import java.util.concurrent.CopyOnWriteArrayList;
import java.util.Collection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public class CopyOnWriteArrayListWitupTest {
    public static void main(String[] args) {
        testRemoveAllNullCollection();
        testRemoveAllBasic();
    }

    private static void testRemoveAllNullCollection() {
        CopyOnWriteArrayList<String> list = new CopyOnWriteArrayList<>();
        boolean npeThrown = false;
        try {
            list.removeAll(null);
        } catch (NullPointerException e) {
            npeThrown = true;
        }
        if (!npeThrown) {
            throw new AssertionError("Expected NullPointerException when passing null to removeAll");
        }
    }

    private static void testRemoveAllBasic() {
        String[] elements = {"a", "b", "c", "d"};
        CopyOnWriteArrayList<String> list = new CopyOnWriteArrayList<>(elements);

        Collection<String> toRemove = new ArrayList<>();
        toRemove.add("b");
        toRemove.add("d");

        boolean changed = list.removeAll(toRemove);
        if (!changed) {
            throw new AssertionError("removeAll should return true when elements are removed");
        }

        // After removal, list should contain only "a" and "c"
        if (list.size() != 2 || !list.contains("a") || !list.contains("c")) {
            throw new AssertionError("List contents incorrect after removeAll: " + list);
        }

        // Removing elements not present should return false
        Collection<String> notPresent = Arrays.asList("x", "y");
        boolean changed2 = list.removeAll(notPresent);
        if (changed2) {
            throw new AssertionError("removeAll should return false when no elements removed");
        }
    }
}
