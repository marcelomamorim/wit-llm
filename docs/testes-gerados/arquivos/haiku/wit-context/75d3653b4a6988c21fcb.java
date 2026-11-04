/*
 * @test
 * @summary Verifies that RandomAccessFile.seek rejects negative offsets with an IOException and leaves the file pointer unchanged, while accepting non-negative offsets.
 * @run main RandomAccessFileWitupTest
 */

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

public class RandomAccessFileWitupTest {

    public static void main(String[] args) throws Exception {
        File f = File.createTempFile("raf-witup", ".tmp");
        try {
            try (RandomAccessFile raf = new RandomAccessFile(f, "rw")) {
                raf.write(new byte[16]);

                raf.seek(0);
                if (raf.getFilePointer() != 0) {
                    throw new RuntimeException("seek(0) did not set pointer to 0");
                }

                raf.seek(5);
                if (raf.getFilePointer() != 5) {
                    throw new RuntimeException("seek(5) did not set pointer to 5");
                }

                long before = raf.getFilePointer();
                boolean thrown = false;
                try {
                    raf.seek(-1);
                } catch (IOException e) {
                    thrown = true;
                    if (!"Negative seek offset".equals(e.getMessage())) {
                        throw new RuntimeException("Unexpected message: " + e.getMessage());
                    }
                }
                if (!thrown) {
                    throw new RuntimeException("seek(-1) did not throw IOException");
                }
                if (raf.getFilePointer() != before) {
                    throw new RuntimeException("File pointer changed after rejected seek");
                }
            }
        } finally {
            f.delete();
        }
    }
}
