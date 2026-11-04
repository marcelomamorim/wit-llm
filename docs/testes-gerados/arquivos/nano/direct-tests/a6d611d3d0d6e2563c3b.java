/* @test    @summary Test for Arrays.fill method with valid and edge cases    @run main ArraysWitupTest */

import java.util.Arrays;

public class ArraysWitupTest {

    public static void main(String[] args) {
        testFillNormalRange();
        testFillFullArray();
        testFillEmptyRange();
        testFillSingleElement();
        testFillInvalidRange();
    }

    static void testFillNormalRange() {
        int[] array = {1, 2, 3, 4, 5};
        Arrays.fill(array, 1, 4, 99);
        assert array[0] == 1 : "Index 0 should remain 1";
        assert array[1] == 99 : "Index 1 should be 99";
        assert array[2] == 99 : "Index 2 should be 99";
        assert array[3] == 99 : "Index 3 should be 99";
        assert array[4] == 5 : "Index 4 should remain 5";
    }

    static void testFillFullArray() {
        int[] array = {0, 0, 0};
        Arrays.fill(array, 0, array.length, 7);
        for (int value : array) {
            assert value == 7 : "All elements should be 7";
        }
    }

    static void testFillEmptyRange() {
        int[] array = {1, 2, 3};
        Arrays.fill(array, 2, 2, 42); // no change expected
        assert array[0] == 1 : "Index 0 should remain 1";
        assert array[1] == 2 : "Index 1 should remain 2";
        assert array[2] == 3 : "Index 2 should remain 3";
    }

    static void testFillSingleElement() {
        int[] array = {5, 6, 7};
        Arrays.fill(array, 1, 2, 88); // only index 1
        assert array[0] == 5 : "Index 0 should remain 5";
        assert array[1] == 88 : "Index 1 should be 88";
        assert array[2] == 7 : "Index 2 should remain 7";
    }

    static void testFillInvalidRange() {
        int[] array = {1, 2, 3};
        try {
            Arrays.fill(array, -1, 2, 9); // fromIndex negative
            assert false : "Expected ArrayIndexOutOfBoundsException for negative fromIndex";
        } catch (ArrayIndexOutOfBoundsException e) {
            // expected
        }
        try {
            Arrays.fill(array, 1, 4, 9); // toIndex beyond array length
            assert false : "Expected ArrayIndexOutOfBoundsException for toIndex beyond length";
        } catch (ArrayIndexOutOfBoundsException e) {
            // expected
        }
        try {
            Arrays.fill(array, 2, 1, 9); // fromIndex > toIndex
            // According to source, no exception thrown, but no change expected
            int[] expected = {1, 2, 3};
            assert java.util.Arrays.equals(array, expected) : "Array should remain unchanged when fromIndex > toIndex";
        } catch (Exception e) {
            assert false : "No exception expected when fromIndex > toIndex";
        }
    }
}
