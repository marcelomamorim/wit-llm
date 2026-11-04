/*
 * @test
 * @summary Test AbstractMap remove() method behavior via entrySet iterator
 * @run main AbstractMapWitupTest
 */

import java.util.AbstractMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.HashMap;

public class AbstractMapWitupTest {

    public static void main(String[] args) {
        testRemoveViaEntrySetIterator();
    }

    static void testRemoveViaEntrySetIterator() {
        // Use a concrete subclass of AbstractMap: HashMap
        Map<String, String> map = new HashMap<>();
        map.put("key1", "value1");
        map.put("key2", "value2");

        AbstractMap<String, String> abstractMap = (AbstractMap<String, String>) map;

        Set<Map.Entry<String, String>> entries = abstractMap.entrySet();
        Iterator<Map.Entry<String, String>> it = entries.iterator();

        // Remove first entry via iterator.remove()
        if (it.hasNext()) {
            it.next();
            it.remove();
        }

        // After removal, size should be 1
        if (abstractMap.size() != 1) {
            throw new RuntimeException("Size after removal should be 1 but was " + abstractMap.size());
        }

        // The removed key should no longer be present
        if (abstractMap.containsKey("key1")) {
            throw new RuntimeException("key1 should have been removed");
        }

        // The other key should still be present
        if (!abstractMap.containsKey("key2")) {
            throw new RuntimeException("key2 should still be present");
        }

        // Remove second entry via iterator.remove() after next()
        if (it.hasNext()) {
            it.next();
            it.remove();
        }

        // Now map should be empty
        if (!abstractMap.isEmpty()) {
            throw new RuntimeException("Map should be empty after removing all entries");
        }
    }
}
