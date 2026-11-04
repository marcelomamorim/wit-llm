/*
 * @test
 * @summary Test java.util.ArrayList.removeIf(Predicate) for null argument and basic removal
 * @run main java.util.ArrayListWitupTest
 */

import java.util.ArrayList;
import java.util.function.Predicate;

public class ArrayListWitupTest {
    public static void main(String[] args) {
        testRemoveIfNullPredicate();
        testRemoveIfBasicRemoval();
    }

    private static void testRemoveIfNullPredicate() {
        ArrayList<String> list = new ArrayList<>();
        try {
            list.removeIf(null);
            throw new RuntimeException("Expected NullPointerException not thrown");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    private static void testRemoveIfBasicRemoval() {
        ArrayList<String> list = new ArrayList<>();
        list.add("keep");
        list.add("remove");
        list.add("keep");
        list.add("remove");

        Predicate<String> toRemove = new Predicate<String>() {
            public boolean test(String s) {
                return "remove".equals(s);
            }
        };

        boolean changed = list.removeIf(toRemove);

        if (!changed) {
            throw new RuntimeException("removeIf should return true when elements are removed");
        }

        if (list.size() != 2) {
            throw new RuntimeException("List size after removeIf is incorrect: " + list.size());
        }

        if (!"keep".equals(list.get(0)) || !"keep".equals(list.get(1))) {
            throw new RuntimeException("List contents after removeIf are incorrect: " + list);
        }
    }
}
