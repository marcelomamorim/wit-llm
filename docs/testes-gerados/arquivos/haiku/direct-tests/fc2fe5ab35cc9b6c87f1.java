/*
 * @test
 * @summary Verifies PushbackReader constructor size validation and observable pushback behavior
 * @run main PushbackReaderWitupTest
 */
import java.io.IOException;
import java.io.PushbackReader;
import java.io.StringReader;

public class PushbackReaderWitupTest {

    public static void main(String[] args) throws Exception {
        testZeroSizeRejected();
        testNegativeSizeRejected();
        testDefaultConstructorReadsThrough();
        testSingleCharPushback();
        testPushbackOrderWithSize2();
        System.out.println("PASS");
    }

    static void testZeroSizeRejected() {
        try {
            new PushbackReader(new StringReader("a"), 0);
            throw new AssertionError("expected IllegalArgumentException for size 0");
        } catch (IllegalArgumentException expected) {
            // expected
        }
    }

    static void testNegativeSizeRejected() {
        try {
            new PushbackReader(new StringReader("a"), -1);
            throw new AssertionError("expected IllegalArgumentException for size -1");
        } catch (IllegalArgumentException expected) {
            // expected
        }
    }

    static void testDefaultConstructorReadsThrough() throws IOException {
        PushbackReader r = new PushbackReader(new StringReader("xy"));
        check(r.read() == 'x', "first char");
        check(r.read() == 'y', "second char");
        check(r.read() == -1, "end of stream");
    }

    static void testSingleCharPushback() throws IOException {
        PushbackReader r = new PushbackReader(new StringReader("b"));
        r.unread('a');
        check(r.read() == 'a', "pushed back char first");
        check(r.read() == 'b', "underlying char after pushback");
        check(r.read() == -1, "end of stream");
    }

    static void testPushbackOrderWithSize2() throws IOException {
        PushbackReader r = new PushbackReader(new StringReader("ab"), 2);
        r.unread('x');
        r.unread('y');
        check(r.read() == 'y', "last pushed back char first");
        check(r.read() == 'x', "earlier pushed back char second");
        check(r.read() == 'a', "underlying 'a'");
        check(r.read() == 'b', "underlying 'b'");
        check(r.read() == -1, "end of stream");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new AssertionError(message);
        }
    }
}
