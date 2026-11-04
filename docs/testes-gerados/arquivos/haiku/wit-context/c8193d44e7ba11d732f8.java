/*
 * @test
 * @summary Checks that the checked map entry set rejects add(Map.Entry) with UnsupportedOperationException and leaves the backing map unchanged
 * @run main CollectionsWitupTest
 */

import java.util.AbstractMap;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class CollectionsWitupTest {
    public static void main(String[] args) {
        Map<String, String> backing = new HashMap<>();
        backing.put("k", "v");
        Map<String, String> checked = Collections.checkedMap(backing, String.class, String.class);
        Set<Map.Entry<String, String>> entries = checked.entrySet();
        Map.Entry<String, String> entry = new AbstractMap.SimpleEntry<>("a", "b");

        boolean thrown = false;
        try {
            entries.add(entry);
        } catch (UnsupportedOperationException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new AssertionError("Expected UnsupportedOperationException from add(Map.Entry)");
        }
        if (backing.containsKey("a")) {
            throw new AssertionError("Backing map was modified by rejected add");
        }
        if (backing.size() != 1 || !"v".equals(backing.get("k"))) {
            throw new AssertionError("Backing map state changed unexpectedly");
        }
    }
}
