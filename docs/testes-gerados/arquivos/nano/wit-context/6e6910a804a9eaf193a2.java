/*
 * @test
 * @summary Test for Vector.remove() method, focusing on IllegalStateException when lastRet == -1
 * @run main VectorWitupTest
 */

import java.util.Vector;

public class VectorWitupTest {

    public static void main(String[] args) {
        testRemoveThrowsWhenLastRetMinusOne();
        testRemoveNormalBehavior();
    }

    private static void testRemoveThrowsWhenLastRetMinusOne() {
        Vector<Integer> vector = new Vector<>();
        try {
            vector.remove();
            System.out.println("FAIL: Expected IllegalStateException was not thrown.");
        } catch (IllegalStateException e) {
            System.out.println("PASS: Caught expected IllegalStateException.");
        } catch (Exception e) {
            System.out.println("FAIL: Caught unexpected exception: " + e);
        }
    }

    private static void testRemoveNormalBehavior() {
        Vector<Integer> vector = new Vector<>();
        vector.add(1);
        vector.add(2);
        vector.add(3);
        // Simulate a state where lastRet is set to a valid index
        // Since lastRet is private, we simulate by calling iterator().next() and remove()
        java.util.Iterator<Integer> it = vector.iterator();
        if (it.hasNext()) {
            it.next(); // advances to index 0
            it.remove(); // removes element at index 0
            if (vector.size() == 2 && vector.get(0) == 2) {
                System.out.println("PASS: remove() removed the first element as expected.");
            } else {
                System.out.println("FAIL: remove() did not behave as expected.");
            }
        } else {
            System.out.println("FAIL: Iterator has no elements.");
        }
    }
}
