/*
 * @test
 * @summary Checks observable IOException behavior of PipedOutputStream.write(int) for unconnected, connected and reader-closed pipes
 * @run main PipedOutputStreamWitupTest
 */
import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;

public class PipedOutputStreamWitupTest {

    public static void main(String[] args) throws Exception {
        testUnconnectedWriteThrows();
        testConnectedWriteDelivers();
        testWriteAfterReaderClosedThrows();
    }

    static void testUnconnectedWriteThrows() {
        PipedOutputStream out = new PipedOutputStream();
        try {
            out.write(1);
        } catch (IOException e) {
            return;
        }
        throw new RuntimeException("Expected IOException when writing to unconnected PipedOutputStream");
    }

    static void testConnectedWriteDelivers() throws IOException {
        PipedInputStream in = new PipedInputStream();
        PipedOutputStream out = new PipedOutputStream(in);
        out.write(42);
        int read = in.read();
        if (read != 42) {
            throw new RuntimeException("Expected byte 42 but was " + read);
        }
        out.close();
        in.close();
    }

    static void testWriteAfterReaderClosedThrows() throws IOException {
        PipedInputStream in = new PipedInputStream();
        PipedOutputStream out = new PipedOutputStream(in);
        in.close();
        try {
            out.write(7);
        } catch (IOException e) {
            return;
        }
        throw new RuntimeException("Expected IOException when reader side is closed");
    }
}
