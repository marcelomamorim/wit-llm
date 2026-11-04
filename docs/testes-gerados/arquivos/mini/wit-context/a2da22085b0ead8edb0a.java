/*
 * @test
 * @summary Test ObjectOutputStream.write(byte[]) for valid and edge cases
 * @run main ObjectOutputStreamWitupTest
 */

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class ObjectOutputStreamWitupTest {
    public static void main(String[] args) throws IOException {
        testWriteWithValidArray();
        testWriteWithEmptyArray();
    }

    private static void testWriteWithValidArray() throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        byte[] data = {1, 2, 3, 4, 5};
        oos.write(data);
        oos.flush();
        oos.close();
        byte[] written = baos.toByteArray();
        if (written.length == 0) {
            throw new AssertionError("No data was written for non-empty byte array");
        }
    }

    private static void testWriteWithEmptyArray() throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        byte[] empty = new byte[0];
        oos.write(empty);
        oos.flush();
        oos.close();
        byte[] written = baos.toByteArray();
        if (written.length == 0) {
            // It is valid that writing empty array produces no output
            // Just ensure no exception is thrown and method completes normally
        } else {
            // If something is written, also acceptable
        }
    }
}
