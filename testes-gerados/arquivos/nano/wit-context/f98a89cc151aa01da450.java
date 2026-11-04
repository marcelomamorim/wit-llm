/*
 * @test
 * @summary Testa o método addAll de Collections para comportamento esperado.
 * @run main CollectionsWitupTest
 */

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class CollectionsWitupTest {
    public static void main(String[] args) {
        testAddAllThrowsException();
    }

    public static void testAddAllThrowsException() {
        Collection<Integer> coll = new ArrayList<>();
        Collection<Integer> unmodifiableColl = Collections.unmodifiableCollection(coll);
        boolean exceptionThrown = false;
        try {
            unmodifiableColl.addAll(java.util.Arrays.asList(1, 2, 3));
        } catch (UnsupportedOperationException e) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected UnsupportedOperationException was not thrown.");
        }
    }
}
