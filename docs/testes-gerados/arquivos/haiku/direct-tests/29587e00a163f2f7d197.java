/*
 * @test
 * @summary ConcurrentHashMap values() view must reject add(V) with UnsupportedOperationException and leave the map unchanged
 * @run main ConcurrentHashMapWitupTest
 */

import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;

public class ConcurrentHashMapWitupTest {

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void expectUnsupported(Collection<Integer> values, Integer element, String label) {
        boolean thrown = false;
        try {
            values.add(element);
        } catch (UnsupportedOperationException e) {
            thrown = true;
        }
        check(thrown, label + ": expected UnsupportedOperationException");
    }

    public static void main(String[] args) {
        ConcurrentHashMap<String, Integer> m = new ConcurrentHashMap<>();
        m.put("a", 1);
        Collection<Integer> values = m.values();

        expectUnsupported(values, 2, "non-empty map add(2)");
        check(m.size() == 1, "size changed after rejected add");
        check(m.containsValue(1), "existing value lost after rejected add");
        check(!m.containsValue(2), "rejected value was inserted");
        check(m.get("a") == 1, "mapping for key a changed");

        expectUnsupported(values, null, "non-empty map add(null)");
        check(m.size() == 1, "size changed after rejected null add");

        ConcurrentHashMap<String, Integer> empty = new ConcurrentHashMap<>();
        Collection<Integer> emptyValues = empty.values();
        expectUnsupported(emptyValues, 3, "empty map add(3)");
        check(empty.isEmpty(), "empty map modified by rejected add");
    }
}
