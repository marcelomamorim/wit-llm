/*
 * @test
 * @summary Tests Writer.write(int) single-character behavior and the closed-state contract of Writer.nullWriter()
 * @run main WriterWitupTest
 */

import java.io.IOException;
import java.io.Writer;

public class WriterWitupTest {

    static class RecordingWriter extends Writer {
        final StringBuilder sb = new StringBuilder();

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
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testSingleChar() throws IOException {
        RecordingWriter w = new RecordingWriter();
        w.write('A');
        check(w.sb.toString().equals("A"), "expected A but was " + w.sb);
    }

    static void testOnlyLow16BitsWritten() throws IOException {
        RecordingWriter w = new RecordingWriter();
        w.write(0x10041);
        check(w.sb.toString().equals("A"), "expected low 16 bits to be written, was " + w.sb);
    }

    static void testSequentialWritesPreserveOrder() throws IOException {
        RecordingWriter w = new RecordingWriter();
        w.write('a');
        w.write('b');
        w.write('c');
        check(w.sb.toString().equals("abc"), "expected abc but was " + w.sb);
    }

    static void testNullWriterClosedThrows() throws IOException {
        Writer w = Writer.nullWriter();
        w.write('x');
        w.close();
        boolean thrown = false;
        try {
            w.write('y');
        } catch (IOException e) {
            thrown = true;
        }
        check(thrown, "expected IOException after close");
    }

    public static void main(String[] args) throws Exception {
        testSingleChar();
        testOnlyLow16BitsWritten();
        testSequentialWritesPreserveOrder();
        testNullWriterClosedThrows();
    }
}
