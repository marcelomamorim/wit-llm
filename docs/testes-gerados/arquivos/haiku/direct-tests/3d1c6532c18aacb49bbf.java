/*
 * @test
 * @summary Checks Writer.write(String,int,int) bounds and closed-state behavior observable through Writer.nullWriter()
 * @run main WriterWitupTest
 */

import java.io.IOException;
import java.io.Writer;

public class WriterWitupTest {

    public static void main(String[] args) throws Exception {
        Writer w = Writer.nullWriter();

        // valid ranges must not throw
        w.write("abc", 0, 3);
        w.write("abc", 1, 2);
        w.write("abc", 3, 0);
        w.write("", 0, 0);

        // invalid ranges must be rejected by Objects.checkFromIndexSize
        expectIndexOutOfBounds(w, "abc", -1, 1);
        expectIndexOutOfBounds(w, "abc", 0, -1);
        expectIndexOutOfBounds(w, "abc", 0, 4);
        expectIndexOutOfBounds(w, "abc", 1, 3);
        expectIndexOutOfBounds(w, "abc", 4, 0);

        // after close, valid calls must report IOException
        w.close();
        expectIOException(w, "abc", 0, 1);
        // closing again has no effect; writes still fail
        w.close();
        expectIOException(w, "abc", 2, 1);
    }

    static void expectIndexOutOfBounds(Writer w, String s, int off, int len) throws IOException {
        try {
            w.write(s, off, len);
        } catch (IndexOutOfBoundsException e) {
            return;
        }
        throw new RuntimeException("Expected IndexOutOfBoundsException for off=" + off + ", len=" + len);
    }

    static void expectIOException(Writer w, String s, int off, int len) {
        try {
            w.write(s, off, len);
        } catch (IOException e) {
            return;
        }
        throw new RuntimeException("Expected IOException for closed writer, off=" + off + ", len=" + len);
    }
}
