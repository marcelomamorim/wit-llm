/*
 * @test
 * @summary Test UnsupportedOperationException thrown by remove() method in UnmodifiableEntrySet anonymous class
 * @run main CollectionsWitupTest
 */

import java.util.Collections;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.HashMap;

public class CollectionsWitupTest {
    public static void main(String[] args) {
        // Create a modifiable map
        Map<String, String> modifiableMap = new HashMap<>();
        modifiableMap.put("key1", "value1");

        // Create an unmodifiable map
        Map<String, String> unmodifiableMap = Collections.unmodifiableMap(modifiableMap);

        // Get the entry set from the unmodifiable map
        Set<Map.Entry<String, String>> entrySet = unmodifiableMap.entrySet();

        // Get iterator over the entry set
        Iterator<Map.Entry<String, String>> it = entrySet.iterator();

        if (it.hasNext()) {
            Map.Entry<String, String> entry = it.next();

            // The remove() method of the iterator should throw UnsupportedOperationException
            boolean thrown = false;
            try {
                it.remove();
            } catch (UnsupportedOperationException e) {
                thrown = true;
            }

            if (!thrown) {
                throw new RuntimeException("Expected UnsupportedOperationException not thrown by remove()");
            }
        } else {
            throw new RuntimeException("Entry set iterator has no elements");
        }
    }
}
