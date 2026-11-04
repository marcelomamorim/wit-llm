/* @test    @summary Test for String(byte[], int) constructor with various inputs    @run main StringWitupTest */

import java.lang.String;

public class StringWitupTest {

    public static void main(String[] args) {
        testNullCharsetName();
        testEmptyByteArray();
        testNegativeOffset();
        testOffsetExceedsArrayLength();
        testValidByteArray();
    }

    private static void testNullCharsetName() {
        byte[] bytes = {65, 66, 67}; // 'ABC'
        int hibyte = 0;
        try {
            String s = new String(bytes, hibyte, 0, "null");
            // Expect NullPointerException
            System.out.println("FAIL: Expected NullPointerException for null charsetName");
        } catch (NullPointerException e) {
            System.out.println("PASS: NullPointerException caught as expected");
        } catch (Exception e) {
            System.out.println("FAIL: Unexpected exception: " + e);
        }
    }

    private static void testEmptyByteArray() {
        byte[] bytes = {};
        int hibyte = 0;
        try {
            String s = new String(bytes, hibyte, 0, "UTF-8");
            if (s.equals("
)) {
                System.out.println("PASS: Empty byte array produces empty string");
            } else {
                System.out.println("FAIL: Empty byte array did not produce empty string");
            }
        } catch (Exception e) {
            System.out.println("FAIL: Unexpected exception: " + e);
        }
    }

    private static void testNegativeOffset() {
        byte[] bytes = {65, 66, 67};
        int hibyte = 0;
        try {
            String s = new String(bytes, hibyte, -1, "UTF-8");
            System.out.println("FAIL: Expected StringIndexOutOfBoundsException for negative offset");
        } catch (StringIndexOutOfBoundsException e) {
            System.out.println("PASS: StringIndexOutOfBoundsException caught as expected");
        } catch (Exception e) {
            System.out.println("FAIL: Unexpected exception: " + e);
        }
    }

    private static void testOffsetExceedsArrayLength() {
        byte[] bytes = {65, 66, 67};
        int hibyte = 0;
        try {
            String s = new String(bytes, hibyte, 4, "UTF-8");
            System.out.println("FAIL: Expected StringIndexOutOfBoundsException for offset exceeding array length");
        } catch (StringIndexOutOfBoundsException e) {
            System.out.println("PASS: StringIndexOutOfBoundsException caught as expected");
        } catch (Exception e) {
            System.out.println("FAIL: Unexpected exception: " + e);
        }
    }

    private static void testValidByteArray() {
        byte[] bytes = {65, 66, 67}; // 'ABC'
        int hibyte = 0;
        try {
            String s = new String(bytes, hibyte, bytes.length, "UTF-8");
            if (s.equals("ABC")) {
                System.out.println("PASS: Valid byte array correctly converted to string: " + s);
            } else {
                System.out.println("FAIL: Unexpected string: " + s);
            }
        } catch (Exception e) {
            System.out.println("FAIL: Unexpected exception: " + e);
        }
    }
}
