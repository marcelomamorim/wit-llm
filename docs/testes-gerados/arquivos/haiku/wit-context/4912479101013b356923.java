/*
 * @test
 * @summary Witup-derived checks for ConcurrentSkipListMap.containsValue null handling and observable results
 * @run main ConcurrentSkipListMapWitupTest
 */

import java.util.concurrent.ConcurrentSkipListMap;

public class ConcurrentSkipListMapWitupTest {

    public static void main(String[] args) {
        testNullValueThrowsNPE();
        testEmptyMapReturnsFalse();
        testPresentValueReturnsTrue();
        testAbsentValueReturnsFalse();
        testValueAfterRemoveReturnsFalse();
    }

    static void testNullValueThrowsNPE() {
        ConcurrentSkipListMap<String, Integer> map = new ConcurrentSkipListMap<>();
        map.put("a", 1);
        boolean thrown = false;
        try {
            map.containsValue(null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("containsValue(null) did not throw NullPointerException");
        }
    }

    static void testEmptyMapReturnsFalse() {
        ConcurrentSkipListMap<String, Integer> map = new ConcurrentSkipListMap<>();
        if (map.containsValue(Integer.valueOf(1))) {
            throw new RuntimeException("empty map reported a value");
        }
    }

    static void testPresentValueReturnsTrue() {
        ConcurrentSkipListMap<String, Integer> map = new ConcurrentSkipListMap<>();
        map.put("a", 1);
        map.put("b", 2);
        if (!map.containsValue(Integer.valueOf(2))) {
            throw new RuntimeException("present value not found");
        }
    }

    static void testAbsentValueReturnsFalse() {
        ConcurrentSkipListMap<String, Integer> map = new ConcurrentSkipListMap<>();
        map.put("a", 1);
        if (map.containsValue(Integer.valueOf(99))) {
            throw new RuntimeException("absent value reported as present");
        }
    }

    static void testValueAfterRemoveReturnsFalse() {
        ConcurrentSkipListMap<String, Integer> map = new ConcurrentSkipListMap<>();
        map.put("a", 1);
        map.remove("a");
        if (map.containsValue(Integer.valueOf(1))) {
            throw new RuntimeException("removed value still reported as present");
        }
    }
}
