/*
 * @test
 * @summary Test PosterOutputStream.write(int) behavior including closed state
 * @run main PosterOutputStreamWitupTest
 */

import sun.net.www.http.PosterOutputStream;
import java.io.IOException;

public class PosterOutputStreamWitupTest {
    public static void main(String[] args) throws IOException {
        PosterOutputStream pos = new PosterOutputStream();

        // Write a byte and verify count increments
        pos.write(100); // write byte value 100
        if (pos.size() != 1) {
            throw new RuntimeException("Expected size 1 after one write, got " + pos.size());
        }

        // Write another byte
        pos.write(50);
        if (pos.size() != 2) {
            throw new RuntimeException("Expected size 2 after two writes, got " + pos.size());
        }

        // Close the stream
        pos.close();

        // Attempt to write after close - should have no effect
        pos.write(25);
        if (pos.size() != 2) {
            throw new RuntimeException("Write after close should have no effect on size");
        }

        // Verify content matches first two bytes written
        byte[] data = pos.toByteArray();
        if (data.length != 2 || data[0] != (byte)100 || data[1] != (byte)50) {
            throw new RuntimeException("Data content mismatch after writes and close");
        }

        System.out.println("Test passed.");
    }
}
