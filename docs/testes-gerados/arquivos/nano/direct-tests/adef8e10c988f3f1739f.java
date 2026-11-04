/* @test    @summary Test insert method of AbstractStringBuilder with char[] input    @run main AbstractStringBuilderWitupTest */

import java.lang.AbstractStringBuilder;

public class AbstractStringBuilderWitupTest {

    public static void main(String[] args) {
        testInsertWithCharArray();
    }

    public static void testInsertWithCharArray() {
        try {
            // Create an instance of a concrete subclass for testing
            StringBuilder sb = new StringBuilder("Hello World");
            // Prepare char array to insert
            char[] insertChars = {' ', 'J', 'a', 'v', 'a'};
            // Insert at position 5 (after 'Hello')
            sb.insert(5, insertChars);
            // Verify the result
            String result = sb.toString();
            if (!"Hello Java World".equals(result)) {
                throw new AssertionError("Unexpected result: " + result);
            }
        } catch (Exception e) {
            throw new RuntimeException("Test failed with exception", e);
        }
    }
}
