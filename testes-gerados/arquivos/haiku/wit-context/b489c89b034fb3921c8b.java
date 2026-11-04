/*
 * @test
 * @summary Verify Writer.append(char) returns the same writer, delegates to write(), and throws IOException on a closed nullWriter
 * @run main WriterWitupTest
 */
import java.io.IOException;
import java.io.Writer;

public class WriterWitupTest {

    static class Recorder extends Writer {
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

    public static void main(String[] args) throws Exception {
        testAppendDelegatesToWrite();
        testAppendReturnsSameWriter();
        testAppendAfterCloseThrows();
    }

    static void testAppendDelegatesToWrite() throws IOException {
        Recorder r = new Recorder();
        Writer ret = r.append('a');
        if (ret != r) {
            throw new RuntimeException("append(char) must return this writer");
        }
        r.append('z');
        if (!"az".equals(r.sb.toString())) {
            throw new RuntimeException("Unexpected content: " + r.sb);
        }
    }

    static void testAppendReturnsSameWriter() throws IOException {
        Writer w = Writer.nullWriter();
        if (w.append('x') != w) {
            throw new RuntimeException("nullWriter append(char) must return this writer");
        }
    }

    static void testAppendAfterCloseThrows() throws IOException {
        Writer w = Writer.nullWriter();
        w.close();
        try {
            w.append('x');
            throw new RuntimeException("Expected IOException after close");
        } catch (IOException expected) {
        }
    }
}
