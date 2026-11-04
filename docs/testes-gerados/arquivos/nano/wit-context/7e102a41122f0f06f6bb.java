/*
 * @test
 * @summary Test deserialization behavior of ObjectInputStream.readBoolean()
 * @run main ObjectInputStreamWitupTest
 */

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

public class ObjectInputStreamWitupTest {

    public static void main(String[] args) throws Exception {
        testReadBooleanNormal();
        testReadBooleanEOF();
    }

    static void testReadBooleanNormal() throws IOException {
        byte[] data = {1}; // represents true
        ByteArrayInputStream bais = new ByteArrayInputStream(data);
        ObjectInputStream ois = new ObjectInputStream(bais);
        boolean result = ois.readBoolean();
        if (result != true) {
            throw new AssertionError("Expected true, got " + result);
        }
        ois.close();
    }

    static void testReadBooleanEOF() {
        byte[] data = {}; // empty stream to trigger EOF
        ByteArrayInputStream bais = new ByteArrayInputStream(data);
        try {
            ObjectInputStream ois = new ObjectInputStream(bais);
            ois.readBoolean();
            ois.close();
            throw new AssertionError("Expected EOFException was not thrown");
        } catch (IOException e) {
            if (!(e instanceof java.io.EOFException)) {
                throw new AssertionError("Expected EOFException, but got: " + e);
            }
            // expected exception
        }
    }
}
