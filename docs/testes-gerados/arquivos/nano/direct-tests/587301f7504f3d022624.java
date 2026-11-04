/* @test    @summary Test addAll method in ImmutableCollections to ensure it throws UnsupportedOperationException    @run main ImmutableCollectionsWitupTest */

import java.util.*;

public class ImmutableCollectionsWitupTest {

    public static void main(String[] args) {
        testAddAllThrowsException();
    }

    static void testAddAllThrowsException() {
        List<String> list = new ArrayList<>();
        Collection<String> coll = Arrays.asList("a", "b");
        try {
            // Attempt to call addAll, expecting an UnsupportedOperationException
            list.addAll(0, coll);
            // If no exception, the test fails
            throw new AssertionError("Expected UnsupportedOperationException was not thrown");
        } catch (UnsupportedOperationException e) {
            // Expected exception caught, test passes
        }
    }
}
