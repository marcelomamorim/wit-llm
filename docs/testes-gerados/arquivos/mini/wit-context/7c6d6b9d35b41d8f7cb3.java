/*
 * @test
 * @summary Test Collections.UnmodifiableList.addAll(int, Collection<? extends E>) throws UnsupportedOperationException
 * @run main CollectionsWitupTest
 */

import java.util.*;

public class CollectionsWitupTest {
    public static void main(String[] args) {
        // Create an unmodifiable list wrapper
        List<String> modifiable = new ArrayList<>();
        modifiable.add("one");
        modifiable.add("two");
        List<String> unmodifiable = Collections.unmodifiableList(modifiable);

        // Prepare a collection to add
        Collection<String> toAdd = new ArrayList<>();
        toAdd.add("three");

        // Test that addAll(int, Collection) throws UnsupportedOperationException
        boolean threw = false;
        try {
            unmodifiable.addAll(1, toAdd);
        } catch (UnsupportedOperationException e) {
            threw = true;
        }

        if (!threw) {
            throw new AssertionError("Expected UnsupportedOperationException was not thrown by addAll(int, Collection)");
        }

        // Also test that addAll(Collection) throws UnsupportedOperationException for completeness
        threw = false;
        try {
            unmodifiable.addAll(toAdd);
        } catch (UnsupportedOperationException e) {
            threw = true;
        }

        if (!threw) {
            throw new AssertionError("Expected UnsupportedOperationException was not thrown by addAll(Collection)");
        }

        // Test that original modifiable list can addAll successfully
        boolean added = modifiable.addAll(toAdd);
        if (!added || !modifiable.containsAll(toAdd)) {
            throw new AssertionError("Modifiable list failed to addAll elements");
        }

        System.out.println("All tests passed.");
    }
}
