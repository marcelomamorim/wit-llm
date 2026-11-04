/*
 * @test
 * @summary Test Collections.UnmodifiableList.sort throws UnsupportedOperationException
 * @run main CollectionsWitupTest
 */

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.ArrayList;

public class CollectionsWitupTest {
    public static void main(String[] args) {
        // Create a modifiable list
        List<String> modifiable = new ArrayList<>();
        modifiable.add("a");
        modifiable.add("b");
        modifiable.add("c");

        // Wrap it with unmodifiableList
        List<String> unmodifiable = Collections.unmodifiableList(modifiable);

        // Try to sort the unmodifiable list and expect UnsupportedOperationException
        try {
            unmodifiable.sort(Comparator.naturalOrder());
            throw new RuntimeException("Expected UnsupportedOperationException not thrown");
        } catch (UnsupportedOperationException expected) {
            // Expected exception
        }

        // Verify original list remains unchanged
        if (!modifiable.get(0).equals("a") || !modifiable.get(1).equals("b") || !modifiable.get(2).equals("c")) {
            throw new RuntimeException("Original list modified unexpectedly");
        }

        // Also verify unmodifiable list contents
        if (!unmodifiable.get(0).equals("a") || !unmodifiable.get(1).equals("b") || !unmodifiable.get(2).equals("c")) {
            throw new RuntimeException("Unmodifiable list contents changed unexpectedly");
        }
    }
}
