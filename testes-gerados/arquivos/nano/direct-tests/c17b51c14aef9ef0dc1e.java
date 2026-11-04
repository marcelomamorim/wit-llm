/* @test    @summary Test Collections.subList with valid indices and boundary cases    @run main CollectionsWitupTest */

import java.util.*;

public class CollectionsWitupTest {

    public static void main(String[] args) {
        testSubListValidRange();
        testSubListFromZero();
        testSubListToEnd();
        testSubListInvalidFromIndex();
        testSubListInvalidToIndex();
        testSubListFromIndexGreaterThanTo();
    }

    static void testSubListValidRange() {
        List<Integer> list = Arrays.asList(1, 2, 3, 4, 5);
        List<Integer> sub = Collections.subList(1, 4);
        if (sub.size() != 3) {
            throw new AssertionError("Expected size 3, got " + sub.size());
        }
        if (!sub.equals(Arrays.asList(2, 3, 4))) {
            throw new AssertionError("Unexpected sublist content: " + sub);
        }
    }

    static void testSubListFromZero() {
        List<String> list = Arrays.asList("a", "b", "c");
        List<String> sub = Collections.subList(0, 2);
        if (sub.size() != 2) {
            throw new AssertionError("Expected size 2, got " + sub.size());
        }
        if (!sub.equals(Arrays.asList("a", "b"))) {
            throw new AssertionError("Unexpected sublist content: " + sub);
        }
    }

    static void testSubListToEnd() {
        List<Double> list = Arrays.asList(1.1, 2.2, 3.3);
        List<Double> sub = Collections.subList(0, 3);
        if (sub.size() != 3) {
            throw new AssertionError("Expected size 3, got " + sub.size());
        }
        if (!sub.equals(Arrays.asList(1.1, 2.2, 3.3))) {
            throw new AssertionError("Unexpected sublist content: " + sub);
        }
    }

    static void testSubListInvalidFromIndex() {
        List<Integer> list = Arrays.asList(1, 2, 3);
        try {
            Collections.subList(-1, 2);
            throw new AssertionError("Expected IndexOutOfBoundsException for fromIndex < 0");
        } catch (IndexOutOfBoundsException e) {
            // expected
        }
    }

    static void testSubListInvalidToIndex() {
        List<Integer> list = Arrays.asList(1, 2, 3);
        try {
            Collections.subList(0, 4);
            throw new AssertionError("Expected IndexOutOfBoundsException for toIndex > size");
        } catch (IndexOutOfBoundsException e) {
            // expected
        }
    }

    static void testSubListFromIndexGreaterThanTo() {
        List<Integer> list = Arrays.asList(1, 2, 3);
        try {
            Collections.subList(2, 1);
            throw new AssertionError("Expected IllegalArgumentException for fromIndex > toIndex");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }
}
