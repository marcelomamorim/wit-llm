/*
 * @test
 * @summary Test PushbackReader.reset() throws IOException as expected
 * @run main PushbackReaderWitupTest
 */

import java.io.PushbackReader;
import java.io.StringReader;
import java.io.IOException;

public class PushbackReaderWitupTest {
    public static void main(String[] args) throws Exception {
        testResetThrowsIOException();
    }

    private static void testResetThrowsIOException() {
        PushbackReader pr = new PushbackReader(new StringReader("abc"));
        try {
            pr.reset();
            throw new RuntimeException("Expected IOException not thrown by reset()");
        } catch (IOException e) {
            String msg = e.getMessage();
            if (msg == null || !msg.contains("mark/reset not supported")) {
                throw new RuntimeException("IOException message unexpected: " + msg);
            }
        }
    }
}
