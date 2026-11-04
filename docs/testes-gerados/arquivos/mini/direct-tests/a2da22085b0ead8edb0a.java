/*
 * @test
 * @summary Test ObjectOutputStream.write(byte[]) method behavior
 * @run main ObjectOutputStreamWitupTest
 */

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class ObjectOutputStreamWitupTest {
    public static void main(String[] args) throws IOException {
        testWriteWithNonEmptyArray();
        testWriteWithEmptyArray();
        testWriteWithNullArray();
    }

    private static void testWriteWithNonEmptyArray() throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        byte[] data = {1, 2, 3, 4, 5};
        oos.write(data);
        oos.flush();
        byte[] written = baos.toByteArray();
        if (written.length == 0) {
            throw new AssertionError("No data was written for non-empty byte array");
        }
        oos.close();
    }

    private static void testWriteWithEmptyArray() throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        byte[] empty = new byte[0];
        oos.write(empty);
        oos.flush();
        byte[] written = baos.toByteArray();
        if (written.length == 0) {
            throw new AssertionError("No data was written for empty byte array");
        }
        oos.close();
    }

    private static void testWriteWithNullArray() {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.write(null);
            throw new AssertionError("Expected NullPointerException not thrown");
        } catch (NullPointerException e) {
            // Expected exception
        } catch (IOException e) {
            throw new AssertionError("Unexpected IOException thrown instead of NullPointerException");
        }
    }
}
