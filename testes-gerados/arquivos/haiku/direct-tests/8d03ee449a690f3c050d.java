/*
 * @test
 * @summary ConcurrentHashMap values view addAll always throws UnsupportedOperationException and leaves the map unchanged
 * @run main ConcurrentHashMapWitupTest
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;

public class ConcurrentHashMapWitupTest {

    public static void main(String[] args) {
        ConcurrentHashMap<String, Integer> map = new ConcurrentHashMap<>();
        map.put("a", 1);
        Collection<Integer> values = map.values();

        expectUnsupported(values, Arrays.asList(2, 3));
        expectUnsupported(values, new ArrayList<Integer>());
        expectUnsupported(values, null);

        if (map.size() != 1) {
            throw new RuntimeException("map size changed: " + map.size());
        }
        if (!Integer.valueOf(1).equals(map.get("a"))) {
            throw new RuntimeException("map value changed: " + map.get("a"));
        }
        if (map.containsValue(2) || map.containsValue(3)) {
            throw new RuntimeException("unexpected value added to map");
        }
    }

    static void expectUnsupported(Collection<Integer> c, Collection<? extends Integer> arg) {
        try {
            c.addAll(arg);
        } catch (UnsupportedOperationException e) {
            return;
        }
        throw new RuntimeException("expected UnsupportedOperationException");
    }
}
