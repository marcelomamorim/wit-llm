/* @test
   @summary Test the remove(Object key, Object value) method of ConcurrentHashMap.
   @run main ConcurrentHashMapWitupTest_Pb33a6c375e7b
 */
import java.util.concurrent.ConcurrentHashMap;
public class ConcurrentHashMapWitupTest_Pb33a6c375e7b {
    public static void main(String[] args) {
        ConcurrentHashMap<Object, Object> map = new ConcurrentHashMap<>();
        Object key = "testKey";
        Object value = "testValue";
        map.put(key, value);
        boolean removed = map.remove(key, value);
        if (!removed) {
            throw new AssertionError("Expected removal to succeed");
        }
        if (map.containsKey(key)) {
            throw new AssertionError("Map should not contain key after removal");
        }
        boolean removeNonExisting = map.remove("nonExistingKey", "value");
        if (removeNonExisting) {
            throw new AssertionError("Removal of non-existing key should return false");
        }
        map.put(null, "value");
        // Expect NullPointerException for null key
        try {
            map.remove(null, "value");
            throw new AssertionError("Expected NullPointerException for null key");
        } catch (NullPointerException e) {
            // expected
        }
        // Test with null value
        map.put("anotherKey", "anotherValue");
        boolean result = map.remove("anotherKey", null);
        if (result) {
            throw new AssertionError("Removal with null value should return false");
        }
    }
}
