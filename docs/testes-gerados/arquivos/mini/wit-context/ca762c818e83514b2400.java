/*
 * @test
 * @summary Test PosterOutputStream.write(byte[], int, int) for bounds and closed state
 * @run main PosterOutputStreamWitupTest
 */

import java.io.IOException;
import sun.net.www.http.PosterOutputStream;

public class PosterOutputStreamWitupTest {
    public static void main(String[] args) throws IOException {
        PosterOutputStream pos = new PosterOutputStream();

        // Test normal write with valid parameters
        byte[] data = new byte[] {1, 2, 3, 4, 5};
        pos.write(data, 0, data.length);

        // Test write after close: should have no effect and not throw
        pos.close();
        pos.write(data, 0, data.length); // no exception, no effect

        // Test write with invalid offset and length parameters when not closed
        PosterOutputStream pos2 = new PosterOutputStream();
        byte[] arr = new byte[5];

        // off < 0
        try {
            pos2.write(arr, -1, 1);
            throw new RuntimeException("Expected IndexOutOfBoundsException for off < 0");
        } catch (IndexOutOfBoundsException expected) {
        }

        // len < 0
        try {
            pos2.write(arr, 0, -1);
            throw new RuntimeException("Expected IndexOutOfBoundsException for len < 0");
        } catch (IndexOutOfBoundsException expected) {
        }

        // off + len > arr.length
        try {
            pos2.write(arr, 3, 3);
            throw new RuntimeException("Expected IndexOutOfBoundsException for off + len > arr.length");
        } catch (IndexOutOfBoundsException expected) {
        }

        // off and len exactly arr.length boundary (should succeed)
        pos2.write(arr, 0, arr.length);
    }
}
