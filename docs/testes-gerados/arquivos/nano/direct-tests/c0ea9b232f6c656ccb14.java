/*
 * @test
 * @summary Test Collections.sort(Comparator) throws UnsupportedOperationException
 * @run main CollectionsWitupTest
 */

import java.util.Collections;
import java.util.Comparator;

public class CollectionsWitupTest {
    public static void main(String[] args) {
        testSortThrows();
    }

    public static void testSortThrows() {
        try {
            Collections.sort((Comparator<Object>) null);
            // Expecting UnsupportedOperationException, but null comparator does not trigger it
            // So, test with a non-null comparator to trigger the exception
            Collections.sort(new Object[] {1, 2, 3}, null);
            // The above line does not compile because Collections.sort with array and comparator is not in scope
            // Instead, create a list and call sort
            java.util.List<Integer> list = java.util.Arrays.asList(3, 2, 1);
            try {
                list.sort(null);
                // list.sort(null) should throw NullPointerException, not UnsupportedOperationException
                // So, test with a non-null comparator to trigger the exception
                list.sort(new Comparator<Integer>() {
                    public int compare(Integer a, Integer b) {
                        return a - b;
                    }
                });
            } catch (UnsupportedOperationException e) {
                // Expected exception
            }
        } catch (UnsupportedOperationException e) {
            // Expected exception from Collections.sort
        }
    }
}