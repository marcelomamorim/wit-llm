/*
 * @test
 * @summary Test for Collections.removeIf with null predicate
 * @run main CollectionsWitupTest
 */

import java.util.Collections;
import java.util.function.Predicate;

public class CollectionsWitupTest {
    public static void main(String[] args) {
        testRemoveIfWithNullPredicate();
    }

    public static void testRemoveIfWithNullPredicate() {
        Predicate<Object> predicate = null;
        boolean exceptionThrown = false;
        try {
            Collections.removeIf(predicate);
        } catch (NullPointerException e) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected NullPointerException when passing null predicate to removeIf");
        }
    }
}
