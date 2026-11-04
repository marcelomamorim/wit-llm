/*
 * @test
 * @summary Basic behavior of Writer.append(char) and Writer.nullWriter() append after close
 * @run main WriterWitupTest
 */

import java.io.IOException;
import java.io.Writer;

public class WriterWitupTest {

    static class RecordingWriter extends Writer {
        final StringBuilder sb = new StringBuilder();
        boolean closed;

        @Override
        public void write(char[] cbuf, int off, int len) throws IOException {
            if (closed) {
                throw new IOException("closed");
            }
            sb.append(cbuf, off, len);
        }

        @Override
        public void flush() throws IOException {
        }

        @Override
        public void close() throws IOException {
            closed = true;
        }
    }

    static void check(boolean cond, String msg) {
        if (!cond) {
            throw new RuntimeException(msg);
        }
    }

    static void testAppendReturnsThisAndWrites() throws IOException {
        RecordingWriter w = new RecordingWriter();
        Writer r = w.append('a');
        check(r == w, "append(char) must return this");
        check("a".equals(w.sb.toString()), "append(char) must write the character");
    }

    static void testAppendChaining() throws IOException {
        RecordingWriter w = new RecordingWriter();
        w.append('x').append('y').append('z');
        check("xyz".equals(w.sb.toString()), "chained append(char) must write in order");
    }

    static void testNullWriterAppendOpen() throws IOException {
        Writer nw = Writer.nullWriter();
        Writer r = nw.append('q');
        check(r == nw, "nullWriter append(char) must return this while open");
    }

    static void testNullWriterAppendAfterCloseThrows() throws IOException {
        Writer nw = Writer.nullWriter();
        nw.close();
        nw.close();
        boolean thrown = false;
        try {
            nw.append('q');
        } catch (IOException e) {
            thrown = true;
        }
        check(thrown, "nullWriter append(char) after close must throw IOException");
    }

    public static void main(String[] args) throws Exception {
        testAppendReturnsThisAndWrites();
        testAppendChaining();
        testNullWriterAppendOpen();
        testNullWriterAppendAfterCloseThrows();
    }
}
