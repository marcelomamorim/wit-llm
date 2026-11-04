/*
 * @test
 * @summary Test deserialization behavior of ObjectInputStream.readLong()
 * @run main ObjectInputStreamWitupTest
 */

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

public class ObjectInputStreamWitupTest {

    public static void main(String[] args) throws IOException {
        testReadLongNormal();
        testReadLongWithInsufficientData();
    }

    static void testReadLongNormal() {
        byte[] data = new byte[] {0, 0, 0, 0, 0, 0, 0, 1};
        ByteArrayInputStream bais = new ByteArrayInputStream(data);
        try (ObjectInputStream ois = new ObjectInputStream(bais)) {
            long value = ois.readLong();
            if (value != 1L) {
                throw new AssertionError("Expected 1L, got " + value);
            }
        } catch (IOException e) {
            throw new AssertionError("Unexpected IOException", e);
        }
    }

    static void testReadLongWithInsufficientData() {
        byte[] data = new byte[] {0, 0, 0}; // less than 8 bytes
        ByteArrayInputStream bais = new ByteArrayInputStream(data);
        try (ObjectInputStream ois = new ObjectInputStream(bais)) {
            ois.readLong();
            throw new AssertionError("Expected IOException due to insufficient data");
        } catch (IOException e) {
            // Expected exception
        }
    }
}
