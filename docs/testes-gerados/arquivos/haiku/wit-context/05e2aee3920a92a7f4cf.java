/*
 * @test
 * @summary Verifies that PushbackReader.mark always throws IOException and leaves the reader usable
 * @run main PushbackReaderWitupTest
 */
import java.io.IOException;
import java.io.PushbackReader;
import java.io.StringReader;

public class PushbackReaderWitupTest {

    public static void main(String[] args) throws Exception {
        testMarkThrowsForPositiveLimit();
        testMarkThrowsForZeroLimit();
        testMarkThrowsForNegativeLimit();
        testMarkThrowsWithLargerPushbackBuffer();
        testReaderRemainsUsableAfterFailedMark();
    }

    static void testMarkThrowsForPositiveLimit() throws Exception {
        PushbackReader r = new PushbackReader(new StringReader("abc"));
        expectMarkFailure(r, 1);
    }

    static void testMarkThrowsForZeroLimit() throws Exception {
        PushbackReader r = new PushbackReader(new StringReader("abc"));
        expectMarkFailure(r, 0);
    }

    static void testMarkThrowsForNegativeLimit() throws Exception {
        PushbackReader r = new PushbackReader(new StringReader("abc"));
        expectMarkFailure(r, -1);
    }

    static void testMarkThrowsWithLargerPushbackBuffer() throws Exception {
        PushbackReader r = new PushbackReader(new StringReader("abc"), 4);
        expectMarkFailure(r, 10);
    }

    static void testReaderRemainsUsableAfterFailedMark() throws Exception {
        PushbackReader r = new PushbackReader(new StringReader("bc"), 2);
        expectMarkFailure(r, 5);
        r.unread('a');
        check(r.read() == 'a', "pushed-back char should be read after failed mark");
        check(r.read() == 'b', "stream should continue after failed mark");
    }

    static void expectMarkFailure(PushbackReader r, int limit) {
        try {
            r.mark(limit);
            throw new AssertionError("expected IOException for readAheadLimit " + limit);
        } catch (IOException e) {
            if (!"mark/reset not supported".equals(e.getMessage())) {
                throw new AssertionError("unexpected message: " + e.getMessage());
            }
        }
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new AssertionError(message);
        }
    }
}
