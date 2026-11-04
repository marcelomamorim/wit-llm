/*
 * @test
 * @summary PipedReader(int) must reject non-positive pipe sizes and accept positive ones
 * @run main PipedReaderWitupTest
 */
import java.io.PipedReader;

public class PipedReaderWitupTest {
    public static void main(String[] args) throws Exception {
        int[] invalid = {0, -1, Integer.MIN_VALUE};
        for (int size : invalid) {
            boolean thrown = false;
            try {
                new PipedReader(size);
            } catch (IllegalArgumentException e) {
                thrown = true;
            }
            if (!thrown) {
                throw new RuntimeException("Expected IllegalArgumentException for pipeSize " + size);
            }
        }
        int[] valid = {1, 16, 1024};
        for (int size : valid) {
            PipedReader r = new PipedReader(size);
            r.close();
        }
    }
}
