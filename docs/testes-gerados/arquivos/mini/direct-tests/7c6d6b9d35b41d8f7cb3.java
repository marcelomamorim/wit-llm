/*
 * @test
 * @summary Test Collections.addAll(int, Collection) throws UnsupportedOperationException
 * @run main CollectionsWitupTest
 */

import java.util.Collections;
import java.util.ArrayList;
import java.util.Collection;

public class CollectionsWitupTest {
    public static void main(String[] args) {
        CollectionsWitupTest test = new CollectionsWitupTest();
        test.testAddAllThrows();
    }

    public void testAddAllThrows() {
        Collection<String> c = new ArrayList<>();
        try {
            // The method is expected to throw UnsupportedOperationException always
            Collections.addAll(0, c);
            throw new AssertionError("Expected UnsupportedOperationException not thrown");
        } catch (UnsupportedOperationException expected) {
            // Test passed
        }
    }
}
