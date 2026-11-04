/* @test    @summary Test HashMap.next() method for correct key retrieval and exception handling    @run main HashMapWitupTest */

import java.util.HashMap;

public class HashMapWitupTest {

    public static void main(String[] args) {
        testNextReturnsCorrectKey();
        testNextWithoutNextNode();
    }

    private static void testNextReturnsCorrectKey() {
        HashMap<String, Integer> map = new HashMap<>();
        map.put("a", 1);
        map.put("b", 2);
        map.put("c", 3);
        HashMap.Node<String, Integer> node = getFirstNode(map);
        if (node != null) {
            HashMap<String, Integer> mapForNext = new HashMap<>();
            mapForNext.put(node.key, node.value);
            try {
                HashMap<String, Integer> tempMap = new HashMap<>();
                tempMap.put(node.key, node.value);
                // Simulate calling next() on a node
                String key = map.next();
                assert key != null : "next() returned null";
            } catch (Exception e) {
                throw new AssertionError("Exception during next() call: " + e);
            }
        }
    }

    private static void testNextWithoutNextNode() {
        HashMap<String, Integer> map = new HashMap<>();
        try {
            // Call next() on an empty map to simulate no next node
            map.next();
            throw new AssertionError("Expected exception not thrown when calling next() on empty map");
        } catch (NullPointerException e) {
            // Expected exception
        } catch (Exception e) {
            // Other exceptions are not expected
            throw new AssertionError("Unexpected exception: " + e);
        }
    }

    private static <K, V> HashMap.Node<K, V> getFirstNode(HashMap<K, V> map) {
        try {
            java.lang.reflect.Field tableField = HashMap.class.getDeclaredField("table");
            tableField.setAccessible(true);
            Object[] table = (Object[]) tableField.get(map);
            if (table != null && table.length > 0) {
                Object node = table[0];
                if (node instanceof HashMap.Node) {
                    return (HashMap.Node<K, V>) node;
                }
            }
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new AssertionError("Reflection error: " + e);
        }
        return null;
    }
}
