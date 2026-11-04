/*
 * @test
 * @summary Test ObjectOutputStream.write(byte[]) method with valid and edge cases
 * @run main ObjectOutputStreamWitupTest
 */

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class ObjectOutputStreamWitupTest {
    public static void main(String[] args) throws IOException {
        testWriteWithValidByteArray();
        testWriteWithEmptyByteArray();
    }

    private static void testWriteWithValidByteArray() throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        byte[] data = {1, 2, 3, 4, 5};
        oos.write(data);
        oos.flush();
        byte[] written = baos.toByteArray();
        if (written.length < data.length) {
            throw new AssertionError("Written data length less than input data length");
        }
        // The written data includes serialization stream header, so just check that data bytes appear
        boolean foundSequence = false;
        for (int i = 0; i <= written.length - data.length; i++) {
            boolean match = true;
            for (int j = 0; j < data.length; j++) {
                if (written[i + j] != data[j]) {
                    match = false;
                    break;
                }
            }
            if (match) {
                foundSequence = true;
                break;
            }
        }
        if (!foundSequence) {
            throw new AssertionError("Written byte array not found in output stream");
        }
        oos.close();
    }

    private static void testWriteWithEmptyByteArray() throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        byte[] empty = new byte[0];
        oos.write(empty);
        oos.flush();
        byte[] written = baos.toByteArray();
        // The output should contain only the serialization stream header, no data bytes
        if (written.length == 0) {
            throw new AssertionError("Output stream is empty after writing empty byte array");
        }
        oos.close();
    }
}
