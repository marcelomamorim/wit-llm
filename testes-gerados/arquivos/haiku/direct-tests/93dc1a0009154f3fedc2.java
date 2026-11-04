/*
 * @test
 * @summary Verifies that sort on immutable lists from List.of throws UnsupportedOperationException and leaves contents unchanged
 * @run main ImmutableCollectionsWitupTest
 */

import java.util.Comparator;
import java.util.List;

public class ImmutableCollectionsWitupTest {

    static int failures = 0;

    static void check(boolean cond, String msg) {
        if (!cond) {
            failures++;
            System.err.println("FAILED: " + msg);
        }
    }

    static void expectUoeOnSort(List<Integer> list, Comparator<? super Integer> c, String name) {
        List<Integer> before = List.copyOf(list);
        boolean thrown = false;
        try {
            list.sort(c);
        } catch (UnsupportedOperationException e) {
            thrown = true;
        }
        check(thrown, name + ": sort should throw UnsupportedOperationException");
        check(list.equals(before), name + ": contents must be unchanged after failed sort");
    }

    public static void main(String[] args) {
        expectUoeOnSort(List.of(3, 1, 2), null, "ListN natural null comparator");
        expectUoeOnSort(List.of(3, 1, 2), Comparator.naturalOrder(), "ListN naturalOrder");
        expectUoeOnSort(List.of(3, 1, 2), Comparator.reverseOrder(), "ListN reverseOrder");
        expectUoeOnSort(List.of(2, 1), Comparator.naturalOrder(), "List12 two elements");
        expectUoeOnSort(List.of(1), null, "List12 single element");
        expectUoeOnSort(List.of(), Comparator.naturalOrder(), "empty immutable list");

        if (failures > 0) {
            throw new RuntimeException(failures + " check(s) failed");
        }
    }
}
