/*
 * @test
 * @summary Test CopyOnWriteArrayList.removeIf(Predicate) for null filter and normal behavior
 * @run main CopyOnWriteArrayListWitupTest
 */

import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Predicate;
import java.util.Objects;

public class CopyOnWriteArrayListWitupTest {
    public static void main(String[] args) {
        testRemoveIfNullFilter();
        testRemoveIfNormalBehavior();
    }

    private static void testRemoveIfNullFilter() {
        CopyOnWriteArrayList<String> list = new CopyOnWriteArrayList<>();
        try {
            list.removeIf(null);
            throw new RuntimeException("Expected NullPointerException not thrown");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    private static void testRemoveIfNormalBehavior() {
        CopyOnWriteArrayList<String> list = new CopyOnWriteArrayList<>(new String[] {"a", "b", "c", "d"});
        Predicate<String> removeBAndD = new Predicate<String>() {
            public boolean test(String s) {
                return "b".equals(s) || "d".equals(s);
            }
        };
        boolean changed = list.removeIf(removeBAndD);
        if (!changed) {
            throw new RuntimeException("Expected removeIf to return true when elements removed");
        }
        if (list.size() != 2) {
            throw new RuntimeException("Expected list size 2 after removals, got " + list.size());
        }
        if (!"a".equals(list.get(0)) || !"c".equals(list.get(1))) {
            throw new RuntimeException("List contents incorrect after removeIf");
        }
        // Test removeIf with predicate that removes nothing
        Predicate<String> removeNone = new Predicate<String>() {
            public boolean test(String s) {
                return false;
            }
        };
        boolean changed2 = list.removeIf(removeNone);
        if (changed2) {
            throw new RuntimeException("Expected removeIf to return false when no elements removed");
        }
    }
}
