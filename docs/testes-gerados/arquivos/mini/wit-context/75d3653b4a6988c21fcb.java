/*
 * @test
 * @summary Test RandomAccessFile.seek(long) for negative position and normal seek
 * @run main RandomAccessFileWitupTest
 */

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

public class RandomAccessFileWitupTest {
    public static void main(String[] args) throws Exception {
        testSeekNegativePosition();
        testSeekValidPosition();
    }

    private static void testSeekNegativePosition() {
        File tempFile = null;
        RandomAccessFile raf = null;
        try {
            tempFile = File.createTempFile("rafTest", ".tmp");
            raf = new RandomAccessFile(tempFile, "rw");
            try {
                raf.seek(-1L);
                throw new RuntimeException("Expected IOException for negative seek position");
            } catch (IOException e) {
                String msg = e.getMessage();
                if (msg == null || !msg.contains("Negative seek offset")) {
                    throw new RuntimeException("IOException thrown but message unexpected: " + msg);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Unexpected IOException during test setup or execution", e);
        } finally {
            if (raf != null) {
                try {
                    raf.close();
                } catch (IOException ignored) {}
            }
            if (tempFile != null && tempFile.exists()) {
                tempFile.delete();
            }
        }
    }

    private static void testSeekValidPosition() {
        File tempFile = null;
        RandomAccessFile raf = null;
        try {
            tempFile = File.createTempFile("rafTest", ".tmp");
            raf = new RandomAccessFile(tempFile, "rw");
            raf.setLength(10L); // ensure file length
            raf.seek(5L);
            long pos = raf.getFilePointer();
            if (pos != 5L) {
                throw new RuntimeException("File pointer expected to be 5 but was " + pos);
            }
        } catch (IOException e) {
            throw new RuntimeException("Unexpected IOException during valid seek test", e);
        } finally {
            if (raf != null) {
                try {
                    raf.close();
                } catch (IOException ignored) {}
            }
            if (tempFile != null && tempFile.exists()) {
                tempFile.delete();
            }
        }
    }
}
