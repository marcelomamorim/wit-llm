/*
 * @test
 * @summary Test PushbackReader.mark(int) throws IOException as expected
 * @run main PushbackReaderWitupTest
 */

import java.io.IOException;
import java.io.PushbackReader;
import java.io.StringReader;

public class PushbackReaderWitupTest {
    public static void main(String[] args) throws Exception {
        PushbackReader pr = new PushbackReader(new StringReader("abc"));
        try {
            pr.mark(10);
            throw new RuntimeException("Expected IOException not thrown");
        } catch (IOException e) {
            String msg = e.getMessage();
            if (msg == null || !msg.equals("mark/reset not supported")) {
                throw new RuntimeException("Unexpected IOException message: " + msg);
            }
        }
    }
}
