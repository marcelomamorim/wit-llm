/*
 * @test
 * @summary Tests for Hashtable.forEach: full traversal, null action rejection, and fail-fast ConcurrentModificationException on structural change
 * @run main HashtableWitupTest
 */

import java.util.ConcurrentModificationException;
import java.util.Hashtable;

public class HashtableWitupTest {

    public static void main(String[] args) {
        testForEachVisitsAllEntries();
        testNullActionOnEmptyTableThrowsNpe();
        testNullActionOnNonEmptyTableThrowsNpe();
        testStructuralModificationThrowsCme();
        testReplacingExistingValueDoesNotThrow();
    }

    static void testForEachVisitsAllEntries() {
        Hashtable<String, Integer> h = new Hashtable<>();
        h.put("one", 1);
        h.put("two", 2);
        h.put("three", 3);
        final int[] count = {0};
        final int[] sum = {0};
        h.forEach((k, v) -> {
            count[0]++;
            sum[0] += v;
        });
        if (count[0] != 3) {
            throw new RuntimeException("Expected 3 visits, got " + count[0]);
        }
        if (sum[0] != 6) {
            throw new RuntimeException("Expected sum 6, got " + sum[0]);
        }
    }

    static void testNullActionOnEmptyTableThrowsNpe() {
        Hashtable<String, Integer> h = new Hashtable<>();
        boolean thrown = false;
        try {
            h.forEach(null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("Expected NullPointerException for null action on empty table");
        }
    }

    static void testNullActionOnNonEmptyTableThrowsNpe() {
        Hashtable<String, Integer> h = new Hashtable<>();
        h.put("a", 1);
        boolean thrown = false;
        try {
            h.forEach(null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("Expected NullPointerException for null action on non-empty table");
        }
    }

    static void testStructuralModificationThrowsCme() {
        Hashtable<String, Integer> h = new Hashtable<>();
        h.put("a", 1);
        boolean thrown = false;
        try {
            h.forEach((k, v) -> h.put("added-" + k, 0));
        } catch (ConcurrentModificationException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("Expected ConcurrentModificationException after structural modification");
        }
    }

    static void testReplacingExistingValueDoesNotThrow() {
        Hashtable<String, Integer> h = new Hashtable<>();
        h.put("a", 1);
        h.forEach((k, v) -> h.put(k, v + 10));
        if (h.get("a") != 11) {
            throw new RuntimeException("Expected value 11, got " + h.get("a"));
        }
    }
}
