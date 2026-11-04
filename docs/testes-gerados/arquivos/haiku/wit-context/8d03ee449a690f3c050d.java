/*
 * @test
 * @summary ConcurrentHashMap values().addAll must always throw UnsupportedOperationException and leave the map unchanged
 * @run main ConcurrentHashMapWitupTest
 */
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.concurrent.ConcurrentHashMap;

public class ConcurrentHashMapWitupTest {
    public static void main(String[] args) {
        ConcurrentHashMap<String, Integer> map = new ConcurrentHashMap<>();
        map.put("a", 1);
        Collection<Integer> values = map.values();

        expectUnsupported(values, Arrays.asList(2, 3));
        expectUnsupported(values, Collections.<Integer>emptyList());
        expectUnsupported(values, new ArrayList<Integer>());

        if (map.size() != 1) {
            throw new RuntimeException("map size changed: " + map.size());
        }
        if (map.get("a") != 1) {
            throw new RuntimeException("map value changed: " + map.get("a"));
        }
        if (map.containsValue(2) || map.containsValue(3)) {
            throw new RuntimeException("rejected values were added");
        }
    }

    static void expectUnsupported(Collection<Integer> target, Collection<? extends Integer> c) {
        try {
            target.addAll(c);
        } catch (UnsupportedOperationException e) {
            return;
        }
        throw new RuntimeException("expected UnsupportedOperationException");
    }
}
