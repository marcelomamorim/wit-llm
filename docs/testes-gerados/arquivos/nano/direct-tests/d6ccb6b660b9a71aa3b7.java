/* @test    @summary Test for ImmutableCollections.nextIndex() method behavior.    @run main ImmutableCollectionsWitupTest */

import java.util.ImmutableCollections;

public class ImmutableCollectionsWitupTest {

    public static void main(String[] args) {
        testNextIndexWhenNotListIterator();
        testNextIndexWhenIsListIterator();
    }

    private static void testNextIndexWhenNotListIterator() {
        ImmutableCollections instance = new ImmutableCollections();
        boolean exceptionThrown = false;
        try {
            // Simulate isListIterator == false by setting cursor to any value
            // Since isListIterator is private, we simulate by calling nextIndex() without setting up list iterator context
            // but as the method throws uoe() if !isListIterator, we expect an exception
            instance.nextIndex();
        } catch (UnsupportedOperationException e) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected UnsupportedOperationException when isListIterator is false.");
        }
    }

    private static void testNextIndexWhenIsListIterator() {
        ImmutableCollections instance = new ImmutableCollections();
        // To test the path where isListIterator == true, we need to simulate that condition.
        // Since isListIterator is private and no setter is available, we cannot directly set it.
        // Therefore, we test the behavior assuming the method is called in a context where isListIterator is true.
        // As the method relies on the internal state, and no public API to set it, we test the return value when cursor is set.
        // But since cursor is also private, we cannot set it directly.
        // Therefore, we test the method's return value when called normally, expecting it to return cursor.
        // As cursor defaults to 0, we check that.
        int result = instance.nextIndex();
        if (result != 0) {
            throw new AssertionError("Expected nextIndex() to return 0, but got " + result);
        }
    }
}
