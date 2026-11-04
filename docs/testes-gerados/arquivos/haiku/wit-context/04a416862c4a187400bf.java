/*
 * @test
 * @summary Witup-derived checks for ConcurrentHashMap.contains(Object): null value must throw NullPointerException and lookups must reflect the current map contents
 * @run main ConcurrentHashMapWitupTest
 */

import java.util.concurrent.ConcurrentHashMap;

public class ConcurrentHashMapWitupTest {

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testNullValueThrowsNPE() {
        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
        map.put("k", "v");
        boolean thrown = false;
        try {
            map.contains(null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "contains(null) should throw NullPointerException");
    }

    static void testNullValueThrowsNPEOnEmptyMap() {
        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
        boolean thrown = false;
        try {
            map.contains(null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "contains(null) on empty map should throw NullPointerException");
    }

    static void testPresentValue() {
        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
        map.put("a", "one");
        map.put("b", "two");
        check(map.contains("one"), "contains should return true for present value");
        check(map.contains("two"), "contains should return true for present value");
    }

    static void testAbsentValue() {
        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
        map.put("a", "one");
        check(!map.contains("three"), "contains should return false for absent value");
        check(!new ConcurrentHashMap<String, String>().contains("one"),
              "contains should return false on empty map");
    }

    static void testValueAfterRemove() {
        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
        map.put("a", "one");
        map.remove("a");
        check(!map.contains("one"), "contains should return false after the value is removed");
    }

    static void testKeyIsNotValue() {
        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
        map.put("key", "value");
        check(!map.contains("key"), "contains should check values, not keys");
    }

    public static void main(String[] args) {
        testNullValueThrowsNPE();
        testNullValueThrowsNPEOnEmptyMap();
        testPresentValue();
        testAbsentValue();
        testValueAfterRemove();
        testKeyIsNotValue();
    }
}
