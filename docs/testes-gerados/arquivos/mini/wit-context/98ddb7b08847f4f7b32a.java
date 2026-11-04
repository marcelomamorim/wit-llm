/*
 * @test
 * @summary Test Collections$UnmodifiableList remove() throws UnsupportedOperationException
 * @run main CollectionsWitupTest
 */

import java.util.Collections;
import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;

public class CollectionsWitupTest {
    public static void main(String[] args) {
        // Create a modifiable list
        List<String> modifiable = new ArrayList<>();
        modifiable.add("one");
        modifiable.add("two");

        // Wrap it with unmodifiableList
        List<String> unmodifiable = Collections.unmodifiableList(modifiable);

        // Get iterator and test remove() throws UnsupportedOperationException
        Iterator<String> it = unmodifiable.iterator();
        if (!it.hasNext()) {
            throw new RuntimeException("Iterator should have elements");
        }
        it.next();
        try {
            it.remove();
            throw new RuntimeException("Expected UnsupportedOperationException not thrown");
        } catch (UnsupportedOperationException expected) {
            // Expected exception
        }

        // Also test remove() on unmodifiable list directly via list.remove(Object) throws UnsupportedOperationException
        try {
            unmodifiable.remove("one");
            throw new RuntimeException("Expected UnsupportedOperationException not thrown on remove(Object)");
        } catch (UnsupportedOperationException expected) {
            // Expected exception
        }

        // Confirm original list still contains elements
        if (modifiable.size() != 2 || !modifiable.contains("one") || !modifiable.contains("two")) {
            throw new RuntimeException("Original list modified unexpectedly");
        }

        System.out.println("Test passed.");
    }
}
