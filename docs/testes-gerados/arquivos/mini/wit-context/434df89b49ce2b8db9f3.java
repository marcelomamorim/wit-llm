/*
 * @test
 * @summary Deterministic tests for java.io.ObjectInputStream.read(byte[], int, int)
 * @run main ObjectInputStreamWitupTest
 */

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

public class ObjectInputStreamWitupTest {
    public static void main(String[] args) throws Exception {
        testReadNullBuffer();
        testReadIndexOutOfBounds();
        testReadValidCall();
    }

    private static void testReadNullBuffer() throws Exception {
        ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(new byte[0]));
        try {
            ois.read(null, 0, 1);
            throw new RuntimeException("Expected NullPointerException not thrown");
        } catch (NullPointerException expected) {
            // Expected exception
        } finally {
            ois.close();
        }
    }

    private static void testReadIndexOutOfBounds() throws Exception {
        ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(new byte[10]));
        byte[] buf = new byte[10];
        try {
            // off < 0
            ois.read(buf, -1, 1);
            throw new RuntimeException("Expected IndexOutOfBoundsException not thrown for off < 0");
        } catch (IndexOutOfBoundsException expected) {
            // Expected
        }
        try {
            // len < 0
            ois.read(buf, 0, -1);
            throw new RuntimeException("Expected IndexOutOfBoundsException not thrown for len < 0");
        } catch (IndexOutOfBoundsException expected) {
            // Expected
        }
        try {
            // off + len > buf.length
            ois.read(buf, 5, 6);
            throw new RuntimeException("Expected IndexOutOfBoundsException not thrown for off+len > buf.length");
        } catch (IndexOutOfBoundsException expected) {
            // Expected
        }
        try {
            // off + len < 0 (integer overflow case)
            ois.read(buf, Integer.MAX_VALUE, Integer.MAX_VALUE);
            throw new RuntimeException("Expected IndexOutOfBoundsException not thrown for off+len overflow");
        } catch (IndexOutOfBoundsException expected) {
            // Expected
        } finally {
            ois.close();
        }
    }

    private static void testReadValidCall() throws Exception {
        byte[] data = {1, 2, 3, 4, 5};
        ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(data));
        byte[] buf = new byte[5];
        int read = ois.read(buf, 0, 5);
        if (read != 5) {
            throw new RuntimeException("Expected to read 5 bytes, but read " + read);
        }
        for (int i = 0; i < 5; i++) {
            if (buf[i] != data[i]) {
                throw new RuntimeException("Data mismatch at index " + i);
            }
        }
        ois.close();
    }
}
