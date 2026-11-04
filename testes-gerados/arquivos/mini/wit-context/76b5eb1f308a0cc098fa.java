/*
 * @test
 * @summary Test Vector.sort(Comparator) for expected behavior and exceptions
 * @run main VectorWitupTest
 */

import java.util.Vector;
import java.util.Comparator;
import java.util.ConcurrentModificationException;
import java.util.Arrays;

public class VectorWitupTest {
    public static void main(String[] args) {
        testSortWithValidComparator();
        testSortWithNullComparator();
        testSortConcurrentModification();
    }

    private static void testSortWithValidComparator() {
        Vector<String> v = new Vector<>();
        v.add("banana");
        v.add("apple");
        v.add("cherry");

        // Sort with natural order comparator
        v.sort(Comparator.naturalOrder());

        // Check that elements are sorted
        if (!"apple".equals(v.get(0)) || !"banana".equals(v.get(1)) || !"cherry".equals(v.get(2))) {
            throw new AssertionError("Vector.sort did not sort elements correctly");
        }
    }

    private static void testSortWithNullComparator() {
        Vector<Integer> v = new Vector<>();
        v.add(3);
        v.add(1);
        v.add(2);

        // Sort with null comparator should use natural ordering
        v.sort(null);

        if (v.get(0) != 1 || v.get(1) != 2 || v.get(2) != 3) {
            throw new AssertionError("Vector.sort with null comparator did not sort elements correctly");
        }
    }

    private static void testSortConcurrentModification() {
        final Vector<Integer> v = new Vector<>();
        v.add(1);
        v.add(2);
        v.add(3);

        // Create a thread that modifies the vector during sort to trigger ConcurrentModificationException
        Thread modifier = new Thread(new Runnable() {
            public void run() {
                try {
                    // Sleep briefly to let sort start
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    // Ignore
                }
                v.add(4); // Structural modification during sort
            }
        });

        modifier.start();

        boolean caught = false;
        try {
            v.sort(null);
        } catch (ConcurrentModificationException e) {
            caught = true;
        }

        try {
            modifier.join();
        } catch (InterruptedException e) {
            // Ignore
        }

        if (!caught) {
            throw new AssertionError("Expected ConcurrentModificationException was not thrown during Vector.sort");
        }
    }
}
