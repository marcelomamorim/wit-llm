/*
 * @test
 * @summary Test for StringWriter.write(String, int, int) method, focusing on exception paths.
 * @run main StringWriterWitupTest
 */

import java.io.StringWriter;

public class StringWriterWitupTest {

    public static void main(String[] args) {
        testWriteWithInvalidParameters();
        testWriteWithValidParameters();
    }

    private static void testWriteWithInvalidParameters() {
        StringWriter sw = new StringWriter();
        String validString = "abc";
        try {
            sw.write(validString, -1, 2);
            System.out.println("Failed: Expected IndexOutOfBoundsException for negative off");
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Passed: Caught expected IndexOutOfBoundsException for negative off");
        }
        try {
            sw.write(validString, 0, -1);
            System.out.println("Failed: Expected IndexOutOfBoundsException for negative len");
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Passed: Caught expected IndexOutOfBoundsException for negative len");
        }
        try {
            sw.write(validString, 2, 2); // off + len > str.length()
            System.out.println("Failed: Expected IndexOutOfBoundsException for off + len > str.length()");
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Passed: Caught expected IndexOutOfBoundsException for off + len > str.length()");
        }
        try {
            sw.write(validString, 0, 3); // valid case
            System.out.println("Passed: Valid write with off=0, len=3");
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Failed: Unexpected IndexOutOfBoundsException for valid parameters");
        }
        try {
            sw.write(null, 0, 1);
            System.out.println("Failed: Expected NullPointerException for null string");
        } catch (NullPointerException e) {
            System.out.println("Passed: Caught expected NullPointerException for null string");
        }
    }

    private static void testWriteWithValidParameters() {
        StringWriter sw = new StringWriter();
        String str = "hello";
        sw.write(str, 0, str.length());
        String result = sw.toString();
        if ("hello".equals(result)) {
            System.out.println("Passed: write(String, int, int) correctly appends substring");
        } else {
            System.out.println("Failed: Unexpected content after write: " + result);
        }
    }
}
