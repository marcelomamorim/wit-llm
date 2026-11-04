/*
 * @test
 * @summary Test Writer.write(int) behavior including IOException on closed nullWriter
 * @run main WriterWitupTest
 */

import java.io.IOException;
import java.io.Writer;

public class WriterWitupTest {

    public static void main(String[] args) throws Exception {
        testNullWriterWriteOpen();
        testNullWriterWriteClosedThrowsIOException();
    }

    private static void testNullWriterWriteOpen() throws IOException {
        Writer w = Writer.nullWriter();
        // Should not throw when open
        w.write('a');
        w.write(0);
        w.flush();
        w.append('b');
        w.append("test");
        w.append("test", 0, 2);
        w.close(); // close once
    }

    private static void testNullWriterWriteClosedThrowsIOException() {
        Writer w = Writer.nullWriter();
        try {
            w.close();
            w.write('a');
            throw new AssertionError("Expected IOException after write on closed Writer");
        } catch (IOException e) {
            // Expected IOException due to closed stream
            String msg = e.getMessage();
            if (msg == null || !msg.contains("closed")) {
                throw new AssertionError("IOException message does not indicate closed stream: " + msg);
            }
        }
    }
}
