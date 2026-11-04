/*
 * @test
 * @summary Test for StringBuffer.append(char[] str, int offset, int len) method, focusing on exception paths.
 * @run main StringBufferWitupTest
 */

import java.lang.StringBuffer;

public class StringBufferWitupTest {

    public static void main(String[] args) {
        testAppendCharArrayWithInvalidRange();
        testAppendCharArrayWithValidRange();
    }

    private static void testAppendCharArrayWithInvalidRange() {
        StringBuffer sb = new StringBuffer();
        char[] data = {'a', 'b', 'c'};
        try {
            sb.append(data, -1, 2); // offset negative
            System.out.println("Failed: Expected IndexOutOfBoundsException for negative offset");
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Passed: Caught expected IndexOutOfBoundsException for negative offset");
        }
        try {
            sb.append(data, 0, 4); // len exceeds array length
            System.out.println("Failed: Expected IndexOutOfBoundsException for len exceeding array length");
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Passed: Caught expected IndexOutOfBoundsException for len exceeding array length");
        }
        try {
            sb.append(data, 2, -1); // negative len
            System.out.println("Failed: Expected IndexOutOfBoundsException for negative len");
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Passed: Caught expected IndexOutOfBoundsException for negative len");
        }
    }

    private static void testAppendCharArrayWithValidRange() {
        StringBuffer sb = new StringBuffer();
        char[] data = {'x', 'y', 'z'};
        try {
            sb.append(data, 1, 2); // valid range
            String result = sb.toString();
            if (result.equals("yz")) {
                System.out.println("Passed: append with valid range produces expected result");
            } else {
                System.out.println("Failed: append with valid range, unexpected result: " + result);
            }
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Failed: Unexpected IndexOutOfBoundsException for valid range");
        }
    }
}
