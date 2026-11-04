/*
 * @test
 * @summary Tests observable behavior of Hashtable.contains(Object value)
 * @run main HashtableWitupTest
 */

import java.util.Hashtable;

public class HashtableWitupTest {

    public static void main(String[] args) {
        testEmptyTableReturnsFalse();
        testFoundValueReturnsTrue();
        testMissingValueReturnsFalse();
        testNullValueThrowsNullPointerException();
        testValueMatchUsesEquals();
        testKeyIsNotSearchedAsValue();
        testRemovedValueNotFound();
        testManyEntriesAcrossBuckets();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testEmptyTableReturnsFalse() {
        Hashtable<String, String> h = new Hashtable<>();
        check(!h.contains("x"), "empty table must not contain any value");
    }

    static void testFoundValueReturnsTrue() {
        Hashtable<String, String> h = new Hashtable<>();
        h.put("one", "1");
        h.put("two", "2");
        check(h.contains("1"), "existing value '1' must be found");
        check(h.contains("2"), "existing value '2' must be found");
    }

    static void testMissingValueReturnsFalse() {
        Hashtable<String, String> h = new Hashtable<>();
        h.put("one", "1");
        check(!h.contains("3"), "absent value must not be found");
    }

    static void testNullValueThrowsNullPointerException() {
        Hashtable<String, String> h = new Hashtable<>();
        h.put("one", "1");
        boolean thrown = false;
        try {
            h.contains(null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "contains(null) must throw NullPointerException");
    }

    static void testValueMatchUsesEquals() {
        Hashtable<String, String> h = new Hashtable<>();
        h.put("k", new String("abc"));
        check(h.contains(new String("abc")), "equal but distinct value must be found");
    }

    static void testKeyIsNotSearchedAsValue() {
        Hashtable<String, String> h = new Hashtable<>();
        h.put("key", "value");
        check(!h.contains("key"), "contains must check values, not keys");
    }

    static void testRemovedValueNotFound() {
        Hashtable<String, String> h = new Hashtable<>();
        h.put("a", "A");
        check(h.contains("A"), "value must be present before removal");
        h.remove("a");
        check(!h.contains("A"), "value must not be found after removal");
    }

    static void testManyEntriesAcrossBuckets() {
        Hashtable<Integer, String> h = new Hashtable<>();
        for (int i = 0; i < 100; i++) {
            h.put(i, "v" + i);
        }
        check(h.contains("v0"), "first value must be found");
        check(h.contains("v57"), "middle value must be found");
        check(h.contains("v99"), "last value must be found");
        check(!h.contains("v100"), "value outside inserted range must not be found");
    }
}
