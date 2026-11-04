/* @test    @summary Test readByte method of ObjectInputStream for normal and EOF conditions    @run main ObjectInputStreamWitupTest */

import java.io.ByteArrayInputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;

public class ObjectInputStreamWitupTest {

    public static void main(String[] args) throws Exception {
        testReadByteNormal();
        testReadByteEOF();
    }

    static void testReadByteNormal() throws IOException {
        byte[] data = {10, 20, 30};
        ByteArrayInputStream bais = new ByteArrayInputStream(data);
        ObjectInputStream ois = new ObjectInputStream(bais);
        for (byte expected : data) {
            byte result = ois.readByte();
            if (result != expected) {
                throw new AssertionError("Expected: " + expected + ", but got: " + result);
            }
        }
        ois.close();
    }

    static void testReadByteEOF() throws IOException {
        byte[] data = {};
        ByteArrayInputStream bais = new ByteArrayInputStream(data);
        ObjectInputStream ois = new ObjectInputStream(bais);
        try {
            ois.readByte();
            throw new AssertionError("Expected EOFException to be thrown");
        } catch (EOFException e) {
            // Expected exception
        } finally {
            ois.close();
        }
    }
}
