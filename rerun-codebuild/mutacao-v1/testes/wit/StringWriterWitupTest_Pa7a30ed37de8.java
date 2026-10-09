/* @test    @summary Test for StringWriter.write(String, int, int) method exception paths    @run main StringWriterWitupTest_Pa7a30ed37de8 */

import java.io.StringWriter;

public class StringWriterWitupTest_Pa7a30ed37de8 {

    public static void main(String[] args) {
        testWriteWithInvalidParameters();
    }

    private static void testWriteWithInvalidParameters() {
        StringWriter sw = new StringWriter();
        String validString = "abc";
        try {
            // Test case: len > -off + str.length() (len > str.length() - off)
            sw.write(validString, 0, validString.length() + 1);
            throw new AssertionError("Expected IndexOutOfBoundsException for len > str.length() - off");
        } catch (IndexOutOfBoundsException e) {
            // Expected exception
        }
        try {
            // Test case: len < 0
            sw.write(validString, 0, -1);
            throw new AssertionError("Expected IndexOutOfBoundsException for negative len");
        } catch (IndexOutOfBoundsException e) {
            // Expected exception
        }
        try {
            // Test case: off < 0
            sw.write(validString, -1, 2);
            throw new AssertionError("Expected IndexOutOfBoundsException for negative off");
        } catch (IndexOutOfBoundsException e) {
            // Expected exception
        }
        try {
            // Test case: off + len > str.length()
            sw.write(validString, 2, 2);
            // Should succeed, as off + len == 4, equal to str.length()
        } catch (IndexOutOfBoundsException e) {
            throw new AssertionError("Unexpected IndexOutOfBoundsException for valid parameters", e);
        }
        try {
            // Test case: null string
            sw.write(null, 0, 1);
            throw new AssertionError("Expected NullPointerException for null string");
        } catch (NullPointerException e) {
            // Expected exception
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception for null string", e);
        }
    }
}
