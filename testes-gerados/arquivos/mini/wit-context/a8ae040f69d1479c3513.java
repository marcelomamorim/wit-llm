/*
 * @test
 * @summary Test Collections$UnmodifiableCollection remove() throws UnsupportedOperationException
 * @run main CollectionsWitupTest
 */

import java.util.Collections;
import java.util.Collection;
import java.util.ArrayList;

public class CollectionsWitupTest {
    public static void main(String[] args) {
        // Create a modifiable collection
        Collection<String> modifiable = new ArrayList<>();
        modifiable.add("test");

        // Wrap it with unmodifiableCollection
        Collection<String> unmodifiable = Collections.unmodifiableCollection(modifiable);

        // Test that remove() throws UnsupportedOperationException
        try {
            unmodifiable.remove("test");
            throw new RuntimeException("Expected UnsupportedOperationException not thrown");
        } catch (UnsupportedOperationException expected) {
            // Expected exception
        }

        // Also test that remove() on iterator throws UnsupportedOperationException
        try {
            unmodifiable.iterator().remove();
            throw new RuntimeException("Expected UnsupportedOperationException not thrown from iterator.remove()");
        } catch (UnsupportedOperationException expected) {
            // Expected exception
        }

        // Test that remove() on modifiable collection works normally
        boolean removed = modifiable.remove("test");
        if (!removed) {
            throw new RuntimeException("Remove on modifiable collection failed");
        }

        // Test that remove() on unmodifiable collection returns false if element not present
        boolean result = unmodifiable.remove("absent");
        if (result) {
            throw new RuntimeException("Remove on unmodifiable collection returned true for absent element");
        }
    }
}
