/*
 * @test
 * @summary BufferedReader(Reader) must buffer and return characters correctly through its public API
 * @run main BufferedReaderWitupTest
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;

public class BufferedReaderWitupTest {

    public static void main(String[] args) throws IOException {
        testReadLines();
        testLargeInputWithArrayReads();
        testLargeLineWithoutNewline();
        testMarkAndReset();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testReadLines() throws IOException {
        BufferedReader br = new BufferedReader(new StringReader("a\nbb\r\nccc"));
        check("a".equals(br.readLine()), "first line mismatch");
        check("bb".equals(br.readLine()), "second line mismatch");
        check("ccc".equals(br.readLine()), "third line mismatch");
        check(br.readLine() == null, "expected end of stream");
        br.close();
    }

    static void testLargeInputWithArrayReads() throws IOException {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 20000; i++) {
            sb.append((char) ('a' + (i % 26)));
        }
        String expected = sb.toString();
        BufferedReader br = new BufferedReader(new StringReader(expected));
        StringBuilder got = new StringBuilder();
        char[] buf = new char[1000];
        int n;
        while ((n = br.read(buf, 0, buf.length)) != -1) {
            got.append(buf, 0, n);
        }
        check(expected.equals(got.toString()), "large content mismatch");
        br.close();
    }

    static void testLargeLineWithoutNewline() throws IOException {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 20000; i++) {
            sb.append((char) ('0' + (i % 10)));
        }
        String expected = sb.toString();
        BufferedReader br = new BufferedReader(new StringReader(expected));
        check(expected.equals(br.readLine()), "large line mismatch");
        check(br.readLine() == null, "expected end of stream after large line");
        br.close();
    }

    static void testMarkAndReset() throws IOException {
        BufferedReader br = new BufferedReader(new StringReader("hello world"));
        for (int i = 0; i < 5; i++) {
            br.read();
        }
        br.mark(100);
        for (int i = 0; i < 6; i++) {
            br.read();
        }
        br.reset();
        check(" world".equals(br.readLine()), "reset did not restore marked position");
        br.close();
    }
}
