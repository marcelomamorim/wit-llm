/*
 * @test
 * @summary Test Collections.removeIf(Predicate) wrapper behavior and null filter handling
 * @run main CollectionsWitupTest
 */

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;

public class CollectionsWitupTest {
    public static void main(String[] args) {
        testRemoveIfWithValidPredicate();
        testRemoveIfWithNullPredicate();
    }

    private static void testRemoveIfWithValidPredicate() {
        List<String> list = new ArrayList<>();
        list.add("keep");
        list.add("remove");
        list.add("keep");

        Predicate<String> predicate = new Predicate<String>() {
            public boolean test(String s) {
                return "remove".equals(s);
            }
        };

        boolean changed = Collections.checkedList(list, String.class).removeIf(predicate);

        if (!changed) {
            throw new RuntimeException("Expected removeIf to return true when element removed");
        }
        if (list.size() != 2 || list.contains("remove")) {
            throw new RuntimeException("Element 'remove' should have been removed from the list");
        }
    }

    private static void testRemoveIfWithNullPredicate() {
        List<String> list = new ArrayList<>();
        list.add("test");

        try {
            Collections.checkedList(list, String.class).removeIf(null);
            throw new RuntimeException("Expected NullPointerException when passing null predicate");
        } catch (NullPointerException expected) {
            // Expected exception
        }
    }
}
