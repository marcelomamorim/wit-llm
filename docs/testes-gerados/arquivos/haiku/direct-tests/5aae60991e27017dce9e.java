/*
 * @test
 * @summary Tests observable behavior of StringReader.mark(int)
 * @run main StringReaderWitupTest
 */

import java.io.IOException;
import java.io.StringReader;

public class StringReaderWitupTest {

    public static void main(String[] args) throws Exception {
        testResetReturnsToMarkedPosition();
        testMarkAtStartWithZeroLimit();
        testMarkUpdatesPosition();
        testNegativeLimitThrowsIllegalArgumentException();
        testMarkOnClosedReaderThrowsIOException();
    }

    static void testResetReturnsToMarkedPosition() throws IOException {
        StringReader r = new StringReader("abcdef");
        r.read();
        r.read();
        r.mark(10);
        check(r.read() == 'c', "expected 'c' after mark");
        r.reset();
        check(r.read() == 'c', "reset should return to marked position");
    }

    static void testMarkAtStartWithZeroLimit() throws IOException {
        StringReader r = new StringReader("xy");
        r.mark(0);
        check(r.read() == 'x', "expected 'x' first");
        check(r.read() == 'y', "expected 'y' second");
        r.reset();
        check(r.read() == 'x', "reset should return to start");
    }

    static void testMarkUpdatesPosition() throws IOException {
        StringReader r = new StringReader("abcdef");
        r.read();
        r.mark(5);
        r.read();
        r.read();
        r.mark(5);
        check(r.read() == 'd', "expected 'd' after second mark");
        check(r.read() == 'e', "expected 'e'");
        r.reset();
        check(r.read() == 'd', "reset should use the most recent mark");
    }

    static void testNegativeLimitThrowsIllegalArgumentException() throws IOException {
        StringReader r = new StringReader("abc");
        try {
            r.mark(-1);
            throw new RuntimeException("expected IllegalArgumentException");
        } catch (IllegalArgumentException expected) {
            // expected
        }
    }

    static void testMarkOnClosedReaderThrowsIOException() throws IOException {
        StringReader r = new StringReader("abc");
        r.close();
        try {
            r.mark(1);
            throw new RuntimeException("expected IOException");
        } catch (IOException expected) {
            // expected
        }
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
