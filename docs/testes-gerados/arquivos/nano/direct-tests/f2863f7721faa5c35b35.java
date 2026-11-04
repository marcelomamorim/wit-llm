/*
 * @test
 * @summary Test DataInputStream.read(byte[] b) method for correct behavior.
 * @run main DataInputStreamWitupTest
 */

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;

public class DataInputStreamWitupTest {

    public static void main(String[] args) throws IOException {
        testReadWithData();
        testReadWithEmptyArray();
        testReadWithNullArray();
    }

    static void testReadWithData() throws IOException {
        byte[] data = {10, 20, 30, 40, 50};
        ByteArrayInputStream bais = new ByteArrayInputStream(data);
        DataInputStream dis = new DataInputStream(bais);
        byte[] buffer = new byte[5];
        int bytesRead = dis.read(buffer);
        assert bytesRead == 5 : "Expected 5 bytes read";
        for (int i = 0; i < buffer.length; i++) {
            assert buffer[i] == data[i] : "Byte mismatch at index " + i;
        }
    }

    static void testReadWithEmptyArray() throws IOException {
        byte[] data = {1, 2, 3};
        ByteArrayInputStream bais = new ByteArrayInputStream(data);
        DataInputStream dis = new DataInputStream(bais);
        byte[] buffer = new byte[0];
        int bytesRead = dis.read(buffer);
        assert bytesRead == 0 : "Expected 0 bytes read for empty buffer";
    }

    static void testReadWithNullArray() {
        byte[] data = {1, 2, 3};
        ByteArrayInputStream bais = new ByteArrayInputStream(data);
        DataInputStream dis = new DataInputStream(bais);
        try {
            dis.read(null);
            assert false : "Expected NullPointerException";
        } catch (NullPointerException e) {
            // expected
        } catch (IOException e) {
            assert false : "Did not expect IOException";
        }
    }
}
