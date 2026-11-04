/*
 * @test
 * @summary Test Collections.UnmodifiableMap.UnmodifiableEntrySet.UnmodifiableEntry.setValue throws UnsupportedOperationException
 * @run main CollectionsWitupTest
 */

import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.Iterator;

public class CollectionsWitupTest {
    public static void main(String[] args) {
        // Create a simple unmodifiable map entry to test setValue
        Map<String, String> modifiableMap = new java.util.HashMap<>();
        modifiableMap.put("key", "value");
        Map<String, String> unmodifiableMap = Collections.unmodifiableMap(modifiableMap);
        Set<Map.Entry<String, String>> entries = unmodifiableMap.entrySet();
        Iterator<Map.Entry<String, String>> it = entries.iterator();
        if (!it.hasNext()) {
            throw new RuntimeException("EntrySet is empty, test cannot proceed");
        }
        Map.Entry<String, String> entry = it.next();

        // The setValue method of the unmodifiable entry should throw UnsupportedOperationException
        boolean threw = false;
        try {
            entry.setValue("newValue");
        } catch (UnsupportedOperationException e) {
            threw = true;
        }

        if (!threw) {
            throw new RuntimeException("Expected UnsupportedOperationException was not thrown by setValue");
        }
    }
}
