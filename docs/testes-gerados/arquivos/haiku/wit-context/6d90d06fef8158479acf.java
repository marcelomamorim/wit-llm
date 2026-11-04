/*
 * @test
 * @summary Witup-generated checks for retainAll on checked map entry sets via Collections.checkedMap
 * @run main CollectionsWitupTest
 */

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class CollectionsWitupTest {
    public static void main(String[] args) {
        testNullArgumentThrowsNpe();
        testRetainAllEmptyClearsMap();
        testRetainAllWithAllEntriesKeepsMap();
    }

    static Map<String, Integer> newCheckedMap() {
        Map<String, Integer> m = new HashMap<>();
        m.put("a", 1);
        m.put("b", 2);
        return Collections.checkedMap(m, String.class, Integer.class);
    }

    static void testNullArgumentThrowsNpe() {
        Map<String, Integer> map = newCheckedMap();
        try {
            map.entrySet().retainAll(null);
        } catch (NullPointerException e) {
            return;
        }
        throw new RuntimeException("expected NullPointerException for retainAll(null)");
    }

    static void testRetainAllEmptyClearsMap() {
        Map<String, Integer> map = newCheckedMap();
        boolean changed = map.entrySet().retainAll(new HashSet<Object>());
        if (!changed) {
            throw new RuntimeException("retainAll with empty collection should report change");
        }
        if (!map.isEmpty()) {
            throw new RuntimeException("map should be empty after retainAll(empty)");
        }
    }

    static void testRetainAllWithAllEntriesKeepsMap() {
        Map<String, Integer> map = newCheckedMap();
        ArrayList<Object> all = new ArrayList<Object>(map.entrySet());
        boolean changed = map.entrySet().retainAll(all);
        if (changed) {
            throw new RuntimeException("retainAll with all entries should not report change");
        }
        if (map.size() != 2) {
            throw new RuntimeException("map size should remain 2, was " + map.size());
        }
    }
}
