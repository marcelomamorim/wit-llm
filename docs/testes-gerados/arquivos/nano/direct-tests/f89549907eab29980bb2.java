/*
 * @test    @summary Test ConcurrentSkipListMap.put method for basic functionality
 * @run main ConcurrentSkipListMapWitupTest
 */

import java.util.concurrent.ConcurrentSkipListMap;

public class ConcurrentSkipListMapWitupTest {

    public static void main(String[] args) {
        testPutBasic();
        testPutNullKey();
        testPutNullValue();
        testOverwriteValue();
    }

    static void testPutBasic() {
        ConcurrentSkipListMap<String, Integer> map = new ConcurrentSkipListMap<>();
        Integer prev = map.put("a", 1);
        assert prev == null : "Expected null for new key";
        Integer value = map.get("a");
        assert value != null && value == 1 : "Value for key 'a' should be 1";
    }

    static void testPutNullKey() {
        ConcurrentSkipListMap<String, Integer> map = new ConcurrentSkipListMap<>();
        try {
            map.put(null, 1);
            assert false : "Expected NullPointerException for null key";
        } catch (NullPointerException e) {
            // expected
        }
    }

    static void testPutNullValue() {
        ConcurrentSkipListMap<String, String> map = new ConcurrentSkipListMap<>();
        String prev = map.put("b", null);
        assert prev == null : "Expected null for new key";
        String value = map.get("b");
        assert value == null : "Value for key 'b' should be null";
    }

    static void testOverwriteValue() {
        ConcurrentSkipListMap<String, Integer> map = new ConcurrentSkipListMap<>();
        map.put("c", 2);
        Integer prev = map.put("c", 3);
        assert prev != null && prev == 2 : "Previous value should be 2";
        Integer newVal = map.get("c");
        assert newVal != null && newVal == 3 : "New value for 'c' should be 3";
    }
}
