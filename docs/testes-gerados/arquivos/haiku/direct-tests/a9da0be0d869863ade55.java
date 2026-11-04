/*
 * @test
 * @summary Verify that the descending key iterator's remove() removes the last returned key from the TreeMap
 * @run main TreeMapWitupTest
 */

import java.util.Iterator;
import java.util.TreeMap;
import java.util.ArrayList;
import java.util.List;

public class TreeMapWitupTest {

    public static void main(String[] args) {
        removeLastDescendingKey();
        removeAllDescendingKeysEmptiesMap();
        removeDoesNotAffectOtherKeys();
    }

    static void removeLastDescendingKey() {
        TreeMap<Integer, String> map = new TreeMap<>();
        map.put(1, "a");
        map.put(2, "b");
        map.put(3, "c");

        Iterator<Integer> it = map.descendingKeySet().iterator();
        check(it.hasNext(), "descending iterator should have elements");
        Integer first = it.next();
        check(first.intValue() == 3, "first descending key should be 3, was " + first);
        it.remove();

        check(!map.containsKey(3), "key 3 should be removed");
        check(map.size() == 2, "size should be 2, was " + map.size());
        check(map.containsKey(1) && map.containsKey(2), "keys 1 and 2 should remain");

        Integer second = it.next();
        check(second.intValue() == 2, "next descending key should be 2, was " + second);
        it.remove();
        check(!map.containsKey(2), "key 2 should be removed");
        check(map.size() == 1 && map.containsKey(1), "only key 1 should remain");
    }

    static void removeAllDescendingKeysEmptiesMap() {
        TreeMap<String, Integer> map = new TreeMap<>();
        map.put("x", 1);
        map.put("y", 2);
        map.put("z", 3);

        Iterator<String> it = map.descendingKeySet().iterator();
        while (it.hasNext()) {
            it.next();
            it.remove();
        }
        check(map.isEmpty(), "map should be empty after removing all keys, size=" + map.size());
    }

    static void removeDoesNotAffectOtherKeys() {
        TreeMap<Integer, Integer> map = new TreeMap<>();
        for (int i = 0; i < 5; i++) {
            map.put(i, i * 10);
        }
        Iterator<Integer> it = map.descendingKeySet().iterator();
        it.next();
        it.remove();

        List<Integer> remaining = new ArrayList<>(map.keySet());
        List<Integer> expected = new ArrayList<>();
        expected.add(0);
        expected.add(1);
        expected.add(2);
        expected.add(3);
        check(remaining.equals(expected), "remaining keys should be [0, 1, 2, 3], were " + remaining);
        check(map.get(3) == null, "value for removed key 3 should be null");
        check(map.get(2) == 20, "value for key 2 should remain 20");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
