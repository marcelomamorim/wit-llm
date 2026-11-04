/*
 * @test
 * @summary Checks public transferTo(Writer) behavior of Reader.nullReader(): returns 0, writes nothing, rejects null writer, and fails after close
 * @run main ReaderWitupTest
 */

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;

public class ReaderWitupTest {

    public static void main(String[] args) throws Exception {
        testReturnsZeroAndWritesNothing();
        testNullWriterThrowsNPE();
        testClosedStreamThrowsIOException();
    }

    static Writer sink(final StringBuilder sb) {
        return new Writer() {
            @Override
            public void write(char[] cbuf, int off, int len) {
                sb.append(cbuf, off, len);
            }

            @Override
            public void flush() {
            }

            @Override
            public void close() {
            }
        };
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testReturnsZeroAndWritesNothing() throws IOException {
        Reader r = Reader.nullReader();
        StringBuilder sb = new StringBuilder();
        long n = r.transferTo(sink(sb));
        check(n == 0L, "expected 0 transferred, got " + n);
        check(sb.length() == 0, "expected no characters written, got " + sb);
        r.close();
    }

    static void testNullWriterThrowsNPE() throws IOException {
        Reader r = Reader.nullReader();
        boolean thrown = false;
        try {
            r.transferTo(null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "expected NullPointerException for null writer");
        r.close();
    }

    static void testClosedStreamThrowsIOException() throws IOException {
        Reader r = Reader.nullReader();
        r.close();
        StringBuilder sb = new StringBuilder();
        boolean thrown = false;
        try {
            r.transferTo(sink(sb));
        } catch (IOException e) {
            thrown = true;
        }
        check(thrown, "expected IOException after close");
        check(sb.length() == 0, "expected no characters written after close");
    }
}
