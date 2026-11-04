/*
 * @test
 * @summary Verifies bounds checking and closed-state behavior of the Writer returned by Writer.nullWriter()
 * @run main WriterWitupTest
 */

import java.io.IOException;
import java.io.Writer;

public class WriterWitupTest {
    public static void main(String[] args) throws Exception {
        testValidRangesAreAccepted();
        testOutOfBoundsRejected();
        testNullArrayRejected();
        testWriteAfterCloseRejected();
        testBoundsCheckedBeforeClosedState();
    }

    static void testValidRangesAreAccepted() throws IOException {
        Writer w = Writer.nullWriter();
        char[] buf = {'a', 'b', 'c'};
        w.write(buf, 0, 3);
        w.write(buf, 1, 2);
        w.write(buf, 3, 0);
        w.write(buf, 0, 0);
        w.close();
    }

    static void testOutOfBoundsRejected() throws IOException {
        Writer w = Writer.nullWriter();
        char[] buf = {'a', 'b', 'c'};
        expectIndexOutOfBounds(w, buf, -1, 1);
        expectIndexOutOfBounds(w, buf, 0, -1);
        expectIndexOutOfBounds(w, buf, 2, 2);
        expectIndexOutOfBounds(w, buf, 4, 0);
        w.close();
    }

    static void testNullArrayRejected() throws IOException {
        Writer w = Writer.nullWriter();
        boolean thrown = false;
        try {
            w.write(null, 0, 0);
        } catch (NullPointerException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("expected NullPointerException for null array");
        }
        w.close();
    }

    static void testWriteAfterCloseRejected() throws IOException {
        Writer w = Writer.nullWriter();
        char[] buf = {'a', 'b', 'c'};
        w.close();
        expectIOException(w, buf, 0, 1);
        expectIOException(w, buf, 0, 0);
    }

    static void testBoundsCheckedBeforeClosedState() throws IOException {
        Writer w = Writer.nullWriter();
        char[] buf = {'a', 'b', 'c'};
        w.close();
        expectIndexOutOfBounds(w, buf, -1, 1);
    }

    static void expectIndexOutOfBounds(Writer w, char[] buf, int off, int len) throws IOException {
        try {
            w.write(buf, off, len);
        } catch (IndexOutOfBoundsException e) {
            return;
        }
        throw new RuntimeException("expected IndexOutOfBoundsException for off=" + off + " len=" + len);
    }

    static void expectIOException(Writer w, char[] buf, int off, int len) {
        try {
            w.write(buf, off, len);
        } catch (IOException e) {
            return;
        }
        throw new RuntimeException("expected IOException for off=" + off + " len=" + len);
    }
}
