/*
 * @test
 * @summary Verifies that the keySet iterator remove() of AbstractMap delegates to the entrySet iterator remove()
 * @run main AbstractMapWitupTest
 */

import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

public class AbstractMapWitupTest {

    static class BackedMap extends AbstractMap<String, Integer> {
        private final HashMap<String, Integer> backing = new HashMap<>();

        @Override
        public Set<Entry<String, Integer>> entrySet() {
            return backing.entrySet();
        }

        @Override
        public Integer put(String key, Integer value) {
            return backing.put(key, value);
        }
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    public static void main(String[] args) {
        BackedMap map = new BackedMap();
        map.put("a", 1);
        map.put("b", 2);
        map.put("c", 3);

        Iterator<String> it = map.keySet().iterator();
        String first = it.next();
        it.remove();
        check(!map.containsKey(first), "removed key still present");
        check(map.size() == 2, "size after one remove expected 2 but was " + map.size());

        boolean illegalState = false;
        Iterator<String> fresh = map.keySet().iterator();
        try {
            fresh.remove();
        } catch (IllegalStateException e) {
            illegalState = true;
        }
        check(illegalState, "remove() before next() should throw IllegalStateException");
        check(map.size() == 2, "failed remove must not change size");

        Iterator<String> all = map.keySet().iterator();
        while (all.hasNext()) {
            all.next();
            all.remove();
        }
        check(map.isEmpty(), "map should be empty after removing all keys");
    }
}
