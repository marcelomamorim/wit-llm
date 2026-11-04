/*
 * @test
 * @summary Behavioral tests for the HashSet(Collection) constructor: empty, duplicate and null elements, copy independence, large input, and null argument.
 * @run main HashSetWitupTest
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class HashSetWitupTest {

    public static void main(String[] args) {
        testEmptyCollection();
        testDuplicatesAndNullElements();
        testCopyIsIndependentOfSource();
        testLargeCollection();
        testNullCollectionThrowsNPE();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testEmptyCollection() {
        Set<String> set = new HashSet<>(new ArrayList<String>());
        check(set.isEmpty(), "set built from empty collection must be empty");
        check(set.size() == 0, "size must be 0");
        check(set.add("x"), "add must succeed on set built from empty collection");
        check(set.contains("x"), "added element must be present");
        check(set.size() == 1, "size must be 1 after one add");
    }

    static void testDuplicatesAndNullElements() {
        List<String> source = Arrays.asList("a", "b", "a", null, null);
        Set<String> set = new HashSet<>(source);
        check(set.size() == 3, "duplicates must collapse; expected size 3 but was " + set.size());
        check(set.contains("a"), "set must contain a");
        check(set.contains("b"), "set must contain b");
        check(set.contains(null), "set must permit and contain null");
        check(!set.contains("c"), "set must not contain c");
    }

    static void testCopyIsIndependentOfSource() {
        List<String> source = new ArrayList<>();
        source.add("one");
        source.add("two");
        Set<String> set = new HashSet<>(source);

        source.add("three");
        check(set.size() == 2, "later source changes must not affect set size");
        check(!set.contains("three"), "later source additions must not appear in set");

        set.add("four");
        check(source.size() == 3, "changes to set must not affect source list");
        check(!source.contains("four"), "set addition must not appear in source list");
    }

    static void testLargeCollection() {
        List<Integer> source = new ArrayList<>();
        for (int i = 0; i < 10000; i++) {
            source.add(i);
        }
        Set<Integer> set = new HashSet<>(source);
        check(set.size() == 10000, "large collection must yield size 10000 but was " + set.size());
        check(set.contains(0), "set must contain 0");
        check(set.contains(9999), "set must contain 9999");
        check(!set.contains(10000), "set must not contain 10000");
    }

    static void testNullCollectionThrowsNPE() {
        boolean thrown = false;
        try {
            Collection<String> nullCollection = null;
            new HashSet<String>(nullCollection);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "null collection argument must throw NullPointerException");
    }
}
