/*
 * @test
 * @summary Test java.io.Writer.write(String, int, int) for bounds and closed stream exceptions
 * @run main WriterWitupTest
 */

import java.io.IOException;
import java.io.Writer;
import java.util.Objects;

public class WriterWitupTest {

    public static void main(String[] args) throws Exception {
        testWriteThrowsIndexOutOfBoundsException();
        testWriteThrowsIOExceptionWhenClosed();
        testWriteAcceptsValidInput();
    }

    static void testWriteThrowsIndexOutOfBoundsException() {
        Writer w = Writer.nullWriter();
        String s = "abc";
        // off < 0
        try {
            w.write(s, -1, 1);
            throw new RuntimeException("Expected IndexOutOfBoundsException for off < 0");
        } catch (IndexOutOfBoundsException expected) {
        }
        // len < 0
        try {
            w.write(s, 0, -1);
            throw new RuntimeException("Expected IndexOutOfBoundsException for len < 0");
        } catch (IndexOutOfBoundsException expected) {
        }
        // off + len > s.length()
        try {
            w.write(s, 2, 2);
            throw new RuntimeException("Expected IndexOutOfBoundsException for off + len > s.length()");
        } catch (IndexOutOfBoundsException expected) {
        }
    }

    static void testWriteThrowsIOExceptionWhenClosed() throws IOException {
        Writer w = Writer.nullWriter();
        w.close();
        String s = "abc";
        try {
            w.write(s, 0, 1);
            throw new RuntimeException("Expected IOException when writing to closed Writer");
        } catch (IOException expected) {
        }
    }

    static void testWriteAcceptsValidInput() throws IOException {
        Writer w = Writer.nullWriter();
        String s = "abc";
        // Valid call should not throw
        w.write(s, 0, 3);
        w.write(s, 1, 2);
        w.write(s, 0, 0);
    }
}
