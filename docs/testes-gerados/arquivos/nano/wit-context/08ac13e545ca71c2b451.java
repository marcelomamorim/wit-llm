/*
 * @test
 * @summary Test for String(byte[], int, int) constructor, focusing on exception paths.
 * @run main StringWitupTest
 */

import java.lang.String;

public class StringWitupTest {

    public static void main(String[] args) {
        testStringConstructorWithNegativeLength();
        testStringConstructorWithOffsetExceedingArray();
        testStringConstructorWithValidInputs();
    }

    private static void testStringConstructorWithNegativeLength() {
        byte[] bytes = {65, 66, 67}; // "ABC"
        int offset = 0;
        int length = -1; // invalid length
        try {
            new String(bytes, offset, length);
            System.out.println("FAIL: Expected StringIndexOutOfBoundsException for negative length");
        } catch (StringIndexOutOfBoundsException e) {
            System.out.println("PASS: Caught expected StringIndexOutOfBoundsException for negative length");
        } catch (Throwable t) {
            System.out.println("FAIL: Caught unexpected exception: " + t);
        }
    }

    private static void testStringConstructorWithOffsetExceedingArray() {
        byte[] bytes = {65, 66, 67}; // "ABC"
        int offset = 4; // beyond array length
        int length = 1;
        try {
            new String(bytes, offset, length);
            System.out.println("FAIL: Expected StringIndexOutOfBoundsException for offset exceeding array length");
        } catch (StringIndexOutOfBoundsException e) {
            System.out.println("PASS: Caught expected StringIndexOutOfBoundsException for offset exceeding array length");
        } catch (Throwable t) {
            System.out.println("FAIL: Caught unexpected exception: " + t);
        }
    }

    private static void testStringConstructorWithValidInputs() {
        byte[] bytes = {65, 66, 67, 68}; // "ABCD"
        int offset = 1;
        int length = 2; // should produce "BC"
        try {
            String s = new String(bytes, offset, length);
            if ("BC".equals(s)) {
                System.out.println("PASS: Successfully created string " + s);
            } else {
                System.out.println("FAIL: Unexpected string content: " + s);
            }
        } catch (Throwable t) {
            System.out.println("FAIL: Unexpected exception: " + t);
        }
    }
}
