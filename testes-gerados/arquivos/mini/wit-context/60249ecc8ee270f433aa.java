/*
 * @test
 * @summary Test ConcurrentSkipListMap.lowerKey(K) for null key and normal keys
 * @run main ConcurrentSkipListMapWitupTest
 */

import java.util.concurrent.ConcurrentSkipListMap;

public class ConcurrentSkipListMapWitupTest {
    public static void main(String[] args) {
        ConcurrentSkipListMap<String, Integer> map = new ConcurrentSkipListMap<>();
        map.put("a", 1);
        map.put("b", 2);
        map.put("c", 3);

        // Test lowerKey with a key that exists
        String lower = map.lowerKey("b");
        if (!"a".equals(lower)) {
            throw new AssertionError("Expected lowerKey(\"b\") to be \"a\" but got " + lower);
        }

        // Test lowerKey with a key that does not exist but is between keys
        lower = map.lowerKey("bb");
        if (!"b".equals(lower)) {
            throw new AssertionError("Expected lowerKey(\"bb\") to be \"b\" but got " + lower);
        }

        // Test lowerKey with a key smaller than any key
        lower = map.lowerKey("0");
        if (lower != null) {
            throw new AssertionError("Expected lowerKey(\"0\") to be null but got " + lower);
        }

        // Test lowerKey with null key - expect NullPointerException
        try {
            map.lowerKey(null);
            throw new AssertionError("Expected NullPointerException for lowerKey(null) but none thrown");
        } catch (NullPointerException expected) {
            // expected
        }
    }
}
