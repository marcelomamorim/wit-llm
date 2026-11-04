/*
 * @test
 * @summary Test java.util.Vector.removeIf(Predicate) for null argument and basic removal
 * @run main VectorWitupTest
 */

import java.util.Vector;
import java.util.function.Predicate;
import java.util.Objects;

public class VectorWitupTest {
    public static void main(String[] args) {
        testRemoveIfNullPredicate();
        testRemoveIfBasicRemoval();
    }

    private static void testRemoveIfNullPredicate() {
        Vector<String> v = new Vector<>();
        try {
            v.removeIf(null);
            throw new AssertionError("Expected NullPointerException for null predicate");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    private static void testRemoveIfBasicRemoval() {
        Vector<String> v = new Vector<>();
        v.add("keep");
        v.add("remove");
        v.add("keep");
        v.add("remove");

        Predicate<String> removePredicate = new Predicate<String>() {
            public boolean test(String s) {
                return "remove".equals(s);
            }
        };

        boolean changed = v.removeIf(removePredicate);

        if (!changed) {
            throw new AssertionError("Expected removeIf to return true when elements removed");
        }

        if (v.size() != 2) {
            throw new AssertionError("Expected vector size 2 after removal, got " + v.size());
        }

        for (String s : v) {
            if ("remove".equals(s)) {
                throw new AssertionError("Vector still contains element that should have been removed");
            }
        }
    }
}
