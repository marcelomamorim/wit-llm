/*
 * @test
 * @summary Tests the skip method of java.io.PushbackReader
 * @run main PushbackReaderWitupTest
 */

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

public class PushbackReaderWitupTest {

    public static void main(String[] args) throws Exception {
        testSkipWithAvailableChars();
        testSkipBeyondAvailableChars();
        testSkipWithNegativeValue();
        testSkipWhenStreamClosed();
    }

    static void testSkipWithAvailableChars() throws IOException {
        StringReader sr = new StringReader("abcdef");
        java.io.PushbackReader pbr = new java.io.PushbackReader(sr, 2);
        long skipped = pbr.skip(3);
        assert skipped == 3 : "Expected to skip 3 characters, but skipped " + skipped;
        int ch = pbr.read();
        assert ch == 'd' : "Expected to read 'd', but got " + (char) ch;
    }

    static void testSkipBeyondAvailableChars() throws IOException {
        StringReader sr = new StringReader("xyz");
        java.io.PushbackReader pbr = new java.io.PushbackReader(sr, 1);
        long skipped = pbr.skip(5);
        assert skipped == 3 : "Expected to skip remaining 3 characters, but skipped " + skipped;
        int ch = pbr.read();
        assert ch == -1 : "Expected end of stream (-1), but got " + ch;
    }

    static void testSkipWithNegativeValue() {
        StringReader sr = new StringReader("abc");
        java.io.PushbackReader pbr = new java.io.PushbackReader(sr, 1);
        try {
            pbr.skip(-1);
            assert false : "Expected IllegalArgumentException for negative skip";
        } catch (IllegalArgumentException e) {
            // expected
        } catch (IOException e) {
            assert false : "Did not expect IOException here";
        }
    }

    static void testSkipWhenStreamClosed() throws IOException {
        StringReader sr = new StringReader("abc");
        java.io.PushbackReader pbr = new java.io.PushbackReader(sr, 1);
        pbr.close();
        try {
            pbr.skip(1);
            assert false : "Expected IOException after stream closed";
        } catch (IOException e) {
            // expected
        }
    }
}
