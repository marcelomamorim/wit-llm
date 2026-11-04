/* @test    @summary Test readUnsignedByte() method of RandomAccessFile for normal and EOF conditions    @run main RandomAccessFileWitupTest */

import java.io.EOFException;
import java.io.IOException;
import java.io.RandomAccessFile;

public class RandomAccessFileWitupTest {

    public static void main(String[] args) throws IOException {
        testReadUnsignedByteNormal();
        testReadUnsignedByteEOF();
    }

    private static void testReadUnsignedByteNormal() throws IOException {
        byte[] data = {10, 20, 30};
        try (RandomAccessFile raf = new RandomAccessFile("testfile.bin", "rw")) {
            raf.setLength(0);
            raf.write(data);
            raf.seek(0);
            for (int i = 0; i < data.length; i++) {
                int value = raf.readUnsignedByte();
                assert value == (data[i] & 0xFF) : "Expected " + (data[i] & 0xFF) + " but got " + value;
            }
        }
    }

    private static void testReadUnsignedByteEOF() throws IOException {
        try (RandomAccessFile raf = new RandomAccessFile("testfile_eof.bin", "rw")) {
            raf.setLength(0);
            raf.writeByte(100);
            raf.seek(1); // position beyond data
            int exceptionThrown = 0;
            try {
                raf.readUnsignedByte();
            } catch (EOFException e) {
                exceptionThrown = 1;
            }
            assert exceptionThrown == 1 : "EOFException was not thrown as expected";
        }
    }
}
