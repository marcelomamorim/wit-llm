/*
 * @test
 * @summary Tests for StringUTF16.codePointCountSB method.
 * @run main StringUTF16WitupTest
 */

import java.lang.StringUTF16;

public class StringUTF16WitupTest {

    public static void main(String[] args) {
        testNullArray();
        testNegativeIndices();
        testValidIndices();
        testEmptyArray();
        testFullArray();
        testPartialArray();
    }

    static void testNullArray() {
        try {
            StringUTF16.codePointCountSB(null, 0, 0);
            System.out.println("FAIL: Expected NullPointerException");
        } catch (NullPointerException e) {
            System.out.println("PASS: NullPointerException caught as expected");
        } catch (Exception e) {
            System.out.println("FAIL: Unexpected exception: " + e);
        }
    }

    static void testNegativeIndices() {
        byte[] array = new byte[4];
        try {
            StringUTF16.codePointCountSB(array, -1, 2);
            System.out.println("FAIL: Expected ArrayIndexOutOfBoundsException for beginIndex");
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("PASS: beginIndex negative caught as expected");
        } catch (Exception e) {
            System.out.println("FAIL: Unexpected exception: " + e);
        }
        try {
            StringUTF16.codePointCountSB(array, 0, -1);
            System.out.println("FAIL: Expected ArrayIndexOutOfBoundsException for endIndex");
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("PASS: endIndex negative caught as expected");
        } catch (Exception e) {
            System.out.println("FAIL: Unexpected exception: " + e);
        }
    }

    static void testValidIndices() {
        byte[] array = new byte[4];
        try {
            int count = StringUTF16.codePointCountSB(array, 0, 4);
            System.out.println("PASS: Valid indices, returned: " + count);
        } catch (Exception e) {
            System.out.println("FAIL: Unexpected exception: " + e);
        }
    }

    static void testEmptyArray() {
        byte[] array = new byte[0];
        int count = StringUTF16.codePointCountSB(array, 0, 0);
        if (count == 0) {
            System.out.println("PASS: Empty array returns 0");
        } else {
            System.out.println("FAIL: Empty array returned: " + count);
        }
    }

    static void testFullArray() {
        byte[] array = new byte[8];
        int count = StringUTF16.codePointCountSB(array, 0, array.length);
        System.out.println("Full array count: " + count);
    }

    static void testPartialArray() {
        byte[] array = new byte[8];
        int count = StringUTF16.codePointCountSB(array, 2, 6);
        System.out.println("Partial array count: " + count);
    }
}
