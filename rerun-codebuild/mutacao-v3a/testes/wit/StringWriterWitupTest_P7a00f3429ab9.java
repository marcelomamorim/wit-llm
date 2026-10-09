/* @test    @summary Test for StringWriter.write(String, int, int) exception handling    @run main StringWriterWitupTest_P7a00f3429ab9 */

import java.io.StringWriter;

public class StringWriterWitupTest_P7a00f3429ab9 {

    public static void main(String[] args) {
        testWriteWithInvalidArguments();
    }

    public static void testWriteWithInvalidArguments() {
        StringWriter writer = new StringWriter();
        String testStr = "abc";
        // Test case: negative length
        try {
            writer.write(testStr, 0, -1);
            System.err.println("Failed: Expected IndexOutOfBoundsException for negative length");
        } catch (IndexOutOfBoundsException e) {
            // Expected exception
        }
        // Test case: offset greater than string length
        try {
            writer.write(testStr, 4, 1);
            System.err.println("Failed: Expected IndexOutOfBoundsException for offset > string length");
        } catch (IndexOutOfBoundsException e) {
            // Expected exception
        }
        // Test case: offset negative
        try {
            writer.write(testStr, -1, 2);
            System.err.println("Failed: Expected IndexOutOfBoundsException for negative offset");
        } catch (IndexOutOfBoundsException e) {
            // Expected exception
        }
        // Test case: offset + len exceeds string length
        try {
            writer.write(testStr, 1, 3);
            // Valid, should not throw
        } catch (IndexOutOfBoundsException e) {
            System.err.println("Failed: Unexpected IndexOutOfBoundsException");
        }
        // Test case: null string
        try {
            writer.write(null, 0, 1);
            System.err.println("Failed: Expected NullPointerException for null string");
        } catch (NullPointerException e) {
            // Expected exception
        }
    }
}
