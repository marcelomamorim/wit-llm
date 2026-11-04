/*
 * @test
 * @summary Test Collections retainAll method throws UnsupportedOperationException
 * @run main CollectionsWitupTest
 */

import java.util.Collections;
import java.util.Collection;
import java.util.ArrayList;

public class CollectionsWitupTest {
    public static void main(String[] args) {
        testRetainAllUnsupported();
    }

    private static void testRetainAllUnsupported() {
        Collection<Object> coll = new ArrayList<>();
        try {
            Collections.emptyList().retainAll(coll);
            throw new Error("Expected UnsupportedOperationException");
        } catch (UnsupportedOperationException expected) {
            // Expected exception
        }
    }
}
