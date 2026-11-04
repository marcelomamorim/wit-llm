/*
 * @test
 * @summary Test java.util.Vector removeIf(Predicate) method
 * @run main VectorWitupTest
 */

import java.util.Vector;
import java.util.function.Predicate;
import java.util.Objects;

public class VectorWitupTest {
    public static void main(String[] args) {
        testRemoveIfNullPredicate();
        testRemoveIfRemoveSome();
        testRemoveIfRemoveNone();
        testRemoveIfRemoveAll();
    }

    private static void testRemoveIfNullPredicate() {
        Vector<String> v = new Vector<>();
        v.add("a");
        try {
            v.removeIf(null);
            throw new RuntimeException("Expected NullPointerException not thrown");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    private static void testRemoveIfRemoveSome() {
        Vector<String> v = new Vector<>();
        v.add("keep");
        v.add("remove");
        v.add("keep");
        v.add("remove");
        Predicate<String> filter = new Predicate<String>() {
            public boolean test(String s) {
                return "remove".equals(s);
            }
        };
        boolean changed = v.removeIf(filter);
        if (!changed) {
            throw new RuntimeException("Expected removeIf to return true when elements removed");
        }
        if (v.size() != 2 || !v.get(0).equals("keep") || !v.get(1).equals("keep")) {
            throw new RuntimeException("Vector contents incorrect after removeIf");
        }
    }

    private static void testRemoveIfRemoveNone() {
        Vector<Integer> v = new Vector<>();
        v.add(1);
        v.add(2);
        v.add(3);
        Predicate<Integer> filter = new Predicate<Integer>() {
            public boolean test(Integer i) {
                return i > 10;
            }
        };
        boolean changed = v.removeIf(filter);
        if (changed) {
            throw new RuntimeException("Expected removeIf to return false when no elements removed");
        }
        if (v.size() != 3 || !v.get(0).equals(1) || !v.get(1).equals(2) || !v.get(2).equals(3)) {
            throw new RuntimeException("Vector contents incorrect after removeIf with no removals");
        }
    }

    private static void testRemoveIfRemoveAll() {
        Vector<String> v = new Vector<>();
        v.add("a");
        v.add("b");
        v.add("c");
        Predicate<String> filter = new Predicate<String>() {
            public boolean test(String s) {
                return true;
            }
        };
        boolean changed = v.removeIf(filter);
        if (!changed) {
            throw new RuntimeException("Expected removeIf to return true when all elements removed");
        }
        if (!v.isEmpty()) {
            throw new RuntimeException("Vector should be empty after removeIf removing all elements");
        }
    }
}
