/*
 * @test
 * @summary Verifies removeIf on ArrayList.subList: range-limited removal, false when nothing matches, and ConcurrentModificationException after a structural change to the backing list.
 * @run main ArrayListWitupTest
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.ConcurrentModificationException;
import java.util.List;

public class ArrayListWitupTest {

    public static void main(String[] args) {
        testRemovesOnlyWithinRange();
        testNoMatchReturnsFalse();
        testComodificationDetected();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new AssertionError(message);
        }
    }

    static void testRemovesOnlyWithinRange() {
        ArrayList<Integer> list = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5, 6));
        List<Integer> sub = list.subList(1, 5);
        boolean modified = sub.removeIf(x -> x % 2 == 0);
        check(modified, "removeIf should report modification");
        check(sub.equals(Arrays.asList(3, 5)), "sublist content wrong: " + sub);
        check(list.equals(Arrays.asList(1, 3, 5, 6)), "backing list content wrong: " + list);
        check(list.size() == 4, "backing list size wrong");
    }

    static void testNoMatchReturnsFalse() {
        ArrayList<Integer> list = new ArrayList<>(Arrays.asList(1, 3, 5));
        boolean modified = list.subList(0, 3).removeIf(x -> x > 10);
        check(!modified, "removeIf should return false when nothing matches");
        check(list.equals(Arrays.asList(1, 3, 5)), "list should be unchanged");
    }

    static void testComodificationDetected() {
        ArrayList<Integer> list = new ArrayList<>(Arrays.asList(1, 2, 3));
        List<Integer> sub = list.subList(0, 2);
        list.add(4);
        try {
            sub.removeIf(x -> true);
            throw new AssertionError("expected ConcurrentModificationException");
        } catch (ConcurrentModificationException expected) {
            // expected
        }
    }
}
