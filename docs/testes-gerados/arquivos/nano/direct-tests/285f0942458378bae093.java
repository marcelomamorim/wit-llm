/* @test    @summary Test retainAll method of Vector for correct behavior and exception handling    @run main VectorWitupTest */

import java.util.Vector;
import java.util.Collection;

public class VectorWitupTest {

    public static void main(String[] args) {
        testRetainAllWithNull();
        testRetainAllWithEmptyCollection();
        testRetainAllWithSubset();
        testRetainAllWithDisjoint();
        testRetainAllWithAll();
    }

    static void testRetainAllWithNull() {
        Vector<Integer> vector = new Vector<>();
        vector.add(1);
        vector.add(2);
        boolean exceptionThrown = false;
        try {
            vector.retainAll(null);
        } catch (NullPointerException e) {
            exceptionThrown = true;
        }
        assert exceptionThrown : "Expected NullPointerException when passing null to retainAll";
    }

    static void testRetainAllWithEmptyCollection() {
        Vector<String> vector = new Vector<>();
        vector.add("a");
        vector.add("b");
        boolean changed = vector.retainAll(java.util.Collections.emptyList());
        assert changed : "retainAll with empty collection should return true if vector was modified";
        assert vector.isEmpty() : "Vector should be empty after retainAll with empty collection";
    }

    static void testRetainAllWithSubset() {
        Vector<String> vector = new Vector<>();
        vector.add("a");
        vector.add("b");
        vector.add("c");
        Collection<String> c = java.util.Arrays.asList("a", "b");
        boolean changed = vector.retainAll(c);
        assert changed : "retainAll with subset should return true if vector was modified";
        assert vector.size() == 2 : "Vector should contain only " + c;
        assert vector.contains("a") && vector.contains("b") : "Vector should contain 'a' and 'b'";
    }

    static void testRetainAllWithDisjoint() {
        Vector<Integer> vector = new Vector<>();
        vector.add(1);
        vector.add(2);
        Collection<Integer> c = java.util.Arrays.asList(3, 4);
        boolean changed = vector.retainAll(c);
        assert changed : "retainAll with disjoint collection should return true if vector was modified";
        assert vector.isEmpty() : "Vector should be empty after retainAll with disjoint collection";
    }

    static void testRetainAllWithAll() {
        Vector<String> vector = new Vector<>();
        vector.add("x");
        vector.add("y");
        Collection<String> c = java.util.Arrays.asList("x", "y");
        boolean changed = vector.retainAll(c);
        assert !changed : "retainAll with same collection should return false if no change";
        assert vector.size() == 2 : "Vector should remain unchanged";
        assert vector.contains("x") && vector.contains("y") : "Vector should contain 'x' and 'y'";
    }
}
