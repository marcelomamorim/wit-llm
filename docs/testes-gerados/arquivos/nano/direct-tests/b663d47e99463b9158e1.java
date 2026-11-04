/* @test    @summary Test addAll method in Collections that throws UnsupportedOperationException    @run main CollectionsWitupTest */

import java.util.*;

public class CollectionsWitupTest {

    public static void main(String[] args) {
        testAddAllThrowsException();
    }

    public static void testAddAllThrowsException() {
        Collection<Map.Entry<Integer, String>> coll = new ArrayList<>();
        try {
            Collections.addAll(coll);
            throw new AssertionError("Expected UnsupportedOperationException was not thrown");
        } catch (UnsupportedOperationException e) {
            // Test passes if exception is thrown
        }
    }
}
