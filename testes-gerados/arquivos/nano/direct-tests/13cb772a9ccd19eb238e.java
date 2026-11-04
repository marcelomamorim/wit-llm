/* @test    @summary Test the next() method of IdentityHashMap for correct key retrieval.    @run main IdentityHashMapWitupTest */

import java.util.IdentityHashMap;

public class IdentityHashMapWitupTest {

    public static void main(String[] args) {
        testNextReturnsCorrectKey();
    }

    public static void testNextReturnsCorrectKey() {
        IdentityHashMap<String, String> map = new IdentityHashMap<>();
        String key1 = "key1";
        String key2 = "key2";
        map.put(key1, "value1");
        map.put(key2, "value2");
        // Simulate traversal to get next key
        Object[] keys = map.keySet().toArray();
        if (keys.length == 0) {
            return; // nothing to test
        }
        Object currentKey = keys[0];
        // Use reflection to invoke next() method
        try {
            java.lang.reflect.Method nextMethod = IdentityHashMap.class.getDeclaredMethod("next");
            nextMethod.setAccessible(true);
            Object result = nextMethod.invoke(map);
            // The result should be one of the keys in the map
            boolean containsKey = false;
            for (Object k : keys) {
                if (k.equals(result)) {
                    containsKey = true;
                    break;
                }
            }
            if (!containsKey) {
                throw new AssertionError("next() returned a key not in the map: " + result);
            }
        } catch (NoSuchMethodException | IllegalAccessException | java.lang.reflect.InvocationTargetException e) {
            throw new RuntimeException("Reflection failed to invoke next()", e);
        }
    }
}
