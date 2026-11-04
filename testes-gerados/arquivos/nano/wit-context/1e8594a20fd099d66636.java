/*
 * @test
 * @summary Test for StringLatin1.charAt(byte[], int) method, focusing on exception paths.
 * @run main StringLatin1WitupTest
 */

import java.lang.StringLatin1;

public class StringLatin1WitupTest {

    public static void main(String[] args) {
        testCharAtWithNegativeIndex();
        testCharAtWithIndexEqualToLength();
        testCharAtWithValidIndex();
    }

    private static void testCharAtWithNegativeIndex() {
        byte[] value = {0x01, 0x02, 0x03};
        int index = -1;
        try {
            StringLatin1.charAt(value, index);
            System.out.println("FAIL: Expected StringIndexOutOfBoundsException for negative index");
        } catch (StringIndexOutOfBoundsException e) {
            System.out.println("PASS: Caught expected exception for negative index");
        }
    }

    private static void testCharAtWithIndexEqualToLength() {
        byte[] value = {0x01, 0x02, 0x03};
        int index = value.length;
        try {
            StringLatin1.charAt(value, index);
            System.out.println("FAIL: Expected StringIndexOutOfBoundsException for index equal to length");
        } catch (StringIndexOutOfBoundsException e) {
            System.out.println("PASS: Caught expected exception for index equal to length");
        }
    }

    private static void testCharAtWithValidIndex() {
        byte[] value = {0x0A, 0x0B, 0x0C};
        int index = 1;
        try {
            char result = StringLatin1.charAt(value, index);
            if (result == (char)(value[index] & 0xff)) {
                System.out.println("PASS: Valid index returns correct character");
            } else {
                System.out.println("FAIL: Valid index returned incorrect character");
            }
        } catch (Exception e) {
            System.out.println("FAIL: Unexpected exception for valid index: " + e);
        }
    }
}
