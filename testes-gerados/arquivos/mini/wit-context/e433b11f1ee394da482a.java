/*
 * @test
 * @summary Test deterministic behavior of DataInputStream.readLong()
 * @run main DataInputStreamWitupTest
 */

import java.io.DataInputStream;
import java.io.ByteArrayInputStream;
import java.io.EOFException;
import java.io.IOException;

public class DataInputStreamWitupTest {
    public static void main(String[] args) throws IOException {
        testReadLongNormal();
        testReadLongEOFException();
    }

    private static void testReadLongNormal() throws IOException {
        // Prepare a byte array with exactly 8 bytes representing a known long value
        byte[] bytes = new byte[] {
            (byte)0x01, (byte)0x23, (byte)0x45, (byte)0x67,
            (byte)0x89, (byte)0xAB, (byte)0xCD, (byte)0xEF
        };
        DataInputStream dis = new DataInputStream(new ByteArrayInputStream(bytes));
        long value = dis.readLong();
        // Expected value is 0x0123456789ABCDEFL
        long expected = 0x0123456789ABCDEFL;
        if (value != expected) {
            throw new AssertionError("readLong() returned " + Long.toHexString(value) +
                                     ", expected " + Long.toHexString(expected));
        }
    }

    private static void testReadLongEOFException() {
        // Provide fewer than 8 bytes to cause EOFException
        byte[] bytes = new byte[] { (byte)0x01, (byte)0x02, (byte)0x03 };
        DataInputStream dis = new DataInputStream(new ByteArrayInputStream(bytes));
        try {
            dis.readLong();
            throw new AssertionError("Expected EOFException not thrown");
        } catch (EOFException e) {
            // Expected exception
        } catch (IOException e) {
            throw new AssertionError("Unexpected IOException thrown: " + e);
        }
    }
}
