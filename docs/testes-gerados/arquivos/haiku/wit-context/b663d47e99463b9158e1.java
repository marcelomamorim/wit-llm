/*
 * @test
 * @summary Verifies that addAll on the entry set of a Collections.checkedMap view always throws UnsupportedOperationException and leaves the backing map unchanged
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
        testAddAllNonEmptyThrows();
        testAddAllEmptyThrows();
    }

    static void testAddAllNonEmptyThrows() {
        Map<String, Integer> base = new HashMap<>();
        Map<String, Integer> checked = Collections.checkedMap(base, String.class, Integer.class);
        Set<Map.Entry<String, Integer>> entries = checked.entrySet();

        List<Map.Entry<String, Integer>> toAdd = new ArrayList<>();
        toAdd.add(new AbstractMap.SimpleEntry<>("a", 1));

        boolean thrown = false;
        try {
            entries.addAll(toAdd);
        } catch (UnsupportedOperationException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("addAll should throw UnsupportedOperationException");
        }
        if (!base.isEmpty()) {
            throw new RuntimeException("backing map must not be modified");
        }
    }

    static void testAddAllEmptyThrows() {
        Map<String, Integer> base = new HashMap<>();
        Map<String, Integer> checked = Collections.checkedMap(base, String.class, Integer.class);
        Set<Map.Entry<String, Integer>> entries = checked.entrySet();

        List<Map.Entry<String, Integer>> toAdd = new ArrayList<>();

        boolean thrown = false;
        try {
            entries.addAll(toAdd);
        } catch (UnsupportedOperationException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("addAll with empty collection should also throw UnsupportedOperationException");
        }
        if (!base.isEmpty()) {
            throw new RuntimeException("backing map must remain empty");
        }
    }
}
