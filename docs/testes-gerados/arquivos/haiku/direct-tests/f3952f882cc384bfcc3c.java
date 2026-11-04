/*
 * @test
 * @summary Verifies that the HashSet(Collection) constructor copies elements, removes duplicates, supports null, and rejects a null collection
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
        testCopiesElements();
        testDuplicatesRemoved();
        testNullElementPreserved();
        testLargeCollection();
        testNullCollectionThrowsNPE();
        testCopyIsIndependentOfSource();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testEmptyCollection() {
        Set<String> s = new HashSet<String>(new ArrayList<String>());
        check(s.isEmpty(), "copy of empty collection must be empty");
        check(s.size() == 0, "size of copy of empty collection must be 0");
    }

    static void testCopiesElements() {
        List<String> src = Arrays.asList("a", "b", "c");
        Set<String> s = new HashSet<String>(src);
        check(s.size() == 3, "expected size 3 but was " + s.size());
        check(s.contains("a"), "missing a");
        check(s.contains("b"), "missing b");
        check(s.contains("c"), "missing c");
    }

    static void testDuplicatesRemoved() {
        List<String> src = Arrays.asList("x", "y", "x", "y", "x");
        Set<String> s = new HashSet<String>(src);
        check(s.size() == 2, "expected 2 unique elements but was " + s.size());
        check(s.contains("x") && s.contains("y"), "unique elements missing");
    }

    static void testNullElementPreserved() {
        List<String> src = Arrays.asList("a", null);
        Set<String> s = new HashSet<String>(src);
        check(s.size() == 2, "expected size 2 with null but was " + s.size());
        check(s.contains(null), "null element must be copied");
        check(s.contains("a"), "missing a");
    }

    static void testLargeCollection() {
        ArrayList<Integer> src = new ArrayList<Integer>();
        for (int i = 0; i < 1000; i++) {
            src.add(i);
        }
        HashSet<Integer> s = new HashSet<Integer>(src);
        check(s.size() == 1000, "expected size 1000 but was " + s.size());
        for (int i = 0; i < 1000; i++) {
            check(s.contains(i), "missing element " + i);
        }
    }

    static void testNullCollectionThrowsNPE() {
        boolean thrown = false;
        try {
            new HashSet<String>((Collection<String>) null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "null collection must cause NullPointerException");
    }

    static void testCopyIsIndependentOfSource() {
        ArrayList<String> src = new ArrayList<String>();
        src.add("one");
        HashSet<String> s = new HashSet<String>(src);
        src.add("two");
        check(!s.contains("two"), "later source change must not affect the copy");
        check(s.size() == 1, "copy size changed after source modification");
        s.add("three");
        check(!src.contains("three"), "change to copy must not affect source");
    }
}
