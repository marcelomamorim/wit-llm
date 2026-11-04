/*
 * @test
 * @summary Behavioral checks for the public BufferedReader(Reader in) constructor (default buffer size)
 * @run main BufferedReaderWitupTest
 */

import java.io.BufferedReader;
import java.io.StringReader;

public class BufferedReaderWitupTest {

    public static void main(String[] args) throws Exception {
        testNullReaderRejected();
        testSingleCharReads();
        testEmptyReaderReadLineReturnsNull();
        testReadLinesAcrossDefaultBufferBoundary();
        testCarriageReturnLineTerminators();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new AssertionError(message);
        }
    }

    static void testNullReaderRejected() {
        try {
            new BufferedReader(null);
            throw new AssertionError("expected NullPointerException for null Reader");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    static void testSingleCharReads() throws Exception {
        try (BufferedReader br = new BufferedReader(new StringReader("ab"))) {
            check(br.read() == 'a', "first char should be 'a'");
            check(br.read() == 'b', "second char should be 'b'");
            check(br.read() == -1, "end of stream should return -1");
        }
    }

    static void testEmptyReaderReadLineReturnsNull() throws Exception {
        try (BufferedReader br = new BufferedReader(new StringReader(""))) {
            check(br.readLine() == null, "readLine on empty input should return null");
        }
    }

    static void testReadLinesAcrossDefaultBufferBoundary() throws Exception {
        StringBuilder sb = new StringBuilder();
        int lineCount = 1000;
        for (int i = 0; i < lineCount; i++) {
            sb.append("line-").append(i).append('\n');
        }
        // Input is well over the default 8192-char buffer, forcing refills.
        check(sb.length() > 8192, "test input should exceed default buffer size");
        try (BufferedReader br = new BufferedReader(new StringReader(sb.toString()))) {
            for (int i = 0; i < lineCount; i++) {
                String expected = "line-" + i;
                String actual = br.readLine();
                check(expected.equals(actual),
                      "line " + i + " expected " + expected + " but was " + actual);
            }
            check(br.readLine() == null, "no lines should remain after last line");
        }
    }

    static void testCarriageReturnLineTerminators() throws Exception {
        try (BufferedReader br = new BufferedReader(new StringReader("x\r\ny\rz"))) {
            check("x".equals(br.readLine()), "first line should be x");
            check("y".equals(br.readLine()), "second line should be y");
            check("z".equals(br.readLine()), "third line should be z");
            check(br.readLine() == null, "stream should be exhausted");
        }
    }
}
