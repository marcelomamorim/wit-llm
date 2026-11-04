/*
 * @test
 * @summary PushbackReader.mark must throw IOException with message "mark/reset not supported"
 * @run main PushbackReaderWitupTest
 */
import java.io.IOException;
import java.io.PushbackReader;
import java.io.StringReader;

public class PushbackReaderWitupTest {

    public static void main(String[] args) throws Exception {
        testMarkThrows(1);
        testMarkThrows(0);
        testMarkThrows(-1);
        testMarkThrows(100);
        testMarkOnClosedReaderThrows();
        testReadStillWorksAfterMarkFailure();
    }

    static void testMarkThrows(int limit) throws Exception {
        PushbackReader r = new PushbackReader(new StringReader("abc"));
        try {
            r.mark(limit);
            throw new RuntimeException("expected IOException for mark(" + limit + ")");
        } catch (IOException e) {
            if (!"mark/reset not supported".equals(e.getMessage())) {
                throw new RuntimeException("unexpected message: " + e.getMessage());
            }
        }
    }

    static void testMarkOnClosedReaderThrows() throws Exception {
        PushbackReader r = new PushbackReader(new StringReader("abc"));
        r.close();
        try {
            r.mark(1);
            throw new RuntimeException("expected IOException for mark on closed reader");
        } catch (IOException e) {
            if (!"mark/reset not supported".equals(e.getMessage())) {
                throw new RuntimeException("unexpected message: " + e.getMessage());
            }
        }
    }

    static void testReadStillWorksAfterMarkFailure() throws Exception {
        PushbackReader r = new PushbackReader(new StringReader("abc"));
        try {
            r.mark(1);
        } catch (IOException e) {
            // expected
        }
        int c = r.read();
        if (c != 'a') {
            throw new RuntimeException("expected 'a' but got " + c);
        }
    }
}
