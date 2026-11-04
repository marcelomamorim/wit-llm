/* @test    @summary Test for ImmutableCollections.next() method to verify exception when no elements remain    @run main ImmutableCollectionsWitupTest */

import java.util.ImmutableCollections;
import java.util.Map;

public class ImmutableCollectionsWitupTest {

    public static void main(String[] args) {
        testNextThrowsNoSuchElementExceptionWhenEmpty();
    }

    private static void testNextThrowsNoSuchElementExceptionWhenEmpty() {
        // Create a subclass with minimal implementation to test the next() method
        class DummyIterator extends ImmutableCollections.MapN.MapNIterator {
            DummyIterator() {
                super();
            }
            @Override
            public boolean hasNext() {
                return false; // Simulate no remaining elements
            }
        }
        try {
            Map.Entry<?, ?> result = new DummyIterator().next();
            // If no exception, test fails
            throw new AssertionError("Expected NoSuchElementException was not thrown.");
        } catch (java.util.NoSuchElementException e) {
            // Expected exception, test passes
        } catch (Throwable t) {
            throw new AssertionError("Unexpected exception type: " + t, t);
        }
    }
}
