/* @test    @summary Test BufferedReader.skip method for various scenarios    @run main BufferedReaderWitupTest_Pe48407034426 */

import java.io.BufferedReader;
import java.io.StringReader;
import java.io.IOException;

public class BufferedReaderWitupTest_Pe48407034426 {

    public static void main(String[] args) throws IOException {
        testSkipNegative();
        testSkipZero();
        testSkipLessThanLength();
        testSkipMoreThanLength();
        testSkipAtEOF();
    }

    static void testSkipNegative() {
        StringReader sr = new StringReader("abc");
        BufferedReader br = new BufferedReader(sr);
        try {
            br.skip(-1);
            System.err.println("Failed: Expected IllegalArgumentException for negative skip");
        } catch (IllegalArgumentException e) {
            // expected
        } catch (IOException e) {
            System.err.println("Failed: Unexpected IOException for negative skip");
        }
    }

    static void testSkipZero() throws IOException {
        StringReader sr = new StringReader("abc");
        BufferedReader br = new BufferedReader(sr);
        long skipped = br.skip(0);
        if (skipped != 0) {
            System.err.println("Failed: skip(0) should return 0, but got " + skipped);
        }
        int ch = br.read();
        if (ch != 'a') {
            System.err.println("Failed: read after skip(0) should be 'a', but got " + (char)ch);
        }
    }

    static void testSkipLessThanLength() throws IOException {
        StringReader sr = new StringReader("abcdef");
        BufferedReader br = new BufferedReader(sr);
        long skipped = br.skip(3);
        if (skipped != 3) {
            System.err.println("Failed: skip(3) should return 3, but got " + skipped);
        }
        int ch = br.read();
        if (ch != 'd') {
            System.err.println("Failed: next char should be 'd', but got " + (char)ch);
        }
    }

    static void testSkipMoreThanLength() throws IOException {
        StringReader sr = new StringReader("xyz");
        BufferedReader br = new BufferedReader(sr);
        long skipped = br.skip(10);
        if (skipped != 3) {
            System.err.println("Failed: skip(10) should return 3, but got " + skipped);
        }
        int ch = br.read();
        if (ch != -1) {
            System.err.println("Failed: should be EOF after skipping all characters, but got " + (char)ch);
        }
    }

    static void testSkipAtEOF() throws IOException {
        StringReader sr = new StringReader("");
        BufferedReader br = new BufferedReader(sr);
        long skipped = br.skip(5);
        if (skipped != 0) {
            System.err.println("Failed: skip at EOF should return 0, but got " + skipped);
        }
        int ch = br.read();
        if (ch != -1) {
            System.err.println("Failed: read at EOF should be -1, but got " + ch);
        }
    }
}
