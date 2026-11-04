/* @test    @summary Test removeIf method for Vector class    @run main VectorWitupTest */

import java.util.Vector;
import java.util.function.Predicate;

public class VectorWitupTest {

    public static void main(String[] args) {
        testRemoveIfWithNullPredicate();
        testRemoveIfWithValidPredicate();
    }

    private static void testRemoveIfWithNullPredicate() {
        Vector<Integer> vector = new Vector<>();
        vector.add(1);
        vector.add(2);
        vector.add(3);
        try {
            vector.removeIf(null);
            // If no exception, test fails
            throw new AssertionError("Expected NullPointerException not thrown");
        } catch (NullPointerException e) {
            // Expected exception
        }
    }

    private static void testRemoveIfWithValidPredicate() {
        Vector<Integer> vector = new Vector<>();
        vector.add(1);
        vector.add(2);
        vector.add(3);
        boolean result = vector.removeIf(e -> e % 2 == 0);
        // After removal, vector should contain only odd numbers
        if (result != true) {
            throw new AssertionError("removeIf should return true when elements are removed");
        }
        if (vector.size() != 2 || !vector.contains(1) || !vector.contains(3)) {
            throw new AssertionError("Vector contents incorrect after removeIf");
        }
    }
}
