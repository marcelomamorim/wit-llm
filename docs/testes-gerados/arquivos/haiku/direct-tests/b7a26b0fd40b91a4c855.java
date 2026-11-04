/*
 * @test
 * @summary Tests Hashtable.remove(Object key, Object value) observable behavior
 * @run main HashtableWitupTest
 */

import java.util.Hashtable;

public class HashtableWitupTest {

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static final class CollidingKey {
        final String id;

        CollidingKey(String id) {
            this.id = id;
        }

        @Override
        public int hashCode() {
            return 42;
        }

        @Override
        public boolean equals(Object o) {
            return o instanceof CollidingKey && ((CollidingKey) o).id.equals(id);
        }
    }

    static void testRemoveExistingPair() {
        Hashtable<String, Integer> t = new Hashtable<>();
        t.put("one", 1);
        t.put("two", 2);
        check(t.remove("one", 1), "remove should return true for matching pair");
        check(t.get("one") == null, "key should be removed");
        check(t.size() == 1, "size should decrease by one");
        check(t.get("two") == 2, "other entries must be preserved");
    }

    static void testValueMismatchReturnsFalse() {
        Hashtable<String, Integer> t = new Hashtable<>();
        t.put("one", 1);
        check(!t.remove("one", 99), "remove should return false when value differs");
        check(t.get("one") == 1, "entry must remain unchanged");
        check(t.size() == 1, "size must not change");
    }

    static void testAbsentKeyReturnsFalse() {
        Hashtable<String, Integer> t = new Hashtable<>();
        t.put("one", 1);
        check(!t.remove("missing", 1), "remove should return false for absent key");
        check(t.size() == 1, "size must not change");
    }

    static void testRemoveTwiceReturnsFalseSecondTime() {
        Hashtable<String, Integer> t = new Hashtable<>();
        t.put("a", 10);
        check(t.remove("a", 10), "first remove should succeed");
        check(!t.remove("a", 10), "second remove should return false");
        check(t.isEmpty(), "table should be empty");
    }

    static void testCollidingKeysRemoveOnlyMatchingEntry() {
        Hashtable<CollidingKey, String> t = new Hashtable<>();
        CollidingKey k1 = new CollidingKey("k1");
        CollidingKey k2 = new CollidingKey("k2");
        t.put(k1, "v1");
        t.put(k2, "v2");
        check(t.remove(k2, "v2"), "remove should succeed for colliding key");
        check(t.get(k1) != null && t.get(k1).equals("v1"), "other colliding entry must remain");
        check(t.get(k2) == null, "removed colliding entry must be gone");
        check(t.size() == 1, "size should be 1");
    }

    static void testNullValueThrowsNullPointerException() {
        Hashtable<String, Integer> t = new Hashtable<>();
        t.put("a", 1);
        try {
            t.remove("a", null);
            throw new RuntimeException("expected NullPointerException for null value");
        } catch (NullPointerException expected) {
            // expected
        }
        check(t.size() == 1, "table must not change after failed remove");
    }

    static void testNullKeyThrowsNullPointerException() {
        Hashtable<String, Integer> t = new Hashtable<>();
        t.put("a", 1);
        try {
            t.remove(null, 1);
            throw new RuntimeException("expected NullPointerException for null key");
        } catch (NullPointerException expected) {
            // expected
        }
        check(t.size() == 1, "table must not change after failed remove");
    }

    public static void main(String[] args) {
        testRemoveExistingPair();
        testValueMismatchReturnsFalse();
        testAbsentKeyReturnsFalse();
        testRemoveTwiceReturnsFalseSecondTime();
        testCollidingKeysRemoveOnlyMatchingEntry();
        testNullValueThrowsNullPointerException();
        testNullKeyThrowsNullPointerException();
    }
}
