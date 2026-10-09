/* @test
   @summary Tests the remove(Object key, Object value) method of ConcurrentSkipListMap.
   @run main ConcurrentSkipListMapWitupTest_Pe01bba25fd34
 */
import java.util.concurrent.ConcurrentSkipListMap;
public class ConcurrentSkipListMapWitupTest_Pe01bba25fd34 {
    public static void main(String[] args) {
        ConcurrentSkipListMap<String, String> map = new ConcurrentSkipListMap<>();
        map.put("key1", "value1");
        map.put("key2", "value2");
        
        // Test removing existing key-value pair
        boolean removed = map.remove("key1", "value1");
        if (!removed) {
            throw new AssertionError("Expected removal of key1-value1 to succeed");
        }
        if (map.containsKey("key1")) {
            throw new AssertionError("Key1 should no longer be in the map");
        }
        
        // Test removing with correct key but different value
        boolean notRemoved = map.remove("key2", "differentValue");
        if (notRemoved) {
            throw new AssertionError("Removal should fail when value does not match");
        }
        if (!map.containsKey("key2")) {
            throw new AssertionError("Key2 should still be in the map");
        }
        
        // Test removing with null key throws NullPointerException
        try {
            map.remove(null, "value");
            throw new AssertionError("Expected NullPointerException for null key");
        } catch (NullPointerException e) {
            // expected
        }
        
        // Test removing with null value returns false and does not remove
        boolean result = map.remove("key2", null);
        if (result) {
            throw new AssertionError("Removal with null value should return false");
        }
        if (!map.containsKey("key2")) {
            throw new AssertionError("Key2 should still be in the map after null value removal attempt");
        }
    }
}
