/*
 * @test
 * @summary Verify PipedWriter.write(int) throws IOException when the writer is not connected to a PipedReader
 * @run main PipedWriterWitupTest
 */

import java.io.IOException;
import java.io.PipedWriter;

public class PipedWriterWitupTest {

    public static void main(String[] args) throws Exception {
        testUnconnectedWriterThrowsIOException();
        testUnconnectedWriterThrowsForSeveralChars();
    }

    private static void testUnconnectedWriterThrowsIOException() {
        PipedWriter w = new PipedWriter();
        boolean thrown = false;
        try {
            w.write('a');
        } catch (IOException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("Expected IOException when writing to unconnected PipedWriter");
        }
    }

    private static void testUnconnectedWriterThrowsForSeveralChars() {
        int[] values = {0, 'x', 0xFFFF};
        for (int c : values) {
            PipedWriter w = new PipedWriter();
            boolean thrown = false;
            try {
                w.write(c);
            } catch (IOException e) {
                thrown = true;
            }
            if (!thrown) {
                throw new RuntimeException("Expected IOException for char value " + c);
            }
        }
    }
}
