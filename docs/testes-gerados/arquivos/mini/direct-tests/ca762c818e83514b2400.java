/*
 * @test
 * @summary Test PosterOutputStream.write(byte[], int, int) behavior including closed state
 * @run main PosterOutputStreamWitupTest
 */

import sun.net.www.http.PosterOutputStream;
import java.io.IOException;

public class PosterOutputStreamWitupTest {
    public static void main(String[] args) throws IOException {
        testWriteNormal();
        testWriteAfterClose();
        testWritePartialArray();
    }

    private static void testWriteNormal() throws IOException {
        PosterOutputStream pos = new PosterOutputStream();
        byte[] data = {1, 2, 3, 4, 5};
        pos.write(data, 0, data.length);
        pos.close();
        byte[] written = pos.toByteArray();
        if (written.length != data.length) {
            throw new RuntimeException("Length mismatch: expected " + data.length + " but got " + written.length);
        }
        for (int i = 0; i < data.length; i++) {
            if (written[i] != data[i]) {
                throw new RuntimeException("Data mismatch at index " + i + ": expected " + data[i] + " but got " + written[i]);
            }
        }
    }

    private static void testWriteAfterClose() throws IOException {
        PosterOutputStream pos = new PosterOutputStream();
        byte[] data = {10, 20, 30};
        pos.close();
        pos.write(data, 0, data.length); // should have no effect
        byte[] written = pos.toByteArray();
        if (written.length != 0) {
            throw new RuntimeException("Expected no data after close, but found length " + written.length);
        }
    }

    private static void testWritePartialArray() throws IOException {
        PosterOutputStream pos = new PosterOutputStream();
        byte[] data = {100, 101, 102, 103, 104};
        pos.write(data, 1, 3); // write bytes 101, 102, 103
        pos.close();
        byte[] written = pos.toByteArray();
        if (written.length != 3) {
            throw new RuntimeException("Expected length 3 but got " + written.length);
        }
        if (written[0] != 101 || written[1] != 102 || written[2] != 103) {
            throw new RuntimeException("Data mismatch in partial write");
        }
    }
}
