/*
 * @test
 * @summary Test ConcurrentHashMap.Entry.setValue throws UnsupportedOperationException
 * @run main ConcurrentHashMapWitupTest
 */

import java.util.concurrent.ConcurrentHashMap;

public class ConcurrentHashMapWitupTest {
    public static void main(String[] args) {
        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
        map.put("key", "value");
        ConcurrentHashMap.Entry<String, String> entry = map.entrySet().iterator().next();
        try {
            entry.setValue("newValue");
            throw new Error("Expected UnsupportedOperationException");
        } catch (UnsupportedOperationException expected) {
            // Test passed
        }
    }
}
