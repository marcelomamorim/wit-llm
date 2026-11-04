/*
 * @test
 * @summary Test that ImmutableCollections.sort throws UnsupportedOperationException
 * @run main ImmutableCollectionsSortTest
 */

import java.util.Comparator;

public class ImmutableCollectionsSortTest {
    public static void main(String[] args) {
        testSortThrowsException();
    }

    static void testSortThrowsException() {
        try {
            new ImmutableCollections().sort(Comparator.naturalOrder());
            throw new AssertionError("Expected UnsupportedOperationException was not thrown");
        } catch (UnsupportedOperationException e) {
            // Test passes if exception is thrown
        }
    }
}
