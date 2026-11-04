/* @test    @summary Test for Vector.sort method to ensure it sorts elements correctly and detects concurrent modifications.    @run main VectorWitupTest */

import java.util.Vector;
import java.util.Comparator;

public class VectorWitupTest {

    public static void main(String[] args) {
        testSortCorrectness();
        testConcurrentModificationDetection();
    }

    private static void testSortCorrectness() {
        Vector<Integer> vector = new Vector<>();
        vector.add(3);
        vector.add(1);
        vector.add(2);
        vector.sort(null); // sort with natural order
        assert vector.get(0) == 1 : "First element should be 1";
        assert vector.get(1) == 2 : "Second element should be 2";
        assert vector.get(2) == 3 : "Third element should be 3";
    }

    private static void testConcurrentModificationDetection() {
        Vector<String> vector = new Vector<>();
        vector.add("a");
        vector.add("b");
        vector.add("c");
        try {
            synchronized (vector) {
                for (String s : vector) {
                    vector.add("d"); // modify during iteration
                }
            }
            assert false : "Expected ConcurrentModificationException";
        } catch (java.util.ConcurrentModificationException e) {
            // expected exception
        }
    }
}
