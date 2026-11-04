/*
 * @test
 * @summary Tests for DataInputStream.read(byte[])
 * @run main DataInputStreamWitupTest
 */

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;

public class DataInputStreamWitupTest {

    public static void main(String[] args) throws IOException {
        testReadWithValidArray();
        testReadWithNullArray();
        testReadWithNegativeOffset();
        testReadWithLargeOffset();
    }

    static void testReadWithValidArray() throws IOException {
        byte[] data = {1, 2, 3, 4, 5};
        ByteArrayInputStream bais = new ByteArrayInputStream(data);
        DataInputStream dis = new DataInputStream(bais);
        byte[] buffer = new byte[3];
        int readBytes = dis.read(buffer);
        if (readBytes != 3) {
            throw new AssertionError("Expected to read 3 bytes, but read " + readBytes);
        }
        for (int i = 0; i < 3; i++) {
            if (buffer[i] != data[i]) {
                throw new AssertionError("Buffer content mismatch at index " + i);
            }
        }
        dis.close();
    }

    static void testReadWithNullArray() {
        ByteArrayInputStream bais = new ByteArrayInputStream(new byte[]{1, 2, 3});
        DataInputStream dis = new DataInputStream(bais);
        try {
            dis.read(null);
            throw new AssertionError("Expected NullPointerException not thrown");
        } catch (NullPointerException expected) {
            // expected
        } catch (IOException e) {
            throw new AssertionError("Unexpected IOException", e);
        } finally {
            try {
                dis.close();
            } catch (IOException e) {
                // ignore
            }
        }
    }

    static void testReadWithNegativeOffset() {
        ByteArrayInputStream bais = new ByteArrayInputStream(new byte[]{1, 2, 3});
        DataInputStream dis = new DataInputStream(bais);
        byte[] buffer = new byte[3];
        try {
            dis.read(buffer, -1, 2);
            throw new AssertionError("Expected IndexOutOfBoundsException not thrown");
        } catch (IndexOutOfBoundsException expected) {
            // expected
        } catch (IOException e) {
            throw new AssertionError("Unexpected IOException", e);
        } finally {
            try {
                dis.close();
            } catch (IOException e) {
                // ignore
            }
        }
    }

    static void testReadWithLargeOffset() {
        ByteArrayInputStream bais = new ByteArrayInputStream(new byte[]{1, 2, 3});
        DataInputStream dis = new DataInputStream(bais);
        byte[] buffer = new byte[3];
        try {
            dis.read(buffer, 4, 1);
            throw new AssertionError("Expected IndexOutOfBoundsException not thrown");
        } catch (IndexOutOfBoundsException expected) {
            // expected
        } catch (IOException e) {
            throw new AssertionError("Unexpected IOException", e);
        } finally {
            try {
                dis.close();
            } catch (IOException e) {
                // ignore
            }
        }
    }
}
