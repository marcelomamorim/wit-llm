/* @test    @summary Test for readInt() method in RandomAccessFile to ensure correct reading of 4 bytes and exception on EOF    @run main RandomAccessFileWitupTest_P06a7dabec580 */

import java.io.*;

public class RandomAccessFileWitupTest_P06a7dabec580 {

    public static void main(String[] args) throws IOException {
        testReadIntNormalCase();
        testReadIntEOF();
    }

    static void testReadIntNormalCase() throws IOException {
        byte[] data = {0x01, 0x02, 0x03, 0x04};
        File tempFile = File.createTempFile("test", null);
        tempFile.deleteOnExit();
        try (RandomAccessFile raf = new RandomAccessFile(tempFile, "rw")) {
            raf.write(data);
            raf.seek(0);
            int result = raf.readInt();
            int expected = (0x01 << 24) + (0x02 << 16) + (0x03 << 8) + 0x04;
            assert result == expected : "Expected " + expected + " but got " + result;
        }
    }

    static void testReadIntEOF() throws IOException {
        byte[] data = {0x0A, 0x0B};
        File tempFile = File.createTempFile("testEOF", null);
        tempFile.deleteOnExit();
        try (RandomAccessFile raf = new RandomAccessFile(tempFile, "rw")) {
            raf.write(data);
            raf.seek(0);
            raf.readInt(); // should throw EOFException
            assert false : "Expected EOFException to be thrown";
        } catch (EOFException e) {
            // expected
        }
    }
}
