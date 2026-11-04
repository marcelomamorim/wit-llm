/* @test    @summary Test get method of ImmutableCollections for valid and invalid indices    @run main ImmutableCollectionsWitupTest */

import java.util.ImmutableCollections;

public class ImmutableCollectionsWitupTest {

    public static void main(String[] args) {
        testGetValidIndex();
        testGetInvalidIndex();
    }

    static void testGetValidIndex() {
        ImmutableCollections collection = new ImmutableCollections();
        // Simulate internal state for testing purposes
        // Since the actual internal state is not accessible, we assume get() returns e0 for index 0 and e1 for index 1
        // and throws for invalid indices. Here, we test the behavior based on the source code.
        try {
            Object result0 = collection.get(0);
            if (result0 == null) {
                throw new AssertionError("Expected non-null result for index 0");
            }
            Object result1 = collection.get(1);
            if (result1 == null) {
                throw new AssertionError("Expected non-null result for index 1");
            }
        } catch (Throwable t) {
            throw new AssertionError("Unexpected exception for valid indices", t);
        }
    }

    static void testGetInvalidIndex() {
        ImmutableCollections collection = new ImmutableCollections();
        boolean exceptionThrown = false;
        try {
            collection.get(2);
        } catch (IndexOutOfBoundsException e) {
            exceptionThrown = true;
        } catch (Throwable t) {
            throw new AssertionError("Unexpected exception type", t);
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected IndexOutOfBoundsException for index 2");
        }
    }
}
