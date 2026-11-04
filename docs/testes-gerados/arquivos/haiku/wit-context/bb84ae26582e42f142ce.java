/*
 * @test
 * @summary Tests bounds and closed-state checks of Writer.nullWriter().write(char[], int, int)
 * @run main WriterWitupTest
 */

import java.io.IOException;
import java.io.Writer;

public class WriterWitupTest {

    public static void main(String[] args) throws Exception {
        char[] buf = new char[8];
        Writer w = Writer.nullWriter();

        w.write(buf, 0, 8);
        w.write(buf, 8, 0);

        expectIndexOutOfBounds(w, buf, -1, 2);
        expectIndexOutOfBounds(w, buf, 0, -1);
        expectIndexOutOfBounds(w, buf, 7, 2);
        expectIndexOutOfBounds(w, buf, 9, 0);

        w.close();
        w.close();

        expectIOException(w, buf, 0, 1);
        expectIndexOutOfBounds(w, buf, 7, 2);
    }

    private static void expectIndexOutOfBounds(Writer w, char[] buf, int off, int len)
            throws IOException {
        try {
            w.write(buf, off, len);
        } catch (IndexOutOfBoundsException e) {
            return;
        }
        throw new AssertionError("expected IndexOutOfBoundsException for off=" + off + ", len=" + len);
    }

    private static void expectIOException(Writer w, char[] buf, int off, int len) {
        try {
            w.write(buf, off, len);
        } catch (IOException e) {
            return;
        }
        throw new AssertionError("expected IOException after close");
    }
}
