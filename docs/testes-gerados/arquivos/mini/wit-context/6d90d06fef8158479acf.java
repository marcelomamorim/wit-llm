/*
 * @test
 * @summary Test java.util.Collections.CheckedMap.CheckedEntrySet.retainAll(Collection<?>) for null argument and normal behavior
 * @run main CollectionsWitupTest
 */

import java.util.*;

public class CollectionsWitupTest {
    public static void main(String[] args) {
        testRetainAllNullArgument();
        testRetainAllNormalBehavior();
    }

    private static void testRetainAllNullArgument() {
        // Create a CheckedMap.CheckedEntrySet with a backing map and test retainAll(null) throws NullPointerException
        Map<String, String> map = new HashMap<>();
        map.put("key1", "value1");
        map.put("key2", "value2");

        // Wrap the map with Collections.checkedMap to get a CheckedMap
        Map<String, String> checkedMap = Collections.checkedMap(map, String.class, String.class);

        // Get the entrySet, which is a CheckedEntrySet
        Set<Map.Entry<String, String>> checkedEntrySet = checkedMap.entrySet();

        try {
            checkedEntrySet.retainAll(null);
            throw new RuntimeException("Expected NullPointerException not thrown");
        } catch (NullPointerException expected) {
            // Expected exception
        }
    }

    private static void testRetainAllNormalBehavior() {
        Map<String, String> map = new HashMap<>();
        map.put("a", "1");
        map.put("b", "2");
        map.put("c", "3");

        Map<String, String> checkedMap = Collections.checkedMap(map, String.class, String.class);
        Set<Map.Entry<String, String>> checkedEntrySet = checkedMap.entrySet();

        // Create a collection with entries to retain
        Set<Map.Entry<String, String>> toRetain = new HashSet<>();
        for (Map.Entry<String, String> e : checkedEntrySet) {
            if ("a".equals(e.getKey()) || "c".equals(e.getKey())) {
                toRetain.add(e);
            }
        }

        boolean changed = checkedEntrySet.retainAll(toRetain);

        if (!changed) {
            throw new RuntimeException("Expected retainAll to modify the set");
        }

        if (checkedEntrySet.size() != 2) {
            throw new RuntimeException("Expected size 2 after retainAll, got " + checkedEntrySet.size());
        }

        for (Map.Entry<String, String> e : checkedEntrySet) {
            if (!"a".equals(e.getKey()) && !"c".equals(e.getKey())) {
                throw new RuntimeException("Unexpected entry after retainAll: " + e);
            }
        }

        // Also verify the backing map is updated
        if (checkedMap.size() != 2) {
            throw new RuntimeException("Expected backing map size 2 after retainAll, got " + checkedMap.size());
        }

        if (checkedMap.containsKey("b")) {
            throw new RuntimeException("Backing map should not contain key 'b' after retainAll");
        }
    }
}
