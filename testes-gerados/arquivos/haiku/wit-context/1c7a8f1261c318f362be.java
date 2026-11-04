/*
 * @test
 * @summary Checks Writer.append(CharSequence, int, int) null substitution, range handling and return value
 * @run main WriterWitupTest
 */

import java.io.IOException;
import java.io.Writer;

public class WriterWitupTest {

    static final class Sink extends Writer {
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

    static void expectIndexException(Sink w, CharSequence csq, int start, int end) {
        try {
            w.append(csq, start, end);
        } catch (IndexOutOfBoundsException e) {
            return;
        } catch (IOException e) {
            throw new RuntimeException("unexpected IOException", e);
        }
        throw new RuntimeException("expected IndexOutOfBoundsException for "
                + start + "," + end);
    }

    public static void main(String[] args) throws Exception {
        Sink w = new Sink();

        Writer r = w.append("abcdef", 1, 4);
        check(r == w, "append must return this");
        check(w.sb.toString().equals("bcd"), "unexpected output: " + w.sb);

        Sink empty = new Sink();
        empty.append("abc", 2, 2);
        check(empty.sb.length() == 0, "empty range must write nothing");

        Sink full = new Sink();
        full.append("xyz", 0, 3);
        check(full.sb.toString().equals("xyz"), "full range mismatch");

        Sink nullSink = new Sink();
        nullSink.append(null, 0, 4);
        check(nullSink.sb.toString().equals("null"), "null csq must be treated as \"null\"");

        Sink nullPart = new Sink();
        nullPart.append(null, 1, 3);
        check(nullPart.sb.toString().equals("ul"), "null csq partial range mismatch");

        expectIndexException(new Sink(), "abc", -1, 2);
        expectIndexException(new Sink(), "abc", 2, 1);
        expectIndexException(new Sink(), "abc", 0, 4);
        expectIndexException(new Sink(), null, 0, 5);

        Sink closedSink = new Sink();
        closedSink.close();
        try {
            closedSink.append("abc", 0, 2);
            throw new RuntimeException("expected IOException from closed sink");
        } catch (IOException expected) {
        }
    }
}
