/*
 * @test
 * @summary Test HashSet(Collection) constructor behavior
 * @run main HashSetWitupTest
 */

import java.util.HashSet;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

public class HashSetWitupTest {
    public static void main(String[] args) {
        testConstructorWithEmptyCollection();
        testConstructorWithNonEmptyCollection();
        testConstructorWithDuplicates();
    }

    private static void testConstructorWithEmptyCollection() {
        Collection<String> empty = new ArrayList<>();
        HashSet<String> set = new HashSet<>(empty);
        if (set.size() != 0) {
            throw new RuntimeException("Expected empty HashSet size 0, got " + set.size());
        }
    }

    private static void testConstructorWithNonEmptyCollection() {
        Collection<String> coll = new ArrayList<>();
        coll.add("a");
        coll.add("b");
        coll.add("c");
        HashSet<String> set = new HashSet<>(coll);
        if (set.size() != 3) {
            throw new RuntimeException("Expected HashSet size 3, got " + set.size());
        }
        if (!set.contains("a") || !set.contains("b") || !set.contains("c")) {
            throw new RuntimeException("HashSet missing expected elements");
        }
    }

    private static void testConstructorWithDuplicates() {
        Collection<String> coll = new ArrayList<>();
        coll.add("dup");
        coll.add("dup");
        coll.add("unique");
        HashSet<String> set = new HashSet<>(coll);
        if (set.size() != 2) {
            throw new RuntimeException("Expected HashSet size 2 with duplicates, got " + set.size());
        }
        if (!set.contains("dup") || !set.contains("unique")) {
            throw new RuntimeException("HashSet missing expected elements with duplicates");
        }
    }
}
