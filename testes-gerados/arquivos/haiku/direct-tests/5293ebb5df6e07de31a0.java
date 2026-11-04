/*
 * @test
 * @summary Verifies observable behavior of ConcurrentHashMap.putIfAbsent for absent, present and removed keys
 * @run main ConcurrentHashMapWitupTest
 */

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ConcurrentHashMapWitupTest {

    public static void main(String[] args) {
        testAbsentKeyReturnsNullAndInserts();
        testPresentKeyReturnsExistingAndKeepsValue();
        testPresentKeyDoesNotChangeSize();
        testReinsertAfterRemove();
        testWithInitialCapacityConstructor();
        testWithCopyConstructor();
        testDistinctKeysAreIndependent();
    }

    static void testAbsentKeyReturnsNullAndInserts() {
        ConcurrentHashMap<Integer, String> map = new ConcurrentHashMap<>();
        String previous = map.putIfAbsent(1, "a");
        check(previous == null, "expected null for absent key");
        check("a".equals(map.get(1)), "expected value stored for absent key");
        check(map.size() == 1, "expected size 1");
    }

    static void testPresentKeyReturnsExistingAndKeepsValue() {
        ConcurrentHashMap<Integer, String> map = new ConcurrentHashMap<>();
        map.put(1, "first");
        String previous = map.putIfAbsent(1, "second");
        check("first".equals(previous), "expected existing value returned");
        check("first".equals(map.get(1)), "expected original value kept");
    }

    static void testPresentKeyDoesNotChangeSize() {
        ConcurrentHashMap<Integer, String> map = new ConcurrentHashMap<>();
        map.put(1, "x");
        map.putIfAbsent(1, "y");
        check(map.size() == 1, "size must remain 1");
    }

    static void testReinsertAfterRemove() {
        ConcurrentHashMap<Integer, String> map = new ConcurrentHashMap<>();
        map.putIfAbsent(1, "v1");
        map.remove(1);
        String previous = map.putIfAbsent(1, "v2");
        check(previous == null, "expected null after key was removed");
        check("v2".equals(map.get(1)), "expected new value after reinsertion");
    }

    static void testWithInitialCapacityConstructor() {
        ConcurrentHashMap<Integer, String> map = new ConcurrentHashMap<>(16);
        check(map.putIfAbsent(5, "five") == null, "expected null on first insert");
        check("five".equals(map.putIfAbsent(5, "other")), "expected existing value");
    }

    static void testWithCopyConstructor() {
        Map<Integer, String> source = new HashMap<>();
        source.put(2, "two");
        ConcurrentHashMap<Integer, String> map = new ConcurrentHashMap<>(source);
        check("two".equals(map.putIfAbsent(2, "x")), "expected copied value returned");
        check(null == map.putIfAbsent(3, "three"), "expected null for new key");
        check("three".equals(map.get(3)), "expected new key stored");
    }

    static void testDistinctKeysAreIndependent() {
        ConcurrentHashMap<String, Integer> map = new ConcurrentHashMap<>();
        check(map.putIfAbsent("a", 1) == null, "expected null for a");
        check(map.putIfAbsent("b", 2) == null, "expected null for b");
        check(Integer.valueOf(1).equals(map.putIfAbsent("a", 9)), "expected 1 for a");
        check(Integer.valueOf(2).equals(map.get("b")), "expected 2 for b");
        check(map.size() == 2, "expected size 2");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
