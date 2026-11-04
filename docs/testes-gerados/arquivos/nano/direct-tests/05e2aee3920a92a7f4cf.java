/*
 * @test
 * @summary Test the mark method of PushbackReader which throws IOException as specified.
 * @run main PushbackReaderWitupTest
 */

import java.io.IOException;
import java.io.PushbackReader;
import java.io.StringReader;

public class PushbackReaderWitupTest {

    public static void main(String[] args) {
        testMarkThrowsIOException();
    }

    public static void testMarkThrowsIOException() {
        StringReader sr = new StringReader("test");
        PushbackReader pbr = new PushbackReader(sr);
        try {
            pbr.mark(10);
            // If no exception, test fails
            System.err.println("Test failed: mark() did not throw IOException as expected.");
            System.exit(1);
        } catch (IOException e) {
            if ("mark/reset not supported".equals(e.getMessage())) {
                System.out.println("Test passed: mark() threw expected IOException.");
                System.exit(0);
            } else {
                System.err.println("Test failed: mark() threw unexpected IOException: " + e);
                System.exit(1);
            }
        } catch (Exception e) {
            System.err.println("Test failed: mark() threw unexpected exception: " + e);
            System.exit(1);
        }
    }
}
