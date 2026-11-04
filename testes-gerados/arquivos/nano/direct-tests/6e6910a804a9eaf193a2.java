/* @test    @summary Test Vector.remove() method for correct exception and behavior    @run main VectorWitupTest */

import java.util.Vector;

public class VectorWitupTest {

    public static void main(String[] args) {
        testRemoveWithoutLastRet();
        testRemoveWithValidIndex();
    }

    static void testRemoveWithoutLastRet() {
        Vector<Integer> vector = new Vector<>();
        vector.add(1);
        vector.add(2);
        vector.add(3);
        boolean exceptionThrown = false;
        try {
            vector.remove();
        } catch (IllegalStateException e) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected IllegalStateException when remove() called with lastRet == -1");
        }
    }

    static void testRemoveWithValidIndex() {
        Vector<String> vector = new Vector<>();
        vector.add("a");
        vector.add("b");
        vector.add("c");
        // Simulate iterator behavior by setting lastRet to a valid index
        // Since remove() uses lastRet, we mimic this by directly calling remove() after setting lastRet
        // But as lastRet is private, we simulate the scenario by calling remove() after a valid operation
        // For this test, we invoke remove() after adding elements, expecting no exception
        vector.remove(); // Should throw IllegalStateException, so we catch and ignore
        boolean exceptionThrown = false;
        try {
            vector.remove();
        } catch (IllegalStateException e) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected IllegalStateException when remove() called with lastRet == -1");
        }
    }
}
