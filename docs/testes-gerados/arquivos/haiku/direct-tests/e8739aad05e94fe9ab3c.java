/*
 * @test
 * @summary Verifies that AbstractMap.put throws UnsupportedOperationException by default and leaves the map unchanged
 * @run main AbstractMapWitupTest
 */

import java.util.AbstractMap;
import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class AbstractMapWitupTest {

    static class EmptyMap extends AbstractMap<String, String> {
        @Override
        public Set<Map.Entry<String, String>> entrySet() {
            return Collections.emptySet();
        }
    }

    static class FixedMap extends AbstractMap<String, String> {
        private final Set<Map.Entry<String, String>> entries = new HashSet<>();

        FixedMap() {
            entries.add(new AbstractMap.SimpleEntry<>("a", "1"));
        }

        @Override
        public Set<Map.Entry<String, String>> entrySet() {
            return entries;
        }
    }

    static void expectUnsupported(Map<String, String> map, String key, String value) {
        try {
            map.put(key, value);
        } catch (UnsupportedOperationException e) {
            return;
        }
        throw new RuntimeException("Expected UnsupportedOperationException");
    }

    public static void main(String[] args) {
        expectUnsupported(new EmptyMap(), "k", "v");
        expectUnsupported(new EmptyMap(), null, null);

        FixedMap map = new FixedMap();
        expectUnsupported(map, "b", "2");

        if (map.size() != 1) {
            throw new RuntimeException("size changed after failed put: " + map.size());
        }
        if (!"1".equals(map.get("a"))) {
            throw new RuntimeException("existing mapping changed");
        }
        if (map.containsKey("b")) {
            throw new RuntimeException("key was added despite failed put");
        }
    }
}
