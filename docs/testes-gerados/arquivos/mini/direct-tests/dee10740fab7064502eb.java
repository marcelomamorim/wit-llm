/*
 * @test
 * @summary Test CopyOnWriteArrayList.removeIf(Predicate) behavior
 * @run main CopyOnWriteArrayListWitupTest
 */

import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Predicate;
import java.util.Objects;

public class CopyOnWriteArrayListWitupTest {
    public static void main(String[] args) {
        testRemoveIfWithNullPredicate();
        testRemoveIfRemovesMatchingElements();
        testRemoveIfReturnsFalseWhenNoMatch();
        testRemoveIfOnEmptyList();
    }

    static void testRemoveIfWithNullPredicate() {
        CopyOnWriteArrayList<String> list = new CopyOnWriteArrayList<>();
        try {
            list.removeIf(null);
            throw new AssertionError("Expected NullPointerException for null predicate");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    static void testRemoveIfRemovesMatchingElements() {
        CopyOnWriteArrayList<String> list = new CopyOnWriteArrayList<>();
        list.add("a");
        list.add("b");
        list.add("c");
        boolean removed = list.removeIf(new Predicate<String>() {
            public boolean test(String s) {
                return "b".equals(s) || "c".equals(s);
            }
        });
        if (!removed) {
            throw new AssertionError("Expected removeIf to return true when elements removed");
        }
        if (list.size() != 1 || !"a".equals(list.get(0))) {
            throw new AssertionError("List contents incorrect after removeIf");
        }
    }

    static void testRemoveIfReturnsFalseWhenNoMatch() {
        CopyOnWriteArrayList<String> list = new CopyOnWriteArrayList<>();
        list.add("a");
        list.add("b");
        boolean removed = list.removeIf(new Predicate<String>() {
            public boolean test(String s) {
                return false;
            }
        });
        if (removed) {
            throw new AssertionError("Expected removeIf to return false when no elements removed");
        }
        if (list.size() != 2) {
            throw new AssertionError("List size changed unexpectedly");
        }
    }

    static void testRemoveIfOnEmptyList() {
        CopyOnWriteArrayList<String> list = new CopyOnWriteArrayList<>();
        boolean removed = list.removeIf(new Predicate<String>() {
            public boolean test(String s) {
                return true;
            }
        });
        if (removed) {
            throw new AssertionError("Expected removeIf to return false on empty list");
        }
        if (!list.isEmpty()) {
            throw new AssertionError("List should remain empty");
        }
    }
}
