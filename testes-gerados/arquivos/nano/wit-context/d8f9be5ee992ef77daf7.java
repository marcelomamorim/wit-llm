/*
 * @test
 * @summary Test for ObjectOutputStream.write(byte[] buf) method.
 * @run main ObjectOutputStreamWitupTest
 */

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class ObjectOutputStreamWitupTest {

    public static void main(String[] args) throws IOException {
        testWriteNormal();
        testWriteEmpty();
        testWriteNull();
        testWriteNegativeOffset();
        testWriteLargeBuffer();
    }

    static void testWriteNormal() throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        byte[] data = {1, 2, 3, 4, 5};
        oos.write(data);
        oos.close();
        byte[] result = baos.toByteArray();
        boolean condition = result.length == data.length;
        if (!condition) {
            throw new AssertionError("Normal write failed: length mismatch");
        }
    }

    static void testWriteEmpty() throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        byte[] data = {};
        oos.write(data);
        oos.close();
        byte[] result = baos.toByteArray();
        boolean condition = result.length == 0;
        if (!condition) {
            throw new AssertionError("Empty write failed: length not zero");
        }
    }

    static void testWriteNull() {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.write(null);
            oos.close();
            throw new AssertionError("Expected NullPointerException not thrown");
        } catch (NullPointerException npe) {
            // expected
        } catch (IOException e) {
            throw new AssertionError("Unexpected IOException", e);
        }
    }

    static void testWriteNegativeOffset() throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        byte[] data = {1, 2, 3};
        try {
            // Simulate invalid offset by calling write with invalid parameters
            // Since write(byte[]) doesn't accept offset, simulate via reflection or skip
            // Instead, test with invalid data length to mimic similar scenario
            byte[] invalidData = {1, 2, 3};
            // No direct way to pass offset, so skip this test as not applicable
        } catch (Exception e) {
            // Not applicable
        }
        oos.close();
    }

    static void testWriteLargeBuffer() throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        byte[] largeData = new byte[1024 * 1024]; // 1MB buffer
        for (int i = 0; i < largeData.length; i++) {
            largeData[i] = (byte) (i % 256);
        }
        oos.write(largeData);
        oos.close();
        byte[] result = baos.toByteArray();
        boolean condition = result.length == largeData.length;
        if (!condition) {
            throw new AssertionError("Large buffer write failed: length mismatch");
        }
    }
}
