/*
 * @test
 * @summary Test HashSet(Collection<? extends E>) constructor for normal and edge cases
 * @run main HashSetWitupTest
 */

import java.util.HashSet;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class HashSetWitupTest {
    public static void main(String[] args) {
        testNullCollection();
        testEmptyCollection();
        testNormalCollection();
        testLargeCollection();
    }

    private static void testNullCollection() {
        try {
            new HashSet<>(null);
            throw new RuntimeException("Expected NullPointerException for null collection");
        } catch (NullPointerException expected) {
            // Expected
        }
    }

    private static void testEmptyCollection() {
        Collection<Object> empty = Collections.emptyList();
        HashSet<Object> set = new HashSet<>(empty);
        if (set.size() != 0) {
            throw new RuntimeException("HashSet constructed from empty collection should be empty");
        }
    }

    private static void testNormalCollection() {
        Collection<String> coll = new ArrayList<>();
        coll.add("a");
        coll.add("b");
        coll.add("c");
        HashSet<String> set = new HashSet<>(coll);
        if (set.size() != coll.size()) {
            throw new RuntimeException("HashSet size should match collection size");
        }
        for (String s : coll) {
            if (!set.contains(s)) {
                throw new RuntimeException("HashSet should contain all elements from collection");
            }
        }
    }

    private static void testLargeCollection() {
        // Create a large collection to test capacity calculation
        Collection<Integer> large = new ArrayList<>();
        final int size = 1000;
        for (int i = 0; i < size; i++) {
            large.add(i);
        }
        HashSet<Integer> set = new HashSet<>(large);
        if (set.size() != size) {
            throw new RuntimeException("HashSet size should match large collection size");
        }
        for (int i = 0; i < size; i++) {
            if (!set.contains(i)) {
                throw new RuntimeException("HashSet should contain all elements from large collection");
            }
        }
    }
}
