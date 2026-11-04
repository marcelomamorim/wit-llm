/* @test    @summary Test for StringUTF16.stripTrailing(byte[] value) method to verify behavior and exception handling    @run main StringUTF16WitupTest */

import java.lang.StringUTF16;

public class StringUTF16WitupTest {

    public static void main(String[] args) {
        testStripTrailingWithNull();
        testStripTrailingWithEmptyArray();
        testStripTrailingWithWhitespace();
        testStripTrailingWithNonWhitespace();
        testStripTrailingWithNullArray();
    }

    private static void testStripTrailingWithNull() {
        try {
            StringUTF16.stripTrailing(null);
            System.out.println("FAIL: Expected NullPointerException for null input");
        } catch (NullPointerException e) {
            System.out.println("PASS: NullPointerException thrown as expected");
        } catch (Exception e) {
            System.out.println("FAIL: Unexpected exception: " + e);
        }
    }

    private static void testStripTrailingWithEmptyArray() {
        byte[] empty = new byte[0];
        String result = null;
        try {
            result = StringUTF16.stripTrailing(empty);
            if (result != null && result.equals("") || result == null) {
                System.out.println("PASS: Empty array returns empty string or null");
            } else {
                System.out.println("FAIL: Unexpected result for empty array: " + result);
            }
        } catch (Exception e) {
            System.out.println("FAIL: Exception for empty array: " + e);
        }
    }

    private static void testStripTrailingWithWhitespace() {
        byte[] whitespaceBytes = {32, 9, 10, 13}; // space, tab, newline, carriage return
        String result = null;
        try {
            result = StringUTF16.stripTrailing(whitespaceBytes);
            if (result != null && result.equals("") || result == null) {
                System.out.println("PASS: Whitespace array returns empty string or null");
            } else {
                System.out.println("FAIL: Unexpected result for whitespace array: " + result);
            }
        } catch (Exception e) {
            System.out.println("FAIL: Exception for whitespace array: " + e);
        }
    }

    private static void testStripTrailingWithNonWhitespace() {
        byte[] nonWhitespaceBytes = {65, 66, 67}; // 'A', 'B', 'C'
        String result = null;
        try {
            result = StringUTF16.stripTrailing(nonWhitespaceBytes);
            if (result != null && result.equals("ABC")) {
                System.out.println("PASS: Non-whitespace array returns original string");
            } else {
                System.out.println("FAIL: Unexpected result for non-whitespace array: " + result);
            }
        } catch (Exception e) {
            System.out.println("FAIL: Exception for non-whitespace array: " + e);
        }
    }

    private static void testStripTrailingWithNullArray() {
        try {
            String result = StringUTF16.stripTrailing(null);
            System.out.println("FAIL: Expected NullPointerException for null array, but got: " + result);
        } catch (NullPointerException e) {
            System.out.println("PASS: NullPointerException thrown as expected for null array");
        } catch (Exception e) {
            System.out.println("FAIL: Unexpected exception for null array: " + e);
        }
    }
}
