/*
 * @test
 * @summary Tests for DataInputStream.readFully(byte[])
 * @run main DataInputStreamWitupTest
 */

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;

public class DataInputStreamWitupTest {

    public static void main(String[] args) throws IOException {
        testReadFullyNormal();
        testReadFullyEOF();
        testReadFullyNegativeArray();
    }

    static void testReadFullyNormal() throws IOException {
        byte[] data = {1, 2, 3, 4, 5};
        ByteArrayInputStream bais = new ByteArrayInputStream(data);
        DataInputStream dis = new DataInputStream(bais);
        byte[] buffer = new byte[5];
        dis.readFully(buffer);
        boolean success = true;
        for (int i = 0; i < buffer.length; i++) {
            if (buffer[i] != data[i]) {
                success = false;
                break;
            }
        }
        if (!success) {
            throw new RuntimeException("Failed normal readFully test");
        }
    }

    static void testReadFullyEOF() {
        byte[] data = {1, 2, 3};
        ByteArrayInputStream bais = new ByteArrayInputStream(data);
        DataInputStream dis = new DataInputStream(bais);
        byte[] buffer = new byte[5];
        try {
            dis.readFully(buffer);
            throw new RuntimeException("Expected EOFException not thrown");
        } catch (IOException e) {
            // Expected exception
            if (!(e instanceof java.io.EOFException)) {
                throw new RuntimeException("Unexpected exception type", e);
            }
        }
    }

    static void testReadFullyNegativeArray() {
        byte[] data = {1, 2, 3};
        ByteArrayInputStream bais = new ByteArrayInputStream(data);
        DataInputStream dis = new DataInputStream(bais);
        byte[] buffer = null;
        try {
            dis.readFully(buffer);
            throw new RuntimeException("Expected NullPointerException not thrown");
        } catch (NullPointerException e) {
            // Expected
        } catch (IOException e) {
            throw new RuntimeException("Unexpected IOException", e);
        }
    }
}
