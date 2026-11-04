/*
 * @test
 * @summary Test deserialization behavior of ObjectInputStream.readUnsignedByte()
 * @run main ObjectInputStreamWitupTest
 */

import java.io.ByteArrayInputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;

public class ObjectInputStreamWitupTest {

    public static void main(String[] args) {
        testReadUnsignedByteNormal();
        testReadUnsignedByteEOF();
    }

    static void testReadUnsignedByteNormal() {
        byte[] data = {0, 1, 127, -1}; // bytes to be read as unsigned
        try (ByteArrayInputStream bais = new ByteArrayInputStream(data);
             ObjectInputStream ois = new ObjectInputStream(bais)) {
            int v1 = ois.readUnsignedByte(); // should be 0
            int v2 = ois.readUnsignedByte(); // should be 1
            int v3 = ois.readUnsignedByte(); // should be 127
            int v4 = ois.readUnsignedByte(); // should be 255 (from -1 byte)
            if (v1 != 0 || v2 != 1 || v3 != 127 || v4 != 255) {
                throw new AssertionError("Unexpected unsigned byte values: " + v1 + "," + v2 + "," + v3 + "," + v4);
            }
        } catch (IOException e) {
            throw new AssertionError("Unexpected IOException in normal read", e);
        }
    }

    static void testReadUnsignedByteEOF() {
        byte[] data = {};
        try (ByteArrayInputStream bais = new ByteArrayInputStream(data);
             ObjectInputStream ois = new ObjectInputStream(bais)) {
            ois.readUnsignedByte();
            throw new AssertionError("Expected EOFException not thrown");
        } catch (EOFException e) {
            // expected
        } catch (IOException e) {
            throw new AssertionError("Unexpected IOException in EOF test", e);
        }
    }
}
