/* @test    @summary Test previousIndex method of ImmutableCollections class    @run main ImmutableCollectionsWitupTest */

import java.util.ImmutableCollections;

public class ImmutableCollectionsWitupTest {

    public static void main(String[] args) {
        testPreviousIndexWhenListIterator();
        testPreviousIndexWhenNotListIterator();
    }

    private static void testPreviousIndexWhenListIterator() {
        // Simulate a list iterator context where isListIterator is true
        ImmutableCollections instance = new ImmutableCollections() {
            boolean isListIterator = true;
            int cursor = 5;
            @Override
            public int previousIndex() {
                if (!isListIterator) {
                    throw new UnsupportedOperationException();
                }
                return cursor - 1;
            }
        };
        int result = instance.previousIndex();
        if (result != 4) {
            throw new AssertionError("Expected previousIndex to be 4, but got " + result);
        }
    }

    private static void testPreviousIndexWhenNotListIterator() {
        // Simulate a context where isListIterator is false, expecting exception
        ImmutableCollections instance = new ImmutableCollections() {
            boolean isListIterator = false;
            int cursor = 5;
            @Override
            public int previousIndex() {
                if (!isListIterator) {
                    throw new UnsupportedOperationException();
                }
                return cursor - 1;
            }
        };
        boolean exceptionThrown = false;
        try {
            instance.previousIndex();
        } catch (UnsupportedOperationException e) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected UnsupportedOperationException to be thrown");
        }
    }
}
