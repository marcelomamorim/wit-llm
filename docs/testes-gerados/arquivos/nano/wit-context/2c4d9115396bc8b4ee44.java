/*
 * @test
 * @summary Test for IdentityHashMap.next() method behavior
 * @run main IdentityHashMapWitupTest
 */

import java.util.IdentityHashMap;

public class IdentityHashMapWitupTest {

    public static void main(String[] args) {
        testNextMethodNormal();
        testNextMethodWithConcurrentModification();
    }

    private static void testNextMethodNormal() {
        IdentityHashMap<String, String> map = new IdentityHashMap<>();
        String key1 = new String("key1");
        String key2 = new String("key2");
        map.put(key1, "value1");
        map.put(key2, "value2");
        IdentityHashMap.ValueIterator iterator = map.new ValueIterator();
        try {
            while (iterator.hasNext()) {
                Object value = iterator.next();
                // Just iterate without exception
            }
        } catch (Exception e) {
            // Fail if any exception occurs
            throw new RuntimeException("Unexpected exception during normal iteration", e);
        }
    }

    private static void testNextMethodWithConcurrentModification() {
        IdentityHashMap<String, String> map = new IdentityHashMap<>();
        String key1 = new String("key1");
        String key2 = new String("key2");
        map.put(key1, "value1");
        map.put(key2, "value2");
        IdentityHashMap.ValueIterator iterator = map.new ValueIterator();
        try {
            if (iterator.hasNext()) {
                iterator.next();
                // Modify the map to trigger ConcurrentModificationException
                map.put("newKey", "newValue");
                iterator.next();
                // If no exception, fail the test
                throw new RuntimeException("Expected ConcurrentModificationException was not thrown");
            }
        } catch (java.util.ConcurrentModificationException e) {
            // Expected exception, test passes
        } catch (Exception e) {
            throw new RuntimeException("Unexpected exception during concurrent modification test", e);
        }
    }
}
