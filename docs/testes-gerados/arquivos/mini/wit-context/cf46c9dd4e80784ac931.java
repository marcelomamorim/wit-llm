/*
 * @test
 * @summary Test ConcurrentHashMap.Node.setValue throws UnsupportedOperationException
 * @run main ConcurrentHashMapWitupTest
 */

import java.util.concurrent.ConcurrentHashMap;

public class ConcurrentHashMapWitupTest {
    public static void main(String[] args) {
        // Create a ConcurrentHashMap instance
        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
        map.put("key", "value");

        // Access the Node via entrySet iterator
        java.util.Map.Entry<String, String> entry = map.entrySet().iterator().next();

        // The entry is a ConcurrentHashMap.Node instance
        // Test that setValue throws UnsupportedOperationException
        try {
            entry.setValue("newValue");
            throw new AssertionError("Expected UnsupportedOperationException not thrown");
        } catch (UnsupportedOperationException expected) {
            // Expected exception
        }
    }
}
