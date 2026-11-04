/*
 * @test
 * @summary Checks Hashtable.forEach null action handling, entry visiting and concurrent modification detection
 * @run main HashtableWitupTest
 */

import java.util.ConcurrentModificationException;
import java.util.Hashtable;
import java.util.function.BiConsumer;

public class HashtableWitupTest {

    public static void main(String[] args) {
        testNullActionOnEmptyTable();
        testNullActionOnNonEmptyTable();
        testVisitsAllEntries();
        testConcurrentModificationDetected();
    }

    static void testNullActionOnEmptyTable() {
        Hashtable<String, Integer> ht = new Hashtable<>();
        boolean thrown = false;
        try {
            ht.forEach(null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new AssertionError("expected NullPointerException for null action on empty table");
        }
    }

    static void testNullActionOnNonEmptyTable() {
        Hashtable<String, Integer> ht = new Hashtable<>();
        ht.put("a", 1);
        boolean thrown = false;
        try {
            ht.forEach(null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new AssertionError("expected NullPointerException for null action on non-empty table");
        }
    }

    static void testVisitsAllEntries() {
        Hashtable<String, Integer> ht = new Hashtable<>();
        ht.put("one", 1);
        ht.put("two", 2);
        ht.put("three", 3);
        final int[] count = new int[1];
        final int[] sum = new int[1];
        BiConsumer<String, Integer> action = (k, v) -> {
            count[0]++;
            sum[0] += v;
        };
        ht.forEach(action);
        if (count[0] != 3) {
            throw new AssertionError("expected 3 visits but was " + count[0]);
        }
        if (sum[0] != 6) {
            throw new AssertionError("expected value sum 6 but was " + sum[0]);
        }
    }

    static void testConcurrentModificationDetected() {
        Hashtable<String, Integer> ht = new Hashtable<>();
        ht.put("a", 1);
        BiConsumer<String, Integer> action = (k, v) -> ht.put("new", 2);
        boolean thrown = false;
        try {
            ht.forEach(action);
        } catch (ConcurrentModificationException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new AssertionError("expected ConcurrentModificationException when table is structurally modified");
        }
    }
}
