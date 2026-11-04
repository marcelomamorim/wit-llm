/*
 * @test
 * @summary Test ObjectInputStream.readFully(byte[], int, int) with various inputs
 * @run main ObjectInputStreamWitupTest
 */

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

public class ObjectInputStreamWitupTest {
    public static void main(String[] args) throws Exception {
        testReadFullyExact();
        testReadFullyPartial();
        testReadFullyEmptyArray();
        testReadFullyOffsetLengthZero();
    }

    private static void testReadFullyExact() throws IOException {
        byte[] data = {1, 2, 3, 4, 5};
        ByteArrayInputStream bais = new ByteArrayInputStream(data);
        ObjectInputStream ois = new ObjectInputStream(bais);
        byte[] buffer = new byte[5];
        ois.readFully(buffer, 0, 5);
        for (int i = 0; i < 5; i++) {
            if (buffer[i] != data[i]) {
                throw new RuntimeException("testReadFullyExact failed: buffer[" + i + "] != data[" + i + "]");
            }
        }
        ois.close();
    }

    private static void testReadFullyPartial() throws IOException {
        byte[] data = {10, 20, 30, 40, 50};
        ByteArrayInputStream bais = new ByteArrayInputStream(data);
        ObjectInputStream ois = new ObjectInputStream(bais);
        byte[] buffer = new byte[5];
        ois.readFully(buffer, 1, 3);
        if (buffer[0] != 0) {
            throw new RuntimeException("testReadFullyPartial failed: buffer[0] should be 0");
        }
        if (buffer[1] != 10 || buffer[2] != 20 || buffer[3] != 30) {
            throw new RuntimeException("testReadFullyPartial failed: buffer[1..3] incorrect");
        }
        if (buffer[4] != 0) {
            throw new RuntimeException("testReadFullyPartial failed: buffer[4] should be 0");
        }
        ois.close();
    }

    private static void testReadFullyEmptyArray() throws IOException {
        byte[] data = {};
        ByteArrayInputStream bais = new ByteArrayInputStream(data);
        ObjectInputStream ois = new ObjectInputStream(bais);
        byte[] buffer = new byte[0];
        ois.readFully(buffer, 0, 0);
        ois.close();
    }

    private static void testReadFullyOffsetLengthZero() throws IOException {
        byte[] data = {1, 2, 3};
        ByteArrayInputStream bais = new ByteArrayInputStream(data);
        ObjectInputStream ois = new ObjectInputStream(bais);
        byte[] buffer = new byte[3];
        ois.readFully(buffer, 1, 0); // should do nothing
        if (buffer[0] != 0 || buffer[1] != 0 || buffer[2] != 0) {
            throw new RuntimeException("testReadFullyOffsetLengthZero failed: buffer should remain zeroed");
        }
        ois.close();
    }
}
