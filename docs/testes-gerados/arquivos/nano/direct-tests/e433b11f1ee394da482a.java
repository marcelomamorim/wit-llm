/* @test    @summary Test readLong method of DataInputStream for correct long value reading.    @run main DataInputStreamWitupTest */

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;

public class DataInputStreamWitupTest {

    public static void main(String[] args) throws IOException {
        testReadLongPositive();
        testReadLongNegative();
        testReadLongZero();
    }

    private static void testReadLongPositive() throws IOException {
        byte[] data = new byte[] {0, 0, 0, 0, 0, 0, 0, 1};
        ByteArrayInputStream bais = new ByteArrayInputStream(data);
        DataInputStream dis = new DataInputStream(bais);
        long result = dis.readLong();
        long expected = 1L;
        if (result != expected) {
            throw new AssertionError("Expected " + expected + " but got " + result);
        }
    }

    private static void testReadLongNegative() throws IOException {
        byte[] data = new byte[] {(byte)0xFF, (byte)0xFF, (byte)0xFF, (byte)0xFF, (byte)0xFF, (byte)0xFF, (byte)0xFF, (byte)0xFF};
        ByteArrayInputStream bais = new ByteArrayInputStream(data);
        DataInputStream dis = new DataInputStream(bais);
        long result = dis.readLong();
        long expected = -1L;
        if (result != expected) {
            throw new AssertionError("Expected " + expected + " but got " + result);
        }
    }

    private static void testReadLongZero() throws IOException {
        byte[] data = new byte[] {0, 0, 0, 0, 0, 0, 0, 0};
        ByteArrayInputStream bais = new ByteArrayInputStream(data);
        DataInputStream dis = new DataInputStream(bais);
        long result = dis.readLong();
        long expected = 0L;
        if (result != expected) {
            throw new AssertionError("Expected " + expected + " but got " + result);
        }
    }
}
