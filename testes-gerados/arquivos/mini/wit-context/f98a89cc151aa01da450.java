/*
 * @test
 * @summary Test Collections.UnmodifiableCollection.addAll throws UnsupportedOperationException
 * @run main CollectionsWitupTest
 */

import java.util.*;

public class CollectionsWitupTest {
    public static void main(String[] args) {
        // Create an unmodifiable collection wrapper around a modifiable collection
        Collection<String> modifiable = new ArrayList<>();
        modifiable.add("one");
        Collection<String> unmodifiable = Collections.unmodifiableCollection(modifiable);

        // Test that addAll throws UnsupportedOperationException
        boolean threw = false;
        try {
            unmodifiable.addAll(Arrays.asList("two", "three"));
        } catch (UnsupportedOperationException e) {
            threw = true;
        }

        if (!threw) {
            throw new RuntimeException("Expected UnsupportedOperationException was not thrown by addAll");
        }

        // Also test that add throws UnsupportedOperationException for completeness
        threw = false;
        try {
            unmodifiable.add("four");
        } catch (UnsupportedOperationException e) {
            threw = true;
        }

        if (!threw) {
            throw new RuntimeException("Expected UnsupportedOperationException was not thrown by add");
        }

        // Test that contains works as expected
        if (!unmodifiable.contains("one")) {
            throw new RuntimeException("UnmodifiableCollection should contain 'one'");
        }

        // Test that size is correct
        if (unmodifiable.size() != 1) {
            throw new RuntimeException("UnmodifiableCollection size should be 1");
        }

        System.out.println("All tests passed.");
    }
}
