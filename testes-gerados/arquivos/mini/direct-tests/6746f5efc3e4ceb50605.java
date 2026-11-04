/*
 * @test
 * @summary Test PipedWriter.write(int) behavior including connection and writing
 * @run main PipedWriterWitupTest
 */

import java.io.IOException;
import java.io.PipedReader;
import java.io.PipedWriter;

public class PipedWriterWitupTest {
    public static void main(String[] args) throws Exception {
        testWriteWithoutConnectionThrows();
        testWriteAfterConnectionSucceeds();
    }

    static void testWriteWithoutConnectionThrows() {
        PipedWriter pw = new PipedWriter();
        try {
            pw.write(42);
            throw new RuntimeException("Expected IOException not thrown");
        } catch (IOException e) {
            if (!"Pipe not connected".equals(e.getMessage())) {
                throw new RuntimeException("Unexpected IOException message: " + e.getMessage());
            }
        }
    }

    static void testWriteAfterConnectionSucceeds() throws IOException {
        PipedReader pr = new PipedReader();
        PipedWriter pw = new PipedWriter(pr);

        // Write a character and verify it is received by PipedReader
        pw.write('A');

        // Read the character from the PipedReader
        int readChar = pr.read();
        if (readChar != 'A') {
            throw new RuntimeException("Expected to read 'A' but got: " + readChar);
        }

        // Write another character
        pw.write('Z');
        readChar = pr.read();
        if (readChar != 'Z') {
            throw new RuntimeException("Expected to read 'Z' but got: " + readChar);
        }

        pw.close();
        pr.close();
    }
}
