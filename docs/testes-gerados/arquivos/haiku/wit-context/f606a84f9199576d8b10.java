/*
 * @test
 * @summary Verifies that the iterator remove() delegation in AbstractMap keySet() throws UnsupportedOperationException when the entry iterator does not support removal and removes the mapping when it does.
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

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static class TestMap extends AbstractMap<String, String> {
        private final List<Map.Entry<String, String>> entries = new ArrayList<>();
        private final boolean removable;

        TestMap(boolean removable) {
            this.removable = removable;
            entries.add(new AbstractMap.SimpleEntry<>("a", "1"));
        }

        public Set<Map.Entry<String, String>> entrySet() {
            return new AbstractSet<Map.Entry<String, String>>() {
                public Iterator<Map.Entry<String, String>> iterator() {
                    if (removable) {
                        return entries.iterator();
                    }
                    final Iterator<Map.Entry<String, String>> it = entries.iterator();
                    return new Iterator<Map.Entry<String, String>>() {
                        public boolean hasNext() {
                            return it.hasNext();
                        }
                        public Map.Entry<String, String> next() {
                            return it.next();
                        }
                    };
                }

                public int size() {
                    return entries.size();
                }
            };
        }
    }

    static void testNonRemovableIteratorThrows() {
        TestMap map = new TestMap(false);
        Iterator<String> ki = map.keySet().iterator();
        check(ki.next().equals("a"), "unexpected key");
        boolean thrown = false;
        try {
            ki.remove();
        } catch (UnsupportedOperationException e) {
            thrown = true;
        }
        check(thrown, "expected UnsupportedOperationException from keySet iterator remove");
        check(map.size() == 1, "mapping must not be removed when removal is unsupported");
    }

    static void testRemovableIteratorDelegates() {
        TestMap map = new TestMap(true);
        Iterator<String> ki = map.keySet().iterator();
        check(ki.next().equals("a"), "unexpected key");
        ki.remove();
        check(map.size() == 0, "size should be 0 after delegated remove");
        check(map.isEmpty(), "map should be empty after delegated remove");
    }

    public static void main(String[] args) {
        testNonRemovableIteratorThrows();
        testRemovableIteratorDelegates();
    }
}
