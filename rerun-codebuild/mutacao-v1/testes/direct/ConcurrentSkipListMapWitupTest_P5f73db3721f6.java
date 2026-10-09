/*
 * @test    @summary Test ConcurrentSkipListMap.put() method for correct behavior
 * @run main ConcurrentSkipListMapWitupTest_P5f73db3721f6
 */

import java.util.concurrent.ConcurrentSkipListMap;

public class ConcurrentSkipListMapWitupTest_P5f73db3721f6 {

    public static void main(String[] args) {
        testPutBasic();
        testPutNullValue();
        testPutOverwrite();
        System.out.println("All tests passed.");
    }

    static void testPutBasic() {
        ConcurrentSkipListMap<String, Integer> map = new ConcurrentSkipListMap<>();
        Integer prev = map.put("a", 1);
        if (prev != null) {
            throw new AssertionError("Expected null for first put, got: " + prev);
        }
        Integer value = map.get("a");
        if (value == null || value != 1) {
            throw new AssertionError("Expected value 1 for key 'a', got: " + value);
        }
    }

    static void testPutNullValue() {
        ConcurrentSkipListMap<String, Integer> map = new ConcurrentSkipListMap<>();
        try {
            map.put("b", null);
            throw new AssertionError("Expected NullPointerException for null value");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    static void testPutOverwrite() {
        ConcurrentSkipListMap<String, Integer> map = new ConcurrentSkipListMap<>();
        map.put("c", 2);
        Integer prev = map.put("c", 3);
        if (prev == null || prev != 2) {
            throw new AssertionError("Expected previous value 2, got: " + prev);
        }
        Integer current = map.get("c");
        if (current == null || current != 3) {
            throw new AssertionError("Expected current value 3, got: " + current);
        }
    }
}
