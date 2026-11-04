/*
 * @test
 * @summary Test ObjectInputStream.readFully(byte[], int, int) for correct behavior
 * @run main ObjectInputStreamWitupTest
 */

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

public class ObjectInputStreamWitupTest {

    public static void main(String[] args) throws IOException {
        testReadFullyNormal();
        testReadFullyPartial();
        testReadFullyEmpty();
    }

    static void testReadFullyNormal() throws IOException {
        byte[] data = {10, 20, 30, 40, 50};
        ByteArrayInputStream bais = new ByteArrayInputStream(data);
        ObjectInputStream ois = new ObjectInputStream(bais);
        byte[] buffer = new byte[3];
        ois.readFully(buffer, 0, buffer.length);
        boolean result = (buffer[0] == 10 && buffer[1] == 20 && buffer[2] == 30);
        if (!result) {
            throw new AssertionError("Failed normal readFully test");
        }
        ois.close();
    }

    static void testReadFullyPartial() throws IOException {
        byte[] data = {1, 2, 3, 4, 5};
        ByteArrayInputStream bais = new ByteArrayInputStream(data);
        ObjectInputStream ois = new ObjectInputStream(bais);
        byte[] buffer = new byte[2];
        ois.readFully(buffer, 0, buffer.length);
        boolean result = (buffer[0] == 1 && buffer[1] == 2);
        if (!result) {
            throw new AssertionError("Failed partial readFully test");
        }
        ois.close();
    }

    static void testReadFullyEmpty() throws IOException {
        byte[] data = {};
        ByteArrayInputStream bais = new ByteArrayInputStream(data);
        ObjectInputStream ois = new ObjectInputStream(bais);
        byte[] buffer = new byte[0];
        ois.readFully(buffer, 0, 0);
        // No exception should be thrown
        ois.close();
    }
}
