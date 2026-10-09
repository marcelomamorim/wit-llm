/* @test
   @summary Tests the seek method of RandomAccessFile for valid and invalid positions.
   @run main RandomAccessFileWitupTest_Pf11d368f9bc7
 */
import java.io.RandomAccessFile;
import java.io.IOException;

public class RandomAccessFileWitupTest_Pf11d368f9bc7 {
    public static void main(String[] args) throws Exception {
        testSeekValidPosition();
        testSeekNegativePosition();
    }

    private static void testSeekValidPosition() throws IOException {
        RandomAccessFile raf = new RandomAccessFile("testfile.txt", "rw");
        try {
            raf.seek(0L);
            // No exception expected
            raf.seek(10L);
            // No exception expected
        } finally {
            raf.close();
        }
    }

    private static void testSeekNegativePosition() {
        try {
            RandomAccessFile raf = new RandomAccessFile("testfile.txt", "rw");
            try {
                raf.seek(-1L);
                throw new AssertionError("Expected IOException for negative seek");
            } catch (IOException e) {
                // Expected exception
            } finally {
                raf.close();
            }
        } catch (IOException e) {
            // If file cannot be created, skip test
        }
    }
}
