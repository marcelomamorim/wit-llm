/* @test    @summary Test the remove() method of AbstractMap for correct delegation and behavior    @run main AbstractMapWitupTest */

import java.util.*;

public class AbstractMapWitupTest {

    public static void main(String[] args) {
        testRemoveDelegatesToIteratorRemove();
        testRemoveWithoutIterator();
    }

    private static void testRemoveDelegatesToIteratorRemove() {
        // Create a simple map with one entry
        Map<String, String> map = new AbstractMap<String, String>() {
            private final Set<Map.Entry<String, String>> entries = new HashSet<>();

            {
                entries.add(new AbstractMap.SimpleEntry<>("key", "value"));
            }

            @Override
            public Set<Map.Entry<String, String>> entrySet() {
                return entries;
            }
        };

        // Obtain iterator from entrySet
        Iterator<Map.Entry<String, String>> iterator = map.entrySet().iterator();
        // Move to first element
        if (iterator.hasNext()) {
            Map.Entry<String, String> entry = iterator.next();
            // Remove via iterator
            iterator.remove();
        }
        // After removal, map should be empty
        assert map.isEmpty() : "Map should be empty after removal";
    }

    private static void testRemoveWithoutIterator() {
        // Create a subclass that calls remove() directly
        class MyMap extends AbstractMap<String, String> {
            private final Map<String, String> internalMap = new HashMap<>();

            public MyMap() {
                internalMap.put("a", "b");
            }

            @Override
            public Set<Map.Entry<String, String>> entrySet() {
                return internalMap.entrySet();
            }

            public void remove() {
                // Call the remove() method of AbstractMap
                // which delegates to iterator.remove()
                super.remove();
            }
        }
        MyMap myMap = new MyMap();
        // Call remove() method, expecting it to delegate to iterator.remove()
        myMap.remove();
        // Map should be empty after removal
        assert myMap.isEmpty() : "MyMap should be empty after remove()";
    }
}
