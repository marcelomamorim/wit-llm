/*
 * @test
 * @summary Basic observable behavior of ConcurrentSkipListMap.put, including null value rejection
 * @run main ConcurrentSkipListMapWitupTest
 */

import java.util.Comparator;
import java.util.concurrent.ConcurrentSkipListMap;

public class ConcurrentSkipListMapWitupTest {

    public static void main(String[] args) {
        testNullValueThrowsAndMapUnchanged();
        testNewKeyReturnsNull();
        testExistingKeyReturnsPreviousValue();
        testPutThenGetReturnsValue();
        testComparatorConstructorOrdering();
    }

    static void testNullValueThrowsAndMapUnchanged() {
        ConcurrentSkipListMap<String, Integer> m = new ConcurrentSkipListMap<>();
        boolean thrown = false;
        try {
            m.put("a", null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "put with null value must throw NullPointerException");
        check(m.isEmpty(), "map must remain empty after rejected put");
        check(!m.containsKey("a"), "key must not be inserted when value is null");
    }

    static void testNewKeyReturnsNull() {
        ConcurrentSkipListMap<String, Integer> m = new ConcurrentSkipListMap<>();
        Integer prev = m.put("a", 1);
        check(prev == null, "put of new key must return null");
        check(m.size() == 1, "size must be 1 after one insertion");
    }

    static void testExistingKeyReturnsPreviousValue() {
        ConcurrentSkipListMap<String, Integer> m = new ConcurrentSkipListMap<>();
        m.put("k", 10);
        Integer prev = m.put("k", 20);
        check(prev != null && prev == 10, "put of existing key must return previous value");
        check(m.size() == 1, "replacing a value must not change size");
        check(m.get("k") == 20, "get must return the latest value");
    }

    static void testPutThenGetReturnsValue() {
        ConcurrentSkipListMap<Integer, String> m = new ConcurrentSkipListMap<>();
        for (int i = 0; i < 100; i++) {
            m.put(i, "v" + i);
        }
        check(m.size() == 100, "size must match number of distinct keys");
        for (int i = 0; i < 100; i++) {
            check(("v" + i).equals(m.get(i)), "get must return value stored by put for key " + i);
        }
    }

    static void testComparatorConstructorOrdering() {
        ConcurrentSkipListMap<String, Integer> m =
            new ConcurrentSkipListMap<>(Comparator.reverseOrder());
        m.put("a", 1);
        m.put("b", 2);
        m.put("c", 3);
        check("c".equals(m.firstKey()), "reverse comparator must order largest key first");
        check("a".equals(m.lastKey()), "reverse comparator must order smallest key last");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
