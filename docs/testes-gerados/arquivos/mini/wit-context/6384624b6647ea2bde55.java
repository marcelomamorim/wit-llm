/*
 * @test
 * @summary Test Collections.UnmodifiableCollection.removeAll throws UnsupportedOperationException
 * @run main CollectionsWitupTest
 */

import java.util.*;

public class CollectionsWitupTest {
    public static void main(String[] args) {
        // Create an unmodifiable collection via Collections.unmodifiableCollection
        Collection<String> modifiable = new ArrayList<>();
        modifiable.add("one");
        modifiable.add("two");
        Collection<String> unmodifiable = Collections.unmodifiableCollection(modifiable);

        // Attempt to call removeAll on the unmodifiable collection and expect UnsupportedOperationException
        boolean caught = false;
        try {
            unmodifiable.removeAll(Collections.singleton("one"));
        } catch (UnsupportedOperationException e) {
            caught = true;
        }

        if (!caught) {
            throw new RuntimeException("Expected UnsupportedOperationException not thrown by removeAll on unmodifiable collection");
        }

        // Also verify that removeAll on modifiable collection works as expected
        boolean removed = modifiable.removeAll(Collections.singleton("one"));
        if (!removed || modifiable.contains("one")) {
            throw new RuntimeException("removeAll did not remove element as expected on modifiable collection");
        }

        System.out.println("Test passed.");
    }
}
