/*
 * @test
 * @summary Test CopyOnWriteArrayList.removeIf(Predicate) for null argument and basic removal
 * @run main CopyOnWriteArrayListWitupTest
 */

import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Predicate;

public class CopyOnWriteArrayListWitupTest {
    public static void main(String[] args) {
        testRemoveIfNullPredicate();
        testRemoveIfBasicBehavior();
    }

    private static void testRemoveIfNullPredicate() {
        CopyOnWriteArrayList<String> list = new CopyOnWriteArrayList<>();
        try {
            list.removeIf(null);
            throw new RuntimeException("Expected NullPointerException not thrown");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    private static void testRemoveIfBasicBehavior() {
        String[] elements = {"a", "b", "c", "d"};
        CopyOnWriteArrayList<String> list = new CopyOnWriteArrayList<>(elements);

        // Remove elements equal to "b" or "c"
        Predicate<String> filter = new Predicate<String>() {
            public boolean test(String s) {
                return "b".equals(s) || "c".equals(s);
            }
        };

        boolean removed = list.removeIf(filter);
        if (!removed) {
            throw new RuntimeException("Expected elements to be removed");
        }

        // After removal, list should contain "a" and "d" only
        if (list.size() != 2) {
            throw new RuntimeException("Expected size 2 after removal, got " + list.size());
        }
        if (!"a".equals(list.get(0)) || !"d".equals(list.get(1))) {
            throw new RuntimeException("List contents incorrect after removal: " + list);
        }

        // Remove elements that do not exist (should return false)
        Predicate<String> noMatchFilter = new Predicate<String>() {
            public boolean test(String s) {
                return "x".equals(s);
            }
        };

        boolean removedNone = list.removeIf(noMatchFilter);
        if (removedNone) {
            throw new RuntimeException("Expected no elements to be removed");
        }
    }
}
