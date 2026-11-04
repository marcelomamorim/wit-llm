/*
 * @test
 * @summary Witup-generated behavioral tests for Arrays.equals(boolean[], boolean[])
 * @run main ArraysWitupTest
 */

import java.util.Arrays;

public class ArraysWitupTest {

    public static void main(String[] args) {
        boolean[] a = {true, false, true};
        boolean[] b = {true, false, true};

        check(Arrays.equals(a, a), true, "same reference");
        check(Arrays.equals(a, b), true, "equal contents");
        check(Arrays.equals(a, new boolean[]{true, true, true}), false, "differs at index 1");
        check(Arrays.equals(a, new boolean[]{true, false}), false, "different length (shorter)");
        check(Arrays.equals(a, new boolean[]{true, false, true, false}), false, "different length (longer)");
        check(Arrays.equals(null, null), true, "both null");
        check(Arrays.equals(a, null), false, "first non-null, second null");
        check(Arrays.equals(null, a), false, "first null, second non-null");
        check(Arrays.equals(new boolean[0], new boolean[0]), true, "both empty");
        check(Arrays.equals(new boolean[0], a), false, "empty vs non-empty");
    }

    static void check(boolean actual, boolean expected, String msg) {
        if (actual != expected) {
            throw new RuntimeException(msg + ": expected " + expected + " but got " + actual);
        }
    }
}
