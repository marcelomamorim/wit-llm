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
        // Use a concrete subclass of AbstractMap: HashMap
        Map<String, String> map = new HashMap<>();
        map.put("key1", "value1");
        map.put("key2", "value2");

        AbstractMap<String, String> abstractMap = new AbstractMap<String, String>() {
            public Set<Entry<String, String>> entrySet() {
                return map.entrySet();
            }
        };

        Set<Entry<String, String>> entries = abstractMap.entrySet();
        Iterator<Entry<String, String>> i = entries.iterator();

        // Check initial size
        if (abstractMap.size() != 2) {
            throw new RuntimeException("Initial size should be 2");
        }

        // Remove first entry via iterator.remove() and AbstractMap.remove() method
        if (i.hasNext()) {
            Entry<String, String> e = i.next();
            i.remove(); // remove via iterator
        }

        // After removal, size should be 1
        if (abstractMap.size() != 1) {
            throw new RuntimeException("Size after removal should be 1");
        }

        // Remove second entry via iterator.remove() again
        if (i.hasNext()) {
            Entry<String, String> e = i.next();
            i.remove();
        }

        // Now map should be empty
        if (!abstractMap.isEmpty()) {
            throw new RuntimeException("Map should be empty after removing all entries");
        }

        // Iterator should have no more elements
        if (i.hasNext()) {
            throw new RuntimeException("Iterator should have no more elements");
        }
    }
}
