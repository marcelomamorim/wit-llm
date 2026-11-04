/*
 * @test
 * @summary Test Writer.append(char) method behavior including nullWriter
 * @run main WriterWitupTest
 */

import java.io.Writer;
import java.io.IOException;

public class WriterWitupTest {
    public static void main(String[] args) throws Exception {
        testAppendCharOnNullWriter();
    }

    private static void testAppendCharOnNullWriter() throws IOException {
        Writer w = Writer.nullWriter();
        // Append a character should succeed and return the same Writer instance
        Writer result = w.append('a');
        if (result != w) {
            throw new AssertionError("append(char) did not return the same Writer instance");
        }

        // Append multiple characters and flush should not throw
        w.append('b');
        w.flush();

        // Close the writer
        w.close();

        // After close, append(char) should throw IOException
        boolean threw = false;
        try {
            w.append('c');
        } catch (IOException e) {
            threw = true;
        }
        if (!threw) {
            throw new AssertionError("append(char) did not throw IOException after close");
        }
    }
}
