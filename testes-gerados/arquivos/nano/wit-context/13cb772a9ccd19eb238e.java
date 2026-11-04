/*
 * @test
 * @summary Test for IdentityHashMap.next() method, focusing on normal behavior and exception handling.
 * @run main IdentityHashMapWitupTest
 */

import java.util.IdentityHashMap;

public class IdentityHashMapWitupTest {

    public static void main(String[] args) {
        testNextNormal();
        testNextWithConcurrentModification();
    }

    private static void testNextNormal() {
        IdentityHashMap<String, String> map = new IdentityHashMap<>();
        String key1 = "key1";
        String key2 = "key2";
        map.put(key1, "value1");
        map.put(key2, "value2");
        IdentityHashMap.KeyIterator<String, String> iterator = map.new KeyIterator();
        try {
            while (iterator.hasNext()) {
                String key = iterator.next();
                // No specific assertion, just ensure no exception occurs
            }
        } catch (Exception e) {
            throw new RuntimeException("Unexpected exception in normal next() iteration", e);
        }
    }

    private static void testNextWithConcurrentModification() {
        IdentityHashMap<String, String> map = new IdentityHashMap<>();
        String key1 = "key1";
        String key2 = "key2";
        map.put(key1, "value1");
        map.put(key2, "value2");
        IdentityHashMap.KeyIterator<String, String> iterator = map.new KeyIterator();
        try {
            if (iterator.hasNext()) {
                map.put("newKey", "newValue");
                iterator.next();
                // Expecting ConcurrentModificationException
                throw new AssertionError("Expected ConcurrentModificationException was not thrown");
            }
        } catch (java.util.ConcurrentModificationException e) {
            // Expected exception, test passes
        } catch (Exception e) {
            throw new RuntimeException("Unexpected exception in concurrent modification test", e);
        }
    }
}
