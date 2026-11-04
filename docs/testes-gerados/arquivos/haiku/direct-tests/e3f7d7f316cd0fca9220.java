/*
 * @test
 * @summary Observable Spliterator.getComparator() behavior for sorted and unsorted sources
 * @run main StreamsWitupTest
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Spliterator;
import java.util.TreeSet;

public class StreamsWitupTest {

    public static void main(String[] args) {
        Spliterator<Integer> unsorted = new ArrayList<Integer>(Arrays.asList(1, 2, 3)).spliterator();
        boolean thrown = false;
        try {
            unsorted.getComparator();
        } catch (IllegalStateException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new AssertionError("IllegalStateException expected for unsorted spliterator");
        }

        Comparator<Integer> cmp = Comparator.reverseOrder();
        Spliterator<Integer> sorted = new TreeSet<Integer>(cmp).spliterator();
        if (sorted.getComparator() != cmp) {
            throw new AssertionError("comparator identity mismatch for sorted spliterator");
        }
    }
}
