/*
 * @test
 * @summary Verifies observable behavior of Arrays.equals(char[], char[])
 * @run main ArraysWitupTest
 */

import java.util.Arrays;

public class ArraysWitupTest {

    public static void main(String[] args) {
        char[] a = {'a', 'b', 'c'};

        check(Arrays.equals(a, a), "same reference must be equal");
        check(Arrays.equals(null, null), "two nulls must be equal");
        check(!Arrays.equals(a, null), "array vs null must not be equal");
        check(!Arrays.equals(null, a), "null vs array must not be equal");
        check(!Arrays.equals(new char[0], null), "empty array vs null must not be equal");
        check(Arrays.equals(new char[0], new char[0]), "two empty arrays must be equal");
        check(Arrays.equals(a, new char[] {'a', 'b', 'c'}), "equal content must be equal");
        check(!Arrays.equals(a, new char[] {'a', 'b'}), "different length must not be equal");
        check(!Arrays.equals(a, new char[] {'x', 'b', 'c'}), "difference at first element must be detected");
        check(!Arrays.equals(a, new char[] {'a', 'b', 'x'}), "difference at last element must be detected");
        check(!Arrays.equals(new char[] {'a'}, new char[] {'b'}), "single differing element must be detected");

        char[] big1 = new char[100];
        char[] big2 = new char[100];
        for (int i = 0; i < big1.length; i++) {
            big1[i] = 'z';
            big2[i] = 'z';
        }
        check(Arrays.equals(big1, big2), "long equal arrays must be equal");
        big2[99] = 'y';
        check(!Arrays.equals(big1, big2), "mismatch at end of long arrays must be detected");
        big2[99] = 'z';
        big2[0] = 'y';
        check(!Arrays.equals(big1, big2), "mismatch at start of long arrays must be detected");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException("Failed: " + message);
        }
    }
}
