/*
 * @test
 * @summary Test that Collections.addAll(Collection<? extends Map.Entry<K,V>>) throws UnsupportedOperationException
 * @run main CollectionsWitupTest
 */

import java.util.Collections;
import java.util.Collection;
import java.util.Map;
import java.util.AbstractMap;

public class CollectionsWitupTest {
    public static void main(String[] args) {
        testAddAllUnsupported();
    }

    private static void testAddAllUnsupported() {
        Collection<Map.Entry<String, String>> coll = new java.util.ArrayList<>();
        try {
            // This method is expected to throw UnsupportedOperationException
            boolean result = new Collections() {
                // Anonymous subclass to access the method since addAll is instance method here
                public boolean addAll(Collection<? extends Map.Entry<String, String>> c) {
                    return Collections.addAll(c);
                }
            }.addAll(coll);
            throw new AssertionError("Expected UnsupportedOperationException not thrown, returned: " + result);
        } catch (UnsupportedOperationException expected) {
            // Expected exception
        }
    }
}
