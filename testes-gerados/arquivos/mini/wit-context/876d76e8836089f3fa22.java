/*
 * @test
 * @summary Test Collections.UnmodifiableList.add(int, E) throws UnsupportedOperationException
 * @run main CollectionsWitupTest
 */

import java.util.Collections;
import java.util.List;
import java.util.ArrayList;

public class CollectionsWitupTest {
    public static void main(String[] args) {
        // Create a modifiable list
        List<String> modifiable = new ArrayList<>();
        modifiable.add("one");
        modifiable.add("two");

        // Create an unmodifiable view
        List<String> unmodifiable = Collections.unmodifiableList(modifiable);

        // Verify that add(int, E) throws UnsupportedOperationException
        boolean threw = false;
        try {
            unmodifiable.add(1, "three");
        } catch (UnsupportedOperationException e) {
            threw = true;
        }

        if (!threw) {
            throw new AssertionError("Expected UnsupportedOperationException not thrown by add(int, E) on unmodifiable list");
        }

        // Also verify that the original list is unchanged
        if (modifiable.size() != 2 || !"one".equals(modifiable.get(0)) || !"two".equals(modifiable.get(1))) {
            throw new AssertionError("Original list was modified unexpectedly");
        }

        System.out.println("Test passed.");
    }
}
