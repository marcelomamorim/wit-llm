/*
 * @test
 * @summary Test PushbackReader.mark(int) throws IOException as expected
 * @run main PushbackReaderWitupTest
 */

import java.io.PushbackReader;
import java.io.StringReader;
import java.io.IOException;

public class PushbackReaderWitupTest {
    public static void main(String[] args) throws Exception {
        testMarkThrowsIOException();
    }

    private static void testMarkThrowsIOException() {
        PushbackReader pr = new PushbackReader(new StringReader("abc"));
        try {
            pr.mark(10);
            throw new RuntimeException("Expected IOException not thrown");
        } catch (IOException e) {
            String msg = e.getMessage();
            if (msg == null || !msg.contains("mark/reset not supported")) {
                throw new RuntimeException("IOException thrown but message unexpected: " + msg);
            }
        }
    }
}
