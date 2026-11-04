/*
 * @test
 * @summary Tests for java.util.Collections.subList(int, int)
 * @run main CollectionsWitupTest
 */

import java.util.List;
import java.util.Collections;

public class CollectionsWitupTest {

    public static void main(String[] args) {
        testSubListNegativeFromIndex();
        testSubListFromIndexGreaterThanToIndex();
        testSubListToIndexExceedsSize();
        System.out.println("All tests passed.");
    }

    private static void testSubListNegativeFromIndex() {
        List<Integer> list = List.of(1, 2, 3, 4, 5);
        try {
            List<Integer> sub = Collections.subList(list.size() / 2, list.size());
            // Should not throw
            if (sub.size() != list.size() - list.size() / 2) {
                throw new AssertionError("Unexpected sublist size");
            }
        } catch (IndexOutOfBoundsException e) {
            throw new AssertionError("Unexpected exception for valid indices", e);
        }
        try {
            List<Integer> sub = Collections.subList(-1, 3);
            throw new AssertionError("Expected IndexOutOfBoundsException for negative fromIndex");
        } catch (IndexOutOfBoundsException e) {
            // Expected
        }
    }

    private static void testSubListFromIndexGreaterThanToIndex() {
        List<Integer> list = List.of(1, 2, 3, 4, 5);
        try {
            List<Integer> sub = Collections.subList(3, 2);
            throw new AssertionError("Expected IllegalArgumentException for fromIndex > toIndex");
        } catch (IllegalArgumentException e) {
            // Expected
        }
    }

    private static void testSubListToIndexExceedsSize() {
        List<Integer> list = List.of(1, 2, 3, 4, 5);
        try {
            List<Integer> sub = Collections.subList(0, list.size() + 1);
            throw new AssertionError("Expected IndexOutOfBoundsException for toIndex > size");
        } catch (IndexOutOfBoundsException e) {
            // Expected
        }
    }
}
