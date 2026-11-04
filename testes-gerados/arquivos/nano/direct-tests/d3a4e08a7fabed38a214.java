/* @test    @summary Test for Arrays.equals(boolean[], boolean[]) method    @run main ArraysWitupTest */

import java.util.Arrays;

public class ArraysWitupTest {

    public static void main(String[] args) {
        testEqualsWithIdenticalArrays();
        testEqualsWithNullArrays();
        testEqualsWithDifferentLengths();
        testEqualsWithDifferentContents();
    }

    static void testEqualsWithIdenticalArrays() {
        boolean[] array1 = {true, false, true};
        boolean[] array2 = {true, false, true};
        boolean result = Arrays.equals(array1, array2);
        if (!result) {
            throw new AssertionError("Expected true for identical arrays");
        }
    }

    static void testEqualsWithNullArrays() {
        boolean[] array1 = null;
        boolean[] array2 = {true, false};
        boolean[] array3 = null;
        boolean result1 = Arrays.equals(array1, array2);
        boolean result2 = Arrays.equals(array2, array3);
        boolean result3 = Arrays.equals(array1, array3);
        if (result1 || result2 || result3) {
            throw new AssertionError("Expected false when one or both arrays are null");
        }
    }

    static void testEqualsWithDifferentLengths() {
        boolean[] array1 = {true, false};
        boolean[] array2 = {true, false, true};
        boolean result = Arrays.equals(array1, array2);
        if (result) {
            throw new AssertionError("Expected false for arrays of different lengths");
        }
    }

    static void testEqualsWithDifferentContents() {
        boolean[] array1 = {true, false, true};
        boolean[] array2 = {true, true, false};
        boolean result = Arrays.equals(array1, array2);
        if (result) {
            throw new AssertionError("Expected false for arrays with different contents");
        }
    }
}
