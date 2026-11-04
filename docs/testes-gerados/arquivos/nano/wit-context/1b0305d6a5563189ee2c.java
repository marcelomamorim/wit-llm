/*
 * @test
 * @summary Test for Collections.removeIf to verify behavior and exception handling.
 * @run main CollectionsWitupTest
 */

import java.util.ArrayList;
import java.util.Collection;
import java.util.function.Predicate;

public class CollectionsWitupTest {

    public static void main(String[] args) {
        testRemoveIfWithModifiableCollection();
        testRemoveIfWithUnmodifiableCollection();
    }

    private static void testRemoveIfWithModifiableCollection() {
        Collection<String> list = new ArrayList<>();
        list.add("a");
        list.add("b");
        list.add("c");
        boolean result = false;
        try {
            result = list.removeIf(s -> s.equals("b"));
        } catch (UnsupportedOperationException e) {
            // Should not happen for modifiable collection
            throw new AssertionError("Unexpected UnsupportedOperationException on modifiable collection", e);
        }
        if (!result) {
            throw new AssertionError("Expected removeIf to return true when element is removed");
        }
        if (list.contains("b")) {
            throw new AssertionError("Element 'b' should have been removed");
        }
    }

    private static void testRemoveIfWithUnmodifiableCollection() {
        Collection<String> unmodifiable = java.util.Collections.unmodifiableCollection(new ArrayList<>());
        // Add elements to the underlying list to test removal
        ArrayList<String> underlyingList = (ArrayList<String>) unmodifiable;
        underlyingList.add("x");
        underlyingList.add("y");
        underlyingList.add("z");
        boolean exceptionThrown = false;
        try {
            unmodifiable.removeIf(s -> s.equals("y"));
        } catch (UnsupportedOperationException e) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected UnsupportedOperationException when calling removeIf on unmodifiable collection");
        }
    }
}
