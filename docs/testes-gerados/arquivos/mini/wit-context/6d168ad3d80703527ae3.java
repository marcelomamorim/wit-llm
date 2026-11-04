/*
 * @test
 * @summary Deterministic tests for sun.net.www.http.PosterOutputStream.write(int)
 * @run main PosterOutputStreamWitupTest
 */

import sun.net.www.http.PosterOutputStream;
import java.io.IOException;

public class PosterOutputStreamWitupTest {
    public static void main(String[] args) throws IOException {
        PosterOutputStream pos = new PosterOutputStream();

        // Test writing a single byte before close
        pos.write(100); // write byte value 100
        if (pos.size() != 1) {
            throw new AssertionError("Expected size 1 after one write, got " + pos.size());
        }

        // Test writing after close has no effect
        pos.close();
        int sizeAfterClose = pos.size();
        pos.write(101); // should have no effect
        if (pos.size() != sizeAfterClose) {
            throw new AssertionError("Write after close should have no effect on size");
        }

        // Test multiple writes before close
        PosterOutputStream pos2 = new PosterOutputStream();
        pos2.write(1);
        pos2.write(2);
        pos2.write(3);
        if (pos2.size() != 3) {
            throw new AssertionError("Expected size 3 after three writes, got " + pos2.size());
        }

        // Test reset before close clears buffer
        pos2.reset();
        if (pos2.size() != 0) {
            throw new AssertionError("Expected size 0 after reset, got " + pos2.size());
        }

        // Test reset after close has no effect
        pos2.close();
        int sizeAfterClose2 = pos2.size();
        pos2.reset();
        if (pos2.size() != sizeAfterClose2) {
            throw new AssertionError("Reset after close should have no effect on size");
        }

        // Test writing after close on new instance
        PosterOutputStream pos3 = new PosterOutputStream();
        pos3.close();
        pos3.write(50); // no effect
        if (pos3.size() != 0) {
            throw new AssertionError("Write after close should not increase size");
        }

        System.out.println("All tests passed.");
    }
}
