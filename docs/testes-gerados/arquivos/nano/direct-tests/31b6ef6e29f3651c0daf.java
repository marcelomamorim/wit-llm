/*
 * @test
 * @summary Test deserialization of long values using ObjectInputStream.readLong()
 * @run main ObjectInputStreamWitupTest
 */

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

public class ObjectInputStreamWitupTest {

    public static void main(String[] args) throws IOException {
        testReadLong();
    }

    public static void testReadLong() throws IOException {
        byte[] data = new byte[] {0, 0, 0, 0, 0, 0, 0, 1}; // represents long value 1L in big-endian
        ByteArrayInputStream bais = new ByteArrayInputStream(data);
        ObjectInputStream ois = new ObjectInputStream(bais);
        long value = ois.readLong();
        assert value == 1L : "Expected 1L, but got " + value;
        ois.close();
    }
}
