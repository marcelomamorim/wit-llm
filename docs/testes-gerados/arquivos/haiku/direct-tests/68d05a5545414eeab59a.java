/*
 * @test
 * @summary Tests lastIndexOf on immutable lists obtained through List.of
 * @run main ImmutableCollectionsWitupTest
 */

import java.util.List;

public class ImmutableCollectionsWitupTest {
    public static void main(String[] args) {
        List<String> list = List.of("a", "b", "a");
        check(list.lastIndexOf("a") == 2, "last occurrence of a should be 2");
        check(list.lastIndexOf("b") == 1, "last occurrence of b should be 1");
        check(list.lastIndexOf("z") == -1, "absent element should return -1");

        List<Integer> nums = List.of(1, 2, 3, 2);
        check(nums.lastIndexOf(2) == 3, "last occurrence of 2 should be 3");
        check(nums.lastIndexOf(4) == -1, "absent integer should return -1");

        List<String> one = List.of("x");
        check(one.lastIndexOf("x") == 0, "single element should be found at index 0");
        check(one.lastIndexOf("y") == -1, "absent element in single list should return -1");

        List<String> empty = List.of();
        check(empty.lastIndexOf("a") == -1, "empty list should return -1");

        check(throwsNpe(list), "null argument should throw NullPointerException (ListN)");
        check(throwsNpe(one), "null argument should throw NullPointerException (List12)");
    }

    static boolean throwsNpe(List<String> l) {
        try {
            l.lastIndexOf(null);
            return false;
        } catch (NullPointerException e) {
            return true;
        }
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
