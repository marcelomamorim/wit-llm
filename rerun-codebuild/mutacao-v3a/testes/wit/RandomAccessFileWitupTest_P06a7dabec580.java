/* @test    @summary Test readInt method for normal and EOF conditions    @run main RandomAccessFileWitupTest_P06a7dabec580 */

import java.io.RandomAccessFile;
import java.io.IOException;
import java.io.EOFException;

public class RandomAccessFileWitupTest_P06a7dabec580 {

    public static void main(String[] args) {
        testReadIntNormal();
        testReadIntEOF();
    }

    private static void testReadIntNormal() {
        try {
            // Prepare a test file with 4 bytes representing an int (e.g., 0x01020304)
            String filename = "testfile.bin";
            java.io.RandomAccessFile raf = new java.io.RandomAccessFile(filename, "rw");
            raf.writeByte(1);
            raf.writeByte(2);
            raf.writeByte(3);
            raf.writeByte(4);
            raf.seek(0);
            int result = raf.readInt();
            raf.close();
            if (result != (1 << 24) + (2 << 16) + (3 << 8) + 4) {
                throw new AssertionError("Unexpected read value: " + result);
            }
        } catch (IOException e) {
            throw new RuntimeException("IOException during testReadIntNormal", e);
        }
    }

    private static void testReadIntEOF() {
        try {
            String filename = "testfile_eof.bin";
            java.io.RandomAccessFile raf = new java.io.RandomAccessFile(filename, "rw");
            // Write only 2 bytes, less than 4 needed for readInt
            raf.writeByte(0x10);
            raf.writeByte(0x20);
            raf.seek(0);
            // Fill buffer for readInt, which will read beyond EOF
            // No need to write more, just read 2 bytes
            int val = raf.readInt(); // Expect EOFException
            raf.close();
            throw new AssertionError("Expected EOFException but got value: " + val);
        } catch (EOFException e) {
            // Expected exception, test passes
        } catch (IOException e) {
            throw new RuntimeException("Unexpected IOException", e);
        }
    }
}
