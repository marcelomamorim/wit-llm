/*
 * @test
 * @summary Deterministic tests for HashSet(Collection<? extends E> c) constructor
 * @run main HashSetWitupTest
 */

import java.util.HashSet;
import java.util.Arrays;

public class HashSetWitupTest {

    public static void main(String[] args) {
        testEmptyCollection();
        testNullCollection();
        testCollectionWithElements();
        testCollectionWithNullElement();
    }

    static void testEmptyCollection() {
        HashSet<String> set = new HashSet<>(Arrays.asList());
        assert set.size() == 0 : "Expected empty set";
    }

    static void testNullCollection() {
        try {
            new HashSet<>(null);
            assert false : "Expected NullPointerException";
        } catch (NullPointerException e) {
            // expected
        }
    }

    static void testCollectionWithElements() {
        HashSet<Integer> original = new HashSet<>();
        original.add(1);
        original.add(2);
        original.add(3);
        HashSet<Integer> set = new HashSet<>(original);
        assert set.size() == 3 : "Set should contain 3 elements";
        assert set.contains(1) : "Set should contain 1";
        assert set.contains(2) : "Set should contain 2";
        assert set.contains(3) : "Set should contain 3";
    }

    static void testCollectionWithNullElement() {
        HashSet<String> original = new HashSet<>();
        original.add(null);
        HashSet<String> set = new HashSet<>(original);
        assert set.size() == 1 : "Set should contain 1 element";
        assert set.contains(null) : "Set should contain null";
    }
}
