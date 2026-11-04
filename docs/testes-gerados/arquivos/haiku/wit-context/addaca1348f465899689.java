/*
 * @test
 * @summary AbstractMap keySet/values iterator remove must throw UnsupportedOperationException when the entrySet iterator does not support remove
 * @run main AbstractMapWitupTest
 */

import java.util.AbstractMap;
import java.util.AbstractSet;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class AbstractMapWitupTest {

    static class ReadOnlyEntriesMap extends AbstractMap<String, String> {
        private final List<Map.Entry<String, String>> entries = new ArrayList<Map.Entry<String, String>>();

        ReadOnlyEntriesMap() {
            entries.add(new AbstractMap.SimpleImmutableEntry<String, String>("a", "1"));
            entries.add(new AbstractMap.SimpleImmutableEntry<String, String>("b", "2"));
        }

        @Override
        public Set<Map.Entry<String, String>> entrySet() {
            return new AbstractSet<Map.Entry<String, String>>() {
                @Override
                public Iterator<Map.Entry<String, String>> iterator() {
                    final Iterator<Map.Entry<String, String>> it = entries.iterator();
                    return new Iterator<Map.Entry<String, String>>() {
                        @Override
                        public boolean hasNext() {
                            return it.hasNext();
                        }

                        @Override
                        public Map.Entry<String, String> next() {
                            return it.next();
                        }
                    };
                }

                @Override
                public int size() {
                    return entries.size();
                }
            };
        }
    }

    private static int failures = 0;

    private static void check(boolean condition, String message) {
        if (!condition) {
            failures++;
            System.err.println("FAILED: " + message);
        }
    }

    public static void main(String[] args) {
        ReadOnlyEntriesMap map = new ReadOnlyEntriesMap();

        Iterator<String> keys = map.keySet().iterator();
        keys.next();
        boolean keyRemoveThrew = false;
        try {
            keys.remove();
        } catch (UnsupportedOperationException e) {
            keyRemoveThrew = true;
        }
        check(keyRemoveThrew, "keySet iterator remove() should throw UnsupportedOperationException");

        Iterator<String> values = map.values().iterator();
        values.next();
        boolean valueRemoveThrew = false;
        try {
            values.remove();
        } catch (UnsupportedOperationException e) {
            valueRemoveThrew = true;
        }
        check(valueRemoveThrew, "values iterator remove() should throw UnsupportedOperationException");

        check(map.size() == 2, "map size must remain 2 after rejected removals");
        check(map.containsKey("a") && map.containsKey("b"),
              "map entries must remain after rejected removals");

        if (failures > 0) {
            throw new RuntimeException(failures + " check(s) failed");
        }
    }
}
