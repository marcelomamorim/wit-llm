/*
 * @test
 * @summary Test Collections.UnmodifiableCollection.retainAll throws UnsupportedOperationException
 * @run main CollectionsWitupTest
 */

import java.util.Collections;
import java.util.Collection;
import java.util.ArrayList;
import java.util.List;

public class CollectionsWitupTest {
    public static void main(String[] args) {
        // Create an unmodifiable collection wrapper
        List<String> list = new ArrayList<>();
        list.add("a");
        list.add("b");
        Collection<String> unmodifiable = Collections.unmodifiableCollection(list);

        // Test that retainAll throws UnsupportedOperationException
        boolean exceptionThrown = false;
        try {
            unmodifiable.retainAll(list);
        } catch (UnsupportedOperationException e) {
            exceptionThrown = true;
        }

        if (!exceptionThrown) {
            throw new RuntimeException("Expected UnsupportedOperationException not thrown by retainAll");
        }

        // Also test that retainAll on modifiable collection returns expected result
        List<String> modifiable = new ArrayList<>();
        modifiable.add("a");
        modifiable.add("b");
        modifiable.add("c");

        boolean changed = modifiable.retainAll(list); // list contains "a", "b"
        if (!changed) {
            throw new RuntimeException("Expected retainAll to return true when elements are removed");
        }
        if (modifiable.size() != 2 || !modifiable.contains("a") || !modifiable.contains("b")) {
            throw new RuntimeException("retainAll did not retain expected elements");
        }
    }
}
