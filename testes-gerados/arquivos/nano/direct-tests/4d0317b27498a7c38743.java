/*
 * @test
 * @summary Tests for StringLatin1.fillNull method.
 * @run main StringLatin1WitupTest
 */

import java.util.Arrays;

public class StringLatin1WitupTest {

    public static void main(String[] args) {
        testFillNullWithEmptyArray();
        testFillNullWithSingleElement();
        testFillNullWithMultipleElements();
        testFillNullWithNullArray();
        testFillNullWithNegativeIndices();
        testFillNullWithEndLessThanStart();
    }

    static void testFillNullWithEmptyArray() {
        byte[] arr = new byte[0];
        try {
            StringLatin1.fillNull(arr, 0, 0);
            assert arr.length == 0 : "Array should remain empty";
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception for empty array", e);
        }
    }

    static void testFillNullWithSingleElement() {
        byte[] arr = {1};
        StringLatin1.fillNull(arr, 0, 1);
        assert arr[0] == 0 : "Element should be set to 0";
    }

    static void testFillNullWithMultipleElements() {
        byte[] arr = {1, 2, 3, 4, 5};
        StringLatin1.fillNull(arr, 1, 4);
        assert arr[0] == 1 : "First element unchanged";
        assert arr[1] == 0 : "Index 1 should be 0";
        assert arr[2] == 0 : "Index 2 should be 0";
        assert arr[3] == 0 : "Index 3 should be 0";
        assert arr[4] == 5 : "Last element unchanged";
    }

    static void testFillNullWithNullArray() {
        byte[] arr = null;
        try {
            StringLatin1.fillNull(arr, 0, 1);
            throw new AssertionError("Expected NullPointerException for null array");
        } catch (NullPointerException e) {
            // expected
        }
    }

    static void testFillNullWithNegativeIndices() {
        byte[] arr = {1, 2, 3};
        try {
            StringLatin1.fillNull(arr, -1, 2);
            throw new AssertionError("Expected ArrayIndexOutOfBoundsException for negative index");
        } catch (ArrayIndexOutOfBoundsException e) {
            // expected
        }
        try {
            StringLatin1.fillNull(arr, 0, -2);
            throw new AssertionError("Expected ArrayIndexOutOfBoundsException for negative end");
        } catch (ArrayIndexOutOfBoundsException e) {
            // expected
        }
    }

    static void testFillNullWithEndLessThanStart() {
        byte[] arr = {1, 2, 3};
        try {
            StringLatin1.fillNull(arr, 2, 1);
            // No exception expected, but the range is empty, so no fill occurs
            assert arr[2] == 3 : "Element should remain unchanged";
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception for end less than start", e);
        }
    }
}
