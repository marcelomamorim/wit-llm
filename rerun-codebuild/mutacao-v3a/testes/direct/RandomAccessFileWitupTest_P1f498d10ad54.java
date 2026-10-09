/*
 * @test
 * @summary Test readByte method of RandomAccessFile for correct EOF handling and byte reading
 * @run main RandomAccessFileWitupTest_P1f498d10ad54
 */

import java.io.RandomAccessFile;
import java.io.EOFException;
import java.io.IOException;
import java.io.File;
import java.nio.file.Files;

public class RandomAccessFileWitupTest_P1f498d10ad54 {

    public static void main(String[] args) throws IOException {
        testReadByteNormal();
        testReadByteEOF();
    }

    static void testReadByteNormal() throws IOException {
        File tempFile = Files.createTempFile("testfile", ".txt").toFile();
        tempFile.deleteOnExit();
        try (RandomAccessFile raf = new RandomAccessFile(tempFile, "rw")) {
            raf.write(new byte[] {10, 20, 30});
            raf.seek(0);
            byte b1 = raf.readByte();
            byte b2 = raf.readByte();
            byte b3 = raf.readByte();
            assert b1 == 10 : "First byte mismatch";
            assert b2 == 20 : "Second byte mismatch";
            assert b3 == 30 : "Third byte mismatch";
        }
    }

    static void testReadByteEOF() throws IOException {
        File tempFile = Files.createTempFile("testfileEOF", ".txt").toFile();
        tempFile.deleteOnExit();
        try (RandomAccessFile raf = new RandomAccessFile(tempFile, "rw")) {
            raf.write(42);
            raf.seek(1); // move beyond data
            try {
                raf.readByte();
                assert false : "Expected EOFException";
            } catch (EOFException e) {
                // expected
            }
        }
    }
}
