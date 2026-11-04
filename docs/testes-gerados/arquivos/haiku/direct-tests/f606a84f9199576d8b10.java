/*
 * @test
 * @summary Verify that iterator remove on AbstractMap keySet and values views delegates to the entry set iterator
 * @run main AbstractMapWitupTest
 */

import java.util.AbstractMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

public class AbstractMapWitupTest extends AbstractMap<String, Integer> {

    private final LinkedHashMap<String, Integer> backing = new LinkedHashMap<>();

    @Override
    public Set<Map.Entry<String, Integer>> entrySet() {
        return backing.entrySet();
    }

    static AbstractMapWitupTest newMap() {
        AbstractMapWitupTest m = new AbstractMapWitupTest();
        m.backing.put("a", 1);
        m.backing.put("b", 2);
        m.backing.put("c", 3);
        return m;
    }

    static void check(boolean cond, String msg) {
        if (!cond) {
            throw new RuntimeException(msg);
        }
    }

    static void testKeySetIteratorRemove() {
        AbstractMapWitupTest m = newMap();
        Iterator<String> it = m.keySet().iterator();
        check(it.next().equals("a"), "first key should be a");
        it.remove();
        check(m.size() == 2, "size should be 2 after remove");
        check(!m.containsKey("a"), "a should be removed");
        check(m.containsKey("b"), "b should remain");
        check(m.containsKey("c"), "c should remain");
    }

    static void testValuesIteratorRemove() {
        AbstractMapWitupTest m = newMap();
        Iterator<Integer> it = m.values().iterator();
        check(it.next().intValue() == 1, "first value should be 1");
        it.remove();
        check(m.size() == 2, "size should be 2 after remove");
        check(!m.containsValue(1), "value 1 should be removed");
        check(m.containsValue(2), "value 2 should remain");
    }

    static void testRemoveBeforeNextThrows() {
        AbstractMapWitupTest m = newMap();
        Iterator<String> it = m.keySet().iterator();
        try {
            it.remove();
            throw new RuntimeException("remove before next should throw IllegalStateException");
        } catch (IllegalStateException expected) {
            // expected
        }
        check(m.size() == 3, "map should be unchanged");
    }

    public static void main(String[] args) {
        testKeySetIteratorRemove();
        testValuesIteratorRemove();
        testRemoveBeforeNextThrows();
    }
}
