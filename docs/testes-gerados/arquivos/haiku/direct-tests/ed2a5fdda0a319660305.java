/*
 * @test
 * @summary Behavioral checks for ConcurrentSkipListMap.putIfAbsent
 * @run main ConcurrentSkipListMapWitupTest
 */

import java.util.concurrent.ConcurrentSkipListMap;

public class ConcurrentSkipListMapWitupTest {

    public static void main(String[] args) {
        testNullValueRejected();
        testAbsentKeyInsertsAndReturnsNull();
        testPresentKeyReturnsExistingAndKeepsValue();
        testMultipleKeysIndependent();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testNullValueRejected() {
        ConcurrentSkipListMap<String, String> map = new ConcurrentSkipListMap<>();
        boolean thrown = false;
        try {
            map.putIfAbsent("a", null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "putIfAbsent with null value must throw NullPointerException");
        check(!map.containsKey("a"), "map must not contain key after rejected null value");
        check(map.isEmpty(), "map must remain empty after rejected null value");
    }

    static void testAbsentKeyInsertsAndReturnsNull() {
        ConcurrentSkipListMap<String, Integer> map = new ConcurrentSkipListMap<>();
        Integer previous = map.putIfAbsent("k", 1);
        check(previous == null, "putIfAbsent on absent key must return null");
        check(map.containsKey("k"), "key must be present after putIfAbsent");
        check(map.get("k") == 1, "value must be stored after putIfAbsent");
        check(map.size() == 1, "size must be 1");
    }

    static void testPresentKeyReturnsExistingAndKeepsValue() {
        ConcurrentSkipListMap<String, Integer> map = new ConcurrentSkipListMap<>();
        map.put("k", 10);
        Integer previous = map.putIfAbsent("k", 20);
        check(previous == 10, "putIfAbsent on present key must return existing value");
        check(map.get("k") == 10, "existing value must not be overwritten");
        check(map.size() == 1, "size must remain 1");
    }

    static void testMultipleKeysIndependent() {
        ConcurrentSkipListMap<Integer, String> map = new ConcurrentSkipListMap<>();
        check(map.putIfAbsent(2, "two") == null, "first insert of key 2 must return null");
        check(map.putIfAbsent(1, "one") == null, "first insert of key 1 must return null");
        check(map.putIfAbsent(3, "three") == null, "first insert of key 3 must return null");
        check("two".equals(map.putIfAbsent(2, "x")), "existing key 2 must return \"two\"");
        check(map.size() == 3, "size must be 3");
        check("one".equals(map.firstEntry().getValue()), "first entry must be key 1 in sorted order");
        check("three".equals(map.lastEntry().getValue()), "last entry must be key 3 in sorted order");
    }
}
