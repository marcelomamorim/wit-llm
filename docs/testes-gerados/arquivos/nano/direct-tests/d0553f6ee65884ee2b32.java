/*
 * @test
 * @summary Test DataInputStream.readFully(byte[] b) for correct reading behavior.
 * @run main DataInputStreamWitupTest
 */

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;

public class DataInputStreamWitupTest {

    public static void main(String[] args) throws IOException {
        testReadFullyExact();
        testReadFullyPartial();
        testReadFullyEmpty();
    }

    static void testReadFullyExact() throws IOException {
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
            throw new AssertionError("readFully did not read data correctly in exact case");
        }
    }

    static void testReadFullyPartial() throws IOException {
        byte[] data = {10, 20, 30, 40, 50};
        ByteArrayInputStream bais = new ByteArrayInputStream(data);
        DataInputStream dis = new DataInputStream(bais);
        byte[] buffer = new byte[3];
        dis.readFully(buffer);
        for (int i = 0; i < buffer.length; i++) {
            if (buffer[i] != data[i]) {
                throw new AssertionError("readFully did not read data correctly in partial case");
            }
        }
    }

    static void testReadFullyEmpty() throws IOException {
        byte[] data = {};
        ByteArrayInputStream bais = new ByteArrayInputStream(data);
        DataInputStream dis = new DataInputStream(bais);
        byte[] buffer = new byte[0];
        dis.readFully(buffer); // should not throw
    }
}
