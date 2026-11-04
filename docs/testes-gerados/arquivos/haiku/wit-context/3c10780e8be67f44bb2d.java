/*
 * @test
 * @summary PushbackReader.reset() must always throw IOException with a mark/reset not supported message
 * @run main PushbackReaderWitupTest
 */

import java.io.IOException;
import java.io.PushbackReader;
import java.io.StringReader;

public class PushbackReaderWitupTest {

    public static void main(String[] args) throws Exception {
        testResetOnFreshReaderThrows();
        testResetAfterUnreadThrows();
        testResetAfterReadThrows();
    }

    private static void testResetOnFreshReaderThrows() throws Exception {
        PushbackReader r = new PushbackReader(new StringReader("abc"));
        expectResetIOException(r);
    }

    private static void testResetAfterUnreadThrows() throws Exception {
        PushbackReader r = new PushbackReader(new StringReader("abc"), 2);
        r.unread('x');
        expectResetIOException(r);
    }

    private static void testResetAfterReadThrows() throws Exception {
        PushbackReader r = new PushbackReader(new StringReader("abc"));
        if (r.read() != 'a') {
            throw new AssertionError("unexpected first character");
        }
        expectResetIOException(r);
    }

    private static void expectResetIOException(PushbackReader r) throws Exception {
        try {
            r.reset();
        } catch (IOException e) {
            String msg = e.getMessage();
            if (msg == null || !msg.contains("mark/reset")) {
                throw new AssertionError("unexpected message: " + msg);
            }
            return;
        }
        throw new AssertionError("reset() did not throw IOException");
    }
}
