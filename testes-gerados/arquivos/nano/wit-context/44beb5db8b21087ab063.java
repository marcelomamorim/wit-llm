/*
 * @test
 * @summary Tests for java.util.Arrays.fill(char[], int, int, char)
 * @run main ArraysWitupTest
 */

import java.util.Arrays;

public class ArraysWitupTest {

    public static void main(String[] args) {
        testFill_Normal();
        testFill_FromIndexGreaterThanToIndex();
        testFill_FromIndexNegative();
        testFill_ToIndexExceedsLength();
    }

    static void testFill_Normal() {
        char[] array = {'a', 'b', 'c', 'd'};
        Arrays.fill(array, 1, 3, 'x');
        assert array[0] == 'a';
        assert array[1] == 'x';
        assert array[2] == 'x';
        assert array[3] == 'd';
    }

    static void testFill_FromIndexGreaterThanToIndex() {
        char[] array = {'a', 'b', 'c'};
        try {
            Arrays.fill(array, 2, 1, 'z');
            assert false : "Expected IllegalArgumentException";
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    static void testFill_FromIndexNegative() {
        char[] array = {'a', 'b', 'c'};
        try {
            Arrays.fill(array, -1, 2, 'z');
        } catch (ArrayIndexOutOfBoundsException e) {
            // expected
        }
    }

    static void testFill_ToIndexExceedsLength() {
        char[] array = {'a', 'b', 'c'};
        try {
            Arrays.fill(array, 1, 5, 'z');
        } catch (ArrayIndexOutOfBoundsException e) {
            // expected
        }
    }
}
