/*
 * @test
 * @summary WITUP-derived checks for ConcurrentHashMap.replace(K, V) null handling and observable replace semantics
 * @run main ConcurrentHashMapWitupTest
 */

import java.util.concurrent.ConcurrentHashMap;

public class ConcurrentHashMapWitupTest {

    public static void main(String[] args) {
        testNullKeyThrowsNpe();
        testNullValueThrowsNpe();
        testNullValueDoesNotMutateExistingMapping();
        testNullKeyDoesNotMutateMap();
        testReplaceAbsentKeyReturnsNullAndDoesNotInsert();
        testReplacePresentKeyReturnsOldValue();
        testReplacePresentKeyUpdatesValue();
    }

    static void testNullKeyThrowsNpe() {
        final ConcurrentHashMap<Integer, Integer> map = new ConcurrentHashMap<>();
        expectNpe(() -> map.replace(null, 1), "replace(null, v)");
    }

    static void testNullValueThrowsNpe() {
        final ConcurrentHashMap<Integer, Integer> map = new ConcurrentHashMap<>();
        expectNpe(() -> map.replace(1, null), "replace(k, null)");
    }

    static void testNullValueDoesNotMutateExistingMapping() {
        final ConcurrentHashMap<Integer, Integer> map = new ConcurrentHashMap<>();
        map.put(1, 10);
        expectNpe(() -> map.replace(1, null), "replace(1, null) with existing key");
        check(map.get(1) == 10, "existing mapping changed after NPE");
        check(map.size() == 1, "size changed after NPE");
    }

    static void testNullKeyDoesNotMutateMap() {
        final ConcurrentHashMap<Integer, Integer> map = new ConcurrentHashMap<>();
        map.put(1, 10);
        expectNpe(() -> map.replace(null, 20), "replace(null, 20)");
        check(map.get(1) == 10, "existing mapping changed after NPE on null key");
        check(map.size() == 1, "size changed after NPE on null key");
    }

    static void testReplaceAbsentKeyReturnsNullAndDoesNotInsert() {
        final ConcurrentHashMap<Integer, Integer> map = new ConcurrentHashMap<>();
        Integer previous = map.replace(5, 50);
        check(previous == null, "replace on absent key should return null");
        check(!map.containsKey(5), "replace on absent key should not insert");
        check(map.size() == 0, "size should remain 0");
    }

    static void testReplacePresentKeyReturnsOldValue() {
        final ConcurrentHashMap<Integer, Integer> map = new ConcurrentHashMap<>();
        map.put(7, 70);
        Integer previous = map.replace(7, 71);
        check(previous != null && previous == 70, "replace should return old value 70");
    }

    static void testReplacePresentKeyUpdatesValue() {
        final ConcurrentHashMap<Integer, Integer> map = new ConcurrentHashMap<>();
        map.put(8, 80);
        map.replace(8, 81);
        check(map.get(8) == 81, "value should be updated to 81");
        check(map.size() == 1, "size should remain 1");
    }

    static void expectNpe(Runnable action, String name) {
        try {
            action.run();
        } catch (NullPointerException e) {
            return;
        }
        throw new RuntimeException(name + " did not throw NullPointerException");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
