/*
 * @test
 * @summary Test java.io.ObjectInputStream.read(byte[], int, int) method
 * @run main ObjectInputStreamWitupTest
 */

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

public class ObjectInputStreamWitupTest {
    public static void main(String[] args) throws Exception {
        testNullBuffer();
        testNegativeOffset();
        testNegativeLength();
        testOffsetLengthOutOfBounds();
        testReadReturnsExpectedBytes();
    }

    private static void testNullBuffer() {
        try {
            ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(new byte[0]));
            ois.read(null, 0, 1);
            throw new RuntimeException("Expected NullPointerException not thrown");
        } catch (NullPointerException e) {
            // expected
        } catch (IOException e) {
            throw new RuntimeException("Unexpected IOException", e);
        }
    }

    private static void testNegativeOffset() {
        try {
            ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(new byte[10]));
            ois.read(new byte[10], -1, 1);
            throw new RuntimeException("Expected IndexOutOfBoundsException not thrown");
        } catch (IndexOutOfBoundsException e) {
            // expected
        } catch (IOException e) {
            throw new RuntimeException("Unexpected IOException", e);
        }
    }

    private static void testNegativeLength() {
        try {
            ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(new byte[10]));
            ois.read(new byte[10], 0, -1);
            throw new RuntimeException("Expected IndexOutOfBoundsException not thrown");
        } catch (IndexOutOfBoundsException e) {
            // expected
        } catch (IOException e) {
            throw new RuntimeException("Unexpected IOException", e);
        }
    }

    private static void testOffsetLengthOutOfBounds() {
        try {
            ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(new byte[10]));
            ois.read(new byte[10], 5, 6); // 5 + 6 = 11 > 10
            throw new RuntimeException("Expected IndexOutOfBoundsException not thrown");
        } catch (IndexOutOfBoundsException e) {
            // expected
        } catch (IOException e) {
            throw new RuntimeException("Unexpected IOException", e);
        }
    }

    private static void testReadReturnsExpectedBytes() throws IOException {
        byte[] data = new byte[] {1, 2, 3, 4, 5};
        ByteArrayInputStream bais = new ByteArrayInputStream(data);
        ObjectInputStream ois = new ObjectInputStream(bais);
        byte[] buf = new byte[5];
        int read = ois.read(buf, 0, 5);
        if (read != 5) {
            throw new RuntimeException("Expected to read 5 bytes but read " + read);
        }
        for (int i = 0; i < 5; i++) {
            if (buf[i] != data[i]) {
                throw new RuntimeException("Byte at index " + i + " differs: expected " + data[i] + " but got " + buf[i]);
            }
        }
    }
}
