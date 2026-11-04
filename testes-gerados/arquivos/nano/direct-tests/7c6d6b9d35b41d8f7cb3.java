/* @test    @summary Test addAll method of Collections that throws UnsupportedOperationException    @run main CollectionsWitupTest */

import java.util.*;

public class CollectionsWitupTest {
    public static void main(String[] args) {
        testAddAllThrowsException();
    }

    public static void testAddAllThrowsException() {
        List<String> list = new ArrayList<>();
        List<String> otherList = Arrays.asList("a", "b", "c");
        try {
            Collections.addAll(0, otherList);
            // If no exception, fail the test
            throw new AssertionError("Expected UnsupportedOperationException was not thrown.");
        } catch (UnsupportedOperationException e) {
            // Test passes
        }
    }
}
