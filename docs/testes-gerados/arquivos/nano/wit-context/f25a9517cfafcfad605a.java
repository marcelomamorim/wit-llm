/*
 * @test
 * @summary Test deserialization behavior of ObjectInputStream.readByte()
 * @run main ObjectInputStreamWitupTest
 */

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

public class ObjectInputStreamWitupTest {

    public static void main(String[] args) throws Exception {
        testReadByteNormal();
        testReadByteEOF();
    }

    static void testReadByteNormal() throws IOException {
        byte[] data = {0, 1, 2, 3};
        ByteArrayInputStream bais = new ByteArrayInputStream(data);
        ObjectInputStream ois = new ObjectInputStream(bais);
        for (int i = 0; i < data.length; i++) {
            byte result = ois.readByte();
            if (result != data[i]) {
                throw new AssertionError("Expected " + data[i] + " but got " + result);
            }
        }
        ois.close();
    }

    static void testReadByteEOF() {
        byte[] emptyData = {};
        ByteArrayInputStream bais = new ByteArrayInputStream(emptyData);
        try {
            ObjectInputStream ois = new ObjectInputStream(bais);
            ois.readByte();
            throw new AssertionError("Expected EOFException was not thrown");
        } catch (IOException e) {
            if (!(e instanceof java.io.EOFException)) {
                throw new AssertionError("Expected EOFException but got: " + e);
            }
        }
    }
}
