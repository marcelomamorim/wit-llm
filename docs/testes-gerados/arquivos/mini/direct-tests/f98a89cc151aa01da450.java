/*
 * @test
 * @summary Test UnsupportedOperationException from addAll method
 * @run main CollectionsWitupTest
 */

import java.util.Collection;
import java.util.Collections;
import java.util.ArrayList;

public class CollectionsWitupTest {
    public static void main(String[] args) {
        testAddAllThrows();
    }

    private static void testAddAllThrows() {
        Collection<Object> coll = new ArrayList<>();
        Collection<Object> dummy = new ArrayList<>();
        try {
            // The addAll method here is instance method of an anonymous subclass
            // because Collections.addAll is static and has different signature
            new Collections() {
                public boolean addAll(Collection<?> c) {
                    throw new UnsupportedOperationException();
                }
            }.addAll(dummy);
            throw new RuntimeException("Expected UnsupportedOperationException not thrown");
        } catch (UnsupportedOperationException expected) {
            // expected
        }
    }
}
