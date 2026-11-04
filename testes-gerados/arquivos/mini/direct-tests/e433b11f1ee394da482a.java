/*
 * @test
 * @summary Test DataInputStream.readLong() for correct long value reading
 * @run main DataInputStreamWitupTest
 */

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;

public class DataInputStreamWitupTest {
    public static void main(String[] args) throws IOException {
        testReadLongZero();
        testReadLongMaxValue();
        testReadLongMinValue();
        testReadLongArbitraryValue();
    }

    private static void testReadLongZero() throws IOException {
        byte[] bytes = new byte[8]; // all zero bytes
        DataInputStream dis = new DataInputStream(new ByteArrayInputStream(bytes));
        long value = dis.readLong();
        if (value != 0L) {
            throw new AssertionError("Expected 0L but got " + value);
        }
    }

    private static void testReadLongMaxValue() throws IOException {
        // Long.MAX_VALUE = 0x7FFFFFFFFFFFFFFF
        byte[] bytes = new byte[] {
            0x7F, (byte)0xFF, (byte)0xFF, (byte)0xFF,
            (byte)0xFF, (byte)0xFF, (byte)0xFF, (byte)0xFF
        };
        DataInputStream dis = new DataInputStream(new ByteArrayInputStream(bytes));
        long value = dis.readLong();
        if (value != Long.MAX_VALUE) {
            throw new AssertionError("Expected Long.MAX_VALUE but got " + value);
        }
    }

    private static void testReadLongMinValue() throws IOException {
        // Long.MIN_VALUE = 0x8000000000000000
        byte[] bytes = new byte[] {
            (byte)0x80, 0, 0, 0, 0, 0, 0, 0
        };
        DataInputStream dis = new DataInputStream(new ByteArrayInputStream(bytes));
        long value = dis.readLong();
        if (value != Long.MIN_VALUE) {
            throw new AssertionError("Expected Long.MIN_VALUE but got " + value);
        }
    }

    private static void testReadLongArbitraryValue() throws IOException {
        // Arbitrary value: 0x0123456789ABCDEF
        byte[] bytes = new byte[] {
            0x01, 0x23, 0x45, 0x67,
            (byte)0x89, (byte)0xAB, (byte)0xCD, (byte)0xEF
        };
        DataInputStream dis = new DataInputStream(new ByteArrayInputStream(bytes));
        long value = dis.readLong();
        long expected = 0x0123456789ABCDEFL;
        if (value != expected) {
            throw new AssertionError("Expected 0x0123456789ABCDEF but got " + Long.toHexString(value));
        }
    }
}
