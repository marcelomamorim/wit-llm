/*
 * @test
 * @summary Test BufferedWriter(Writer out) constructor with default buffer size
 * @run main BufferedWriterWitupTest
 */

import java.io.BufferedWriter;
import java.io.CharArrayWriter;
import java.io.Writer;

public class BufferedWriterWitupTest {
    public static void main(String[] args) throws Exception {
        // Create a CharArrayWriter to wrap
        CharArrayWriter caw = new CharArrayWriter();
        // Create BufferedWriter with default buffer size
        BufferedWriter bw = new BufferedWriter(caw);

        // Write a string and flush
        String testString = "Hello, BufferedWriter!";
        bw.write(testString);
        bw.flush();

        // Verify the content was written correctly
        String result = caw.toString();
        if (!testString.equals(result)) {
            throw new AssertionError("Expected '" + testString + "' but got '" + result + "'");
        }

        // Close the BufferedWriter
        bw.close();

        // After close, writing should throw IOException
        boolean threw = false;
        try {
            bw.write('a');
        } catch (java.io.IOException e) {
            threw = true;
        }
        if (!threw) {
            throw new AssertionError("Expected IOException after close");
        }
    }
}
