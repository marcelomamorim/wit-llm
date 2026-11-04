/*
 * @test
 * @summary Test Collections.CheckedMap.CheckedEntrySet.addAll throws UnsupportedOperationException
 * @run main CollectionsWitupTest
 */

import java.util.Collection;
import java.util.Map;
import java.util.Collections;
import java.util.Set;

public class CollectionsWitupTest {
    public static void main(String[] args) {
        // We test the addAll method of Collections.CheckedMap.CheckedEntrySet
        // which is known to throw UnsupportedOperationException always.

        // Create a dummy CheckedEntrySet instance via a CheckedMap
        Map<String, String> baseMap = Collections.emptyMap();
        Map<String, String> checkedMap = Collections.checkedMap(baseMap, String.class, String.class);
        Set<Map.Entry<String, String>> checkedEntrySet = checkedMap.entrySet();

        // Prepare a dummy collection of Map.Entry to pass to addAll
        Collection<Map.Entry<String, String>> dummyEntries = Collections.emptySet();

        try {
            // This call is expected to throw UnsupportedOperationException
            checkedEntrySet.addAll(dummyEntries);
            throw new RuntimeException("Expected UnsupportedOperationException not thrown");
        } catch (UnsupportedOperationException expected) {
            // Expected path
        }
    }
}
