/*
 * @test
 * @summary Test java.util.Collections.CheckedMap.CheckedEntrySet.removeAll(Collection<?>) for null argument
 * @run main CollectionsWitupTest
 */

import java.util.Collections;
import java.util.Collection;
import java.util.Set;
import java.util.Map;
import java.util.HashMap;
import java.util.Objects;

public class CollectionsWitupTest {
    public static void main(String[] args) {
        testRemoveAllNullCollection();
    }

    private static void testRemoveAllNullCollection() {
        // Setup a CheckedMap.CheckedEntrySet instance via a CheckedMap
        Map<String, String> baseMap = new HashMap<>();
        baseMap.put("key1", "value1");
        baseMap.put("key2", "value2");

        Map<String, String> checkedMap = Collections.checkedMap(baseMap, String.class, String.class);
        Set<Map.Entry<String, String>> entrySet = checkedMap.entrySet();

        // The entrySet is a CheckedEntrySet instance
        // Test removeAll with null argument should throw NullPointerException
        try {
            entrySet.removeAll(null);
            throw new AssertionError("Expected NullPointerException when calling removeAll with null");
        } catch (NullPointerException expected) {
            // Expected exception
        }

        // Test removeAll with empty collection returns false (no removal)
        boolean result = entrySet.removeAll(Collections.emptySet());
        if (result) {
            throw new AssertionError("removeAll with empty collection should return false");
        }

        // Test removeAll with a collection containing an entry that is not present returns false
        Map.Entry<String, String> fakeEntry = new Map.Entry<String, String>() {
            public String getKey() { return "nonexistent"; }
            public String getValue() { return "novalue"; }
            public String setValue(String value) { throw new UnsupportedOperationException(); }
            public boolean equals(Object o) { return false; }
            public int hashCode() { return 0; }
        };
        Collection<Map.Entry<String, String>> toRemove = Collections.singleton(fakeEntry);
        boolean result2 = entrySet.removeAll(toRemove);
        if (result2) {
            throw new AssertionError("removeAll with non-present entry should return false");
        }
    }
}
