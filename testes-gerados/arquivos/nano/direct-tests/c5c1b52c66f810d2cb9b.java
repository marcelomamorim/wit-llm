/* @test    @summary Test readUnsignedByte method of ObjectInputStream for normal and EOF conditions    @run main ObjectInputStreamWitupTest */

import java.io.ByteArrayInputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;

public class ObjectInputStreamWitupTest {

    public static void main(String[] args) throws Exception {
        testReadUnsignedByteNormal();
        testReadUnsignedByteEOF();
    }

    static void testReadUnsignedByteNormal() throws IOException {
        byte[] data = {0x00, 0x7F, (byte)0xFF};
        ByteArrayInputStream bais = new ByteArrayInputStream(data);
        ObjectInputStream ois = new ObjectInputStream(bais);
        int first = ois.readUnsignedByte();
        int second = ois.readUnsignedByte();
        int third = ois.readUnsignedByte();
        assert first == 0 : "Expected 0, got " + first;
        assert second == 127 : "Expected 127, got " + second;
        assert third == 255 : "Expected 255, got " + third;
        ois.close();
    }

    static void testReadUnsignedByteEOF() {
        byte[] data = {};
        ByteArrayInputStream bais = new ByteArrayInputStream(data);
        try (ObjectInputStream ois = new ObjectInputStream(bais)) {
            ois.readUnsignedByte();
            assert false : "Expected EOFException";
        } catch (EOFException e) {
            // expected
        } catch (IOException e) {
            assert false : "Unexpected IOException: " + e;
        }
    }
}
