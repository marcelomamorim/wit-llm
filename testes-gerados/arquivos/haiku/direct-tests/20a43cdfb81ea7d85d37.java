/*
 * @test
 * @summary Verify indexOf behavior of immutable lists returned by List.of
 * @run main ImmutableCollectionsWitupTest
 */

import java.util.List;

public class ImmutableCollectionsWitupTest {
    static int failures = 0;

    static void check(boolean cond, String msg) {
        if (!cond) {
            failures++;
            System.err.println("FAILED: " + msg);
        }
    }

    public static void main(String[] args) {
        List<String> empty = List.of();
        check(empty.indexOf("x") == -1, "empty list indexOf must return -1");

        List<String> one = List.of("a");
        check(one.indexOf("a") == 0, "single element found at index 0");
        check(one.indexOf("b") == -1, "single element missing returns -1");

        List<String> two = List.of("a", "b");
        check(two.indexOf("a") == 0, "first element of two-element list");
        check(two.indexOf("b") == 1, "second element of two-element list");
        check(two.indexOf("c") == -1, "absent element in two-element list returns -1");

        List<Integer> four = List.of(1, 2, 3, 4);
        check(four.indexOf(3) == 2, "element at index 2 must be found at 2");
        check(four.indexOf(Integer.valueOf(4)) == 3, "last element must be found at 3");
        check(four.indexOf(Integer.valueOf(9)) == -1, "absent integer returns -1");

        List<String> dup = List.of("a", "b", "c", "a");
        check(dup.indexOf("a") == 0, "indexOf must return first occurrence");

        List<String> typed = List.of("1", "2");
        check(typed.indexOf(Integer.valueOf(1)) == -1, "equals must be type sensitive");

        try {
            four.indexOf(null);
            failures++;
            System.err.println("FAILED: indexOf(null) must throw NullPointerException");
        } catch (NullPointerException expected) {
            // expected
        }

        try {
            two.indexOf(null);
            failures++;
            System.err.println("FAILED: indexOf(null) on two-element list must throw NullPointerException");
        } catch (NullPointerException expected) {
            // expected
        }

        if (failures > 0) {
            throw new RuntimeException(failures + " check(s) failed");
        }
    }
}
