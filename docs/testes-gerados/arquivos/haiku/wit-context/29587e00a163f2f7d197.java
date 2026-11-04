/*
 * @test
 * @summary Verifies that the values view add(V) of ConcurrentHashMap always throws UnsupportedOperationException and leaves the map unchanged
 * @run main ConcurrentHashMapWitupTest
 */

import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;

public class ConcurrentHashMapWitupTest {

    public static void main(String[] args) {
        ConcurrentHashMap<String, Integer> empty = new ConcurrentHashMap<>();
        expectUnsupported(empty.values(), 1, "empty map");
        if (empty.size() != 0) {
            throw new RuntimeException("empty map changed after rejected add");
        }

        ConcurrentHashMap<String, Integer> map = new ConcurrentHashMap<>();
        map.put("a", 1);
        map.put("b", 2);
        Collection<Integer> values = map.values();
        expectUnsupported(values, 3, "non-empty map");
        expectUnsupported(values, 1, "existing value");

        if (map.size() != 2) {
            throw new RuntimeException("size changed: " + map.size());
        }
        if (map.containsValue(3)) {
            throw new RuntimeException("rejected value was added");
        }
        if (map.get("a") != 1 || map.get("b") != 2) {
            throw new RuntimeException("existing mappings modified");
        }
    }

    static void expectUnsupported(Collection<Integer> c, Integer e, String label) {
        try {
            c.add(e);
        } catch (UnsupportedOperationException ex) {
            return;
        }
        throw new RuntimeException("expected UnsupportedOperationException: " + label);
    }
}
