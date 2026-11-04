/*
 * @test
 * @summary Behavioral tests for ConcurrentSkipListMap.get(Object key)
 * @run main ConcurrentSkipListMapWitupTest
 */

import java.util.Comparator;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentSkipListMap;

public class ConcurrentSkipListMapWitupTest {

    public static void main(String[] args) {
        testGetOnEmptyMapReturnsNull();
        testGetReturnsPutValue();
        testGetMissingKeyReturnsNull();
        testGetReturnsLatestValueAfterOverwrite();
        testGetAfterRemoveReturnsNull();
        testGetWithComparatorUsesComparatorEquality();
        testGetWithSortedMapCopyConstructor();
    }

    static void testGetOnEmptyMapReturnsNull() {
        ConcurrentSkipListMap<String, Integer> m = new ConcurrentSkipListMap<>();
        check(m.get("a") == null, "get on empty map should return null");
    }

    static void testGetReturnsPutValue() {
        ConcurrentSkipListMap<String, Integer> m = new ConcurrentSkipListMap<>();
        m.put("a", 1);
        check(Integer.valueOf(1).equals(m.get("a")), "get should return the value stored by put");
    }

    static void testGetMissingKeyReturnsNull() {
        ConcurrentSkipListMap<String, Integer> m = new ConcurrentSkipListMap<>();
        m.put("a", 1);
        m.put("c", 3);
        check(m.get("b") == null, "get for absent key should return null");
    }

    static void testGetReturnsLatestValueAfterOverwrite() {
        ConcurrentSkipListMap<String, Integer> m = new ConcurrentSkipListMap<>();
        m.put("a", 1);
        m.put("a", 2);
        check(Integer.valueOf(2).equals(m.get("a")), "get should return the most recent value");
    }

    static void testGetAfterRemoveReturnsNull() {
        ConcurrentSkipListMap<String, Integer> m = new ConcurrentSkipListMap<>();
        m.put("a", 1);
        m.remove("a");
        check(m.get("a") == null, "get after remove should return null");
    }

    static void testGetWithComparatorUsesComparatorEquality() {
        ConcurrentSkipListMap<String, Integer> m =
                new ConcurrentSkipListMap<>(String.CASE_INSENSITIVE_ORDER);
        m.put("Key", 42);
        check(Integer.valueOf(42).equals(m.get("KEY")),
              "get should locate keys by comparator equality");
        check(Integer.valueOf(42).equals(m.get("key")),
              "get should locate keys by comparator equality (lower case)");
    }

    static void testGetWithSortedMapCopyConstructor() {
        TreeMap<String, Integer> tm = new TreeMap<>();
        tm.put("k", 5);
        tm.put("j", 4);
        ConcurrentSkipListMap<String, Integer> m = new ConcurrentSkipListMap<>(tm);
        check(Integer.valueOf(5).equals(m.get("k")), "get should return copied value for k");
        check(Integer.valueOf(4).equals(m.get("j")), "get should return copied value for j");
        check(m.get("z") == null, "get for key not copied should return null");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
