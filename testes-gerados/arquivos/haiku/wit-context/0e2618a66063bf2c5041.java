/*
 * @test
 * @summary WITUP-derived checks for Collections.removeAll on checked entry sets
 * @run main CollectionsWitupTest
 */

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class CollectionsWitupTest {

    public static void main(String[] args) {
        testNullArgumentThrowsNpe();
        testEmptyCollectionReturnsFalse();
        testRemovesPresentEntry();
    }

    private static void testNullArgumentThrowsNpe() {
        Map<String, Integer> base = new HashMap<>();
        base.put("a", 1);
        base.put("b", 2);
        Set<Map.Entry<String, Integer>> entries =
                Collections.checkedMap(base, String.class, Integer.class).entrySet();
        boolean thrown = false;
        try {
            entries.removeAll(null);
        } catch (NullPointerException expected) {
            thrown = true;
        }
        if (!thrown) {
            throw new AssertionError("expected NullPointerException for null collection");
        }
        if (base.size() != 2) {
            throw new AssertionError("map modified after NPE: size=" + base.size());
        }
    }

    private static void testEmptyCollectionReturnsFalse() {
        Map<String, Integer> base = new HashMap<>();
        base.put("a", 1);
        Set<Map.Entry<String, Integer>> entries =
                Collections.checkedMap(base, String.class, Integer.class).entrySet();
        boolean changed = entries.removeAll(Collections.emptyList());
        if (changed) {
            throw new AssertionError("removeAll(empty) should return false");
        }
        if (base.size() != 1) {
            throw new AssertionError("map changed unexpectedly: size=" + base.size());
        }
    }

    private static void testRemovesPresentEntry() {
        Map<String, Integer> base = new HashMap<>();
        base.put("a", 1);
        base.put("b", 2);
        Set<Map.Entry<String, Integer>> entries =
                Collections.checkedMap(base, String.class, Integer.class).entrySet();
        List<Map.Entry<String, Integer>> toRemove = new ArrayList<>();
        toRemove.add(new AbstractMap.SimpleEntry<>("a", 1));
        boolean changed = entries.removeAll(toRemove);
        if (!changed) {
            throw new AssertionError("removeAll should return true when an entry is removed");
        }
        if (base.containsKey("a") || !base.containsKey("b")) {
            throw new AssertionError("unexpected map state after removeAll: " + base);
        }
    }
}
