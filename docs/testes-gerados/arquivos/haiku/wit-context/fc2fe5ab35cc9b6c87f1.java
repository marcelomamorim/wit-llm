/*
 * @test
 * @summary Checks PushbackReader constructor argument validation and observable pushback behavior
 * @run main PushbackReaderWitupTest
 */

import java.io.IOException;
import java.io.PushbackReader;
import java.io.StringReader;

public class PushbackReaderWitupTest {

    public static void main(String[] args) throws Exception {
        testNonPositiveSizeRejected();
        testPositiveSizeAccepted();
        testDefaultConstructorOneCharBuffer();
        testLifoPushbackWithLargerBuffer();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testNonPositiveSizeRejected() {
        int[] sizes = {0, -1, Integer.MIN_VALUE};
        for (int size : sizes) {
            boolean thrown = false;
            try {
                new PushbackReader(new StringReader("a"), size);
            } catch (IllegalArgumentException e) {
                thrown = true;
            }
            check(thrown, "expected IllegalArgumentException for size " + size);
        }
    }

    static void testPositiveSizeAccepted() throws IOException {
        PushbackReader r = new PushbackReader(new StringReader("xy"), 1);
        check(r.read() == 'x', "first char should be x");
        check(r.read() == 'y', "second char should be y");
        check(r.read() == -1, "end of stream expected");
        r.close();
    }

    static void testDefaultConstructorOneCharBuffer() throws IOException {
        PushbackReader r = new PushbackReader(new StringReader("ab"));
        r.unread('z');
        check(r.read() == 'z', "pushed back char should be read first");
        check(r.read() == 'a', "underlying char a expected");

        PushbackReader r2 = new PushbackReader(new StringReader(""));
        r2.unread('1');
        boolean overflow = false;
        try {
            r2.unread('2');
        } catch (IOException e) {
            overflow = true;
        }
        check(overflow, "second unread on one-char buffer should fail with IOException");
        r2.close();
        r.close();
    }

    static void testLifoPushbackWithLargerBuffer() throws IOException {
        PushbackReader r = new PushbackReader(new StringReader(""), 3);
        r.unread('a');
        r.unread('b');
        r.unread('c');
        check(r.read() == 'c', "last pushed char should be read first");
        check(r.read() == 'b', "middle pushed char should be read second");
        check(r.read() == 'a', "first pushed char should be read last");
        check(r.read() == -1, "end of stream expected after pushback drained");
        r.close();
    }
}
