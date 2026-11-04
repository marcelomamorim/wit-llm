/*
 * @test
 * @summary Tests for DataInputStream.readLong() method.
 * @run main DataInputStreamWitupTest
 */

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;

public class DataInputStreamWitupTest {

    public static void main(String[] args) throws IOException {
        testReadLongNormal();
        testReadLongEOF();
        testReadLongInvalidData();
    }

    static void testReadLongNormal() throws IOException {
        byte[] data = new byte[] {0, 0, 0, 0, 0, 0, 0, 1};
        ByteArrayInputStream bais = new ByteArrayInputStream(data);
        DataInputStream dis = new DataInputStream(bais);
        long result = dis.readLong();
        assert result == 1L : "Expected 1L, got " + result;
    }

    static void testReadLongEOF() {
        byte[] data = new byte[] {0, 0, 0, 0, 0, 0, 0}; // only 7 bytes
        ByteArrayInputStream bais = new ByteArrayInputStream(data);
        DataInputStream dis = new DataInputStream(bais);
        try {
            dis.readLong();
            assert false : "Expected EOFException";
        } catch (IOException e) {
            // Expected exception
            assert e.getClass().getName().equals("java.io.EOFException") : "Expected EOFException, got " + e;
        }
    }

    static void testReadLongInvalidData() {
        byte[] data = new byte[] {(byte)0xFF, (byte)0xFF, (byte)0xFF, (byte)0xFF, (byte)0xFF, (byte)0xFF, (byte)0xFF, (byte)0xFF};
        ByteArrayInputStream bais = new ByteArrayInputStream(data);
        DataInputStream dis = new DataInputStream(bais);
        long result = dis.readLong();
        assert result == -1L : "Expected -1L, got " + result;
    }
}
