/*
 * @test
 * @summary Tests for java.util.Vector.sort method, focusing on exception paths.
 * @run main VectorWitupTest
 */

import java.util.Vector;
import java.util.Comparator;

public class VectorWitupTest {

    public static void main(String[] args) {
        testSortWithNullComparatorAndEmptyVector();
        testSortWithNullComparatorAndNonEmptyVector();
    }

    private static void testSortWithNullComparatorAndEmptyVector() {
        Vector<Integer> vector = new Vector<>();
        try {
            vector.sort(null);
            System.out.println("Passed: sort with null comparator on empty vector.");
        } catch (Exception e) {
            System.err.println("Failed: sort with null comparator on empty vector: " + e);
        }
    }

    private static void testSortWithNullComparatorAndNonEmptyVector() {
        Vector<Integer> vector = new Vector<>();
        vector.add(1);
        vector.add(2);
        try {
            vector.sort(null);
            System.out.println("Passed: sort with null comparator on non-empty vector.");
        } catch (Exception e) {
            System.err.println("Failed: sort with null comparator on non-empty vector: " + e);
        }
    }
}
