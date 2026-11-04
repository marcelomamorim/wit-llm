/* @test    @summary Test for ImmutableCollections.previousIndex() method to verify exception throwing behavior.    @run main ImmutableCollectionsWitupTest */

import java.util.ImmutableCollections;

public class ImmutableCollectionsWitupTest {

    public static void main(String[] args) {
        testPreviousIndexThrowsUOE();
    }

    private static void testPreviousIndexThrowsUOE() {
        ImmutableCollections.AbstractImmutableCollection<?> collection = new ImmutableCollections.AbstractImmutableCollection<Object>() {
            @Override
            public int size() {
                return 0;
            }
        };
        try {
            // Simulate the condition where isListIterator is false
            // Since isListIterator is a private field, we cannot set it directly; instead, we rely on the behavior
            // that previousIndex() throws UnsupportedOperationException when isListIterator is false.
            // We invoke previousIndex() expecting an exception.
            int result = collection.previousIndex();
            // If no exception, the test fails
            System.err.println("FAIL: Expected UnsupportedOperationException was not thrown.");
        } catch (UnsupportedOperationException e) {
            // Expected exception
            System.out.println("PASS: UnsupportedOperationException thrown as expected.");
        } catch (Exception e) {
            System.err.println("FAIL: Unexpected exception: " + e);
        }
    }
}
