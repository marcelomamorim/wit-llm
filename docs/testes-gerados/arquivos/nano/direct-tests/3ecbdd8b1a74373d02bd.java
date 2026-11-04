/*
 * @test    @summary Test ConcurrentHashMap.put method for basic functionality
 * @run main ConcurrentHashMapWitupTest
 */

import java.util.concurrent.ConcurrentHashMap;

public class ConcurrentHashMapWitupTest {

    public static void main(String[] args) {
        testPutBasic();
        testPutNullKey();
        testPutNullValue();
        testOverwriteValue();
    }

    static void testPutBasic() {
        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
        String prev = map.put("key1", "value1");
        assert prev == null : "Expected null for new key";
        String result = map.get("key1");
        assert "value1".equals(result) : "Expected value1 for key1";
    }

    static void testPutNullKey() {
        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
        try {
            map.put(null, "value");
            assert false : "Expected NullPointerException for null key";
        } catch (NullPointerException e) {
            // expected
        }
    }

    static void testPutNullValue() {
        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
        String prev = map.put("key2", null);
        assert prev == null : "Expected null for previous value";
        String result = map.get("key2");
        assert result == null : "Expected null value for key2";
    }

    static void testOverwriteValue() {
        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
        map.put("key3", "value3");
        String prev = map.put("key3", "value4");
        assert "value3".equals(prev) : "Expected previous value to be value3";
        String result = map.get("key3");
        assert "value4".equals(result) : "Expected updated value4";
    }
}
