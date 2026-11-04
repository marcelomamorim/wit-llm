/*
 * @test
 * @summary Tests for java.io.PushbackReader.skip(long n)
 * @run main PushbackReaderWitupTest
 */

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

public class PushbackReaderWitupTest {

    public static void main(String[] args) throws Exception {
        testSkipNegative();
        testSkipZero();
        testSkipWithinBuffer();
        testSkipExceedsBuffer();
    }

    private static void testSkipNegative() {
        Reader reader = new StringReader("abc");
        PushbackReader pbr = new PushbackReader(reader, 2);
        try {
            pbr.skip(-1);
            System.err.println("Failed: Expected IllegalArgumentException for negative skip");
        } catch (IllegalArgumentException e) {
            // Expected exception
        } catch (IOException e) {
            System.err.println("Failed: Unexpected IOException");
        }
    }

    private static void testSkipZero() throws IOException {
        Reader reader = new StringReader("abc");
        PushbackReader pbr = new PushbackReader(reader, 2);
        long skipped = pbr.skip(0);
        if (skipped != 0) {
            System.err.println("Failed: skip(0) should return 0");
        }
    }

    private static void testSkipWithinBuffer() throws IOException {
        String data = "abcdef";
        StringReader sr = new StringReader(data);
        PushbackReader pbr = new PushbackReader(sr, 3);
        pbr.read(); // read 'a'
        pbr.read(); // read 'b'
        long skipped = pbr.skip(1); // skip 'c'
        if (skipped != 1) {
            System.err.println("Failed: skip(1) within buffer");
        }
        int ch = pbr.read();
        if (ch != 'd') {
            System.err.println("Failed: next char should be 'd'");
        }
    }

    private static void testSkipExceedsBuffer() throws IOException {
        String data = "xyz";
        StringReader sr = new StringReader(data);
        PushbackReader pbr = new PushbackReader(sr, 2);
        pbr.read(); // read 'x'
        pbr.read(); // read 'y'
        long skipped = pbr.skip(5); // skip more than buffer size
        if (skipped != 2) {
            System.err.println("Failed: skip exceeds buffer size");
        }
        int ch = pbr.read();
        if (ch != 'z') {
            System.err.println("Failed: next char should be 'z'");
        }
    }
}
