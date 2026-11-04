/*
 * @test
 * @summary Test ImmutableCollections.ListItr.remove() throws UnsupportedOperationException
 * @run main ImmutableCollectionsWitupTest
 */

import java.util.ImmutableCollections;

public class ImmutableCollectionsWitupTest {
    public static void main(String[] args) {
        // Create an instance of ListItr to test remove()
        // ListItr is a package-private inner class of ImmutableCollections
        // We cannot instantiate it directly, so we test via a public method that returns ListItr
        // However, no public factory is visible in the provided context
        // Instead, we create an immutable list and get its iterator, which is ListItr

        java.util.List<String> list = java.util.List.of("a", "b", "c");
        java.util.Iterator<String> it = list.iterator();

        // Advance iterator to have a valid remove() call
        if (it.hasNext()) {
            it.next();
        }

        boolean uoeThrown = false;
        try {
            it.remove();
        } catch (UnsupportedOperationException e) {
            uoeThrown = true;
        }

        if (!uoeThrown) {
            throw new AssertionError("Expected UnsupportedOperationException from remove()");
        }

        System.out.println("Test passed: remove() throws UnsupportedOperationException as expected.");
    }
}
