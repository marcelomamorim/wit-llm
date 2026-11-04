/*
 * @test
 * @summary Verifies that sort on an unmodifiable list view throws UnsupportedOperationException and leaves the backing order unchanged
 * @run main CollectionsWitupTest
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

public class CollectionsWitupTest {

    public static void main(String[] args) {
        testUnmodifiableRandomAccessListSortThrows();
        testUnmodifiableSequentialListSortThrows();
        testSortWithNullComparatorThrows();
        testOrderUnchangedAfterFailedSort();
    }

    static void testUnmodifiableRandomAccessListSortThrows() {
        List<Integer> backing = new ArrayList<>(Arrays.asList(3, 1, 2));
        List<Integer> view = Collections.unmodifiableList(backing);
        try {
            view.sort(Comparator.naturalOrder());
            throw new RuntimeException("Expected UnsupportedOperationException");
        } catch (UnsupportedOperationException expected) {
            // expected
        }
    }

    static void testUnmodifiableSequentialListSortThrows() {
        List<Integer> backing = new LinkedList<>(Arrays.asList(3, 1, 2));
        List<Integer> view = Collections.unmodifiableList(backing);
        try {
            view.sort(Comparator.naturalOrder());
            throw new RuntimeException("Expected UnsupportedOperationException");
        } catch (UnsupportedOperationException expected) {
            // expected
        }
    }

    static void testSortWithNullComparatorThrows() {
        List<String> view = Collections.unmodifiableList(new ArrayList<>(Arrays.asList("b", "a")));
        try {
            view.sort(null);
            throw new RuntimeException("Expected UnsupportedOperationException");
        } catch (UnsupportedOperationException expected) {
            // expected
        }
    }

    static void testOrderUnchangedAfterFailedSort() {
        List<Integer> backing = new ArrayList<>(Arrays.asList(3, 1, 2));
        List<Integer> view = Collections.unmodifiableList(backing);
        try {
            view.sort(Comparator.naturalOrder());
        } catch (UnsupportedOperationException expected) {
            // expected
        }
        if (!view.equals(Arrays.asList(3, 1, 2))) {
            throw new RuntimeException("Unmodifiable view order changed: " + view);
        }
        if (!backing.equals(Arrays.asList(3, 1, 2))) {
            throw new RuntimeException("Backing list order changed: " + backing);
        }
    }
}
