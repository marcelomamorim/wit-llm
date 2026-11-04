/*
 * @test
 * @summary Tests BufferedReader.mark(int) argument validation, reset behavior and closed-stream handling
 * @run main BufferedReaderWitupTest
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;

public class BufferedReaderWitupTest {

    public static void main(String[] args) throws Exception {
        testNegativeLimitThrows();
        testZeroLimitAllowed();
        testMarkAndResetRestoresPosition();
        testMarkOnClosedReaderThrowsIOException();
        testNegativeLimitOnClosedReaderThrowsIllegalArgument();
    }

    static void testNegativeLimitThrows() throws IOException {
        BufferedReader br = new BufferedReader(new StringReader("abc"));
        try {
            br.mark(-1);
            throw new RuntimeException("expected IllegalArgumentException");
        } catch (IllegalArgumentException expected) {
        }
        if (br.read() != 'a') {
            throw new RuntimeException("stream position altered after rejected mark");
        }
    }

    static void testZeroLimitAllowed() throws IOException {
        BufferedReader br = new BufferedReader(new StringReader("xy"));
        br.mark(0);
        if (br.read() != 'x') {
            throw new RuntimeException("unexpected character after mark(0)");
        }
    }

    static void testMarkAndResetRestoresPosition() throws IOException {
        BufferedReader br = new BufferedReader(new StringReader("hello"));
        if (br.read() != 'h') {
            throw new RuntimeException("unexpected first character");
        }
        br.mark(10);
        br.read();
        br.read();
        br.reset();
        if (br.read() != 'e') {
            throw new RuntimeException("reset did not return to marked position");
        }
    }

    static void testMarkOnClosedReaderThrowsIOException() throws IOException {
        BufferedReader br = new BufferedReader(new StringReader("abc"));
        br.close();
        try {
            br.mark(1);
            throw new RuntimeException("expected IOException on closed reader");
        } catch (IOException expected) {
        }
    }

    static void testNegativeLimitOnClosedReaderThrowsIllegalArgument() throws IOException {
        BufferedReader br = new BufferedReader(new StringReader("abc"));
        br.close();
        try {
            br.mark(-1);
            throw new RuntimeException("expected IllegalArgumentException");
        } catch (IllegalArgumentException expected) {
        }
    }
}
