/*
 * @test
 * @summary PushbackReader.reset() must throw IOException with message "mark/reset not supported" and must not disturb pushed-back data
 * @run main PushbackReaderWitupTest
 */

import java.io.IOException;
import java.io.PushbackReader;
import java.io.StringReader;

public class PushbackReaderWitupTest {
    public static void main(String[] args) throws Exception {
        PushbackReader r = new PushbackReader(new StringReader("abc"));
        try {
            r.reset();
            throw new RuntimeException("reset() did not throw IOException");
        } catch (IOException e) {
            if (!"mark/reset not supported".equals(e.getMessage())) {
                throw new RuntimeException("unexpected message: " + e.getMessage());
            }
        }

        PushbackReader r2 = new PushbackReader(new StringReader("abc"));
        r2.unread('x');
        try {
            r2.reset();
            throw new RuntimeException("reset() did not throw IOException after unread");
        } catch (IOException e) {
            if (!"mark/reset not supported".equals(e.getMessage())) {
                throw new RuntimeException("unexpected message: " + e.getMessage());
            }
        }
        int c = r2.read();
        if (c != 'x') {
            throw new RuntimeException("pushed-back char changed after reset(): " + c);
        }
    }
}
