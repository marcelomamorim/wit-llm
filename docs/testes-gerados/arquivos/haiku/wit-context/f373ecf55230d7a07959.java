/*
 * @test
 * @summary Tests observable behavior of Arrays.equals(short[], short[])
 * @run main ArraysWitupTest
 */

import java.util.Arrays;

public class ArraysWitupTest {

    public static void main(String[] args) {
        short[] a = {1, 2, 3};
        short[] same = a;
        short[] equalCopy = {1, 2, 3};
        short[] differentLast = {1, 2, 4};
        short[] shorter = {1, 2};
        short[] longer = {1, 2, 3, 4};
        short[] empty1 = {};
        short[] empty2 = {};
        short[] extremes1 = {Short.MIN_VALUE};
        short[] extremes2 = {Short.MAX_VALUE};
        short[] negative1 = {-1};
        short[] negative2 = {-1};
        short[] firstDiffers = {9, 2, 3};

        check(Arrays.equals((short[]) null, (short[]) null), true, "null, null");
        check(Arrays.equals(a, (short[]) null), false, "array, null");
        check(Arrays.equals((short[]) null, a), false, "null, array");
        check(Arrays.equals(a, same), true, "same reference");
        check(Arrays.equals(a, equalCopy), true, "equal contents");
        check(Arrays.equals(a, Arrays.copyOf(a, a.length)), true, "copyOf contents");
        check(Arrays.equals(a, differentLast), false, "different last element");
        check(Arrays.equals(a, firstDiffers), false, "different first element");
        check(Arrays.equals(a, shorter), false, "shorter array");
        check(Arrays.equals(shorter, a), false, "shorter array reversed");
        check(Arrays.equals(a, longer), false, "longer array");
        check(Arrays.equals(longer, a), false, "longer array reversed");
        check(Arrays.equals(empty1, empty2), true, "empty arrays");
        check(Arrays.equals(empty1, a), false, "empty versus non-empty");
        check(Arrays.equals(negative1, negative2), true, "negative values");
        check(Arrays.equals(extremes1, extremes2), false, "min versus max short");
        check(Arrays.equals(a, differentLast) == Arrays.equals(differentLast, a), true, "symmetry");
    }

    static void check(boolean actual, boolean expected, String message) {
        if (actual != expected) {
            throw new RuntimeException("Unexpected result for " + message);
        }
    }
}
