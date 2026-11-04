/*
 * @test
 * @summary Test Writer.append(CharSequence) behavior including IOException on closed nullWriter
 * @run main WriterWitupTest
 */

import java.io.Writer;
import java.io.IOException;

public class WriterWitupTest {
    public static void main(String[] args) throws Exception {
        testAppendOnOpenNullWriter();
        testAppendOnClosedNullWriterThrowsIOException();
    }

    private static void testAppendOnOpenNullWriter() throws IOException {
        Writer w = Writer.nullWriter();
        // Should return the same Writer instance and not throw
        Writer result = w.append((CharSequence) null);
        if (result != w) {
            throw new AssertionError("append(null) did not return same Writer instance");
        }
        result = w.append("test");
        if (result != w) {
            throw new AssertionError("append(String) did not return same Writer instance");
        }
        result = w.append("test", 0, 2);
        if (result != w) {
            throw new AssertionError("append(CharSequence, int, int) did not return same Writer instance");
        }
    }

    private static void testAppendOnClosedNullWriterThrowsIOException() {
        Writer w = Writer.nullWriter();
        try {
            w.close();
        } catch (IOException e) {
            throw new AssertionError("close() on nullWriter should not throw", e);
        }
        try {
            w.append("fail");
            throw new AssertionError("append() on closed nullWriter did not throw IOException");
        } catch (IOException e) {
            // Expected IOException
        }
        try {
            w.append((CharSequence) null);
            throw new AssertionError("append(null) on closed nullWriter did not throw IOException");
        } catch (IOException e) {
            // Expected IOException
        }
        try {
            w.append("fail", 0, 1);
            throw new AssertionError("append(CharSequence, int, int) on closed nullWriter did not throw IOException");
        } catch (IOException e) {
            // Expected IOException
        }
    }
}
