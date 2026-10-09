/* @test    @summary Test for ObjectOutputStream.write(byte[], int, int) exception paths    @run main ObjectOutputStreamWitupTest_P2e0011781a92 */

import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;
import java.io.IOException;

public class ObjectOutputStreamWitupTest_P2e0011781a92 {

    public static void main(String[] args) throws Exception {
        testWriteNullBuffer();
        testWriteInvalidBounds();
    }

    private static void testWriteNullBuffer() {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = null;
        try {
            oos = new ObjectOutputStream(baos);
            oos.write(null, 0, 0);
            throw new AssertionError("Expected NullPointerException not thrown");
        } catch (NullPointerException e) {
            // Expected exception
        } catch (IOException e) {
            throw new RuntimeException("Unexpected IOException", e);
        } finally {
            if (oos != null) {
                try {
                    oos.close();
                } catch (IOException e) {
                    // ignore
                }
            }
        }
    }

    private static void testWriteInvalidBounds() {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = null;
        byte[] buffer = {1, 2, 3, 4, 5};
        try {
            oos = new ObjectOutputStream(baos);
            // off + len > buffer.length
            oos.write(buffer, 3, 3);
            throw new AssertionError("Expected IndexOutOfBoundsException not thrown for end index > length");
        } catch (IndexOutOfBoundsException e) {
            // Expected exception
        } catch (IOException e) {
            throw new RuntimeException("Unexpected IOException", e);
        }
        try {
            // off < 0
            oos = new ObjectOutputStream(baos);
            oos.write(buffer, -1, 2);
            throw new AssertionError("Expected IndexOutOfBoundsException not thrown for negative offset");
        } catch (IndexOutOfBoundsException e) {
            // Expected exception
        } catch (IOException e) {
            throw new RuntimeException("Unexpected IOException", e);
        }
        try {
            // len < 0
            oos = new ObjectOutputStream(baos);
            oos.write(buffer, 0, -1);
            throw new AssertionError("Expected IndexOutOfBoundsException not thrown for negative length");
        } catch (IndexOutOfBoundsException e) {
            // Expected exception
        } catch (IOException e) {
            throw new RuntimeException("Unexpected IOException", e);
        }
        try {
            // endoff < 0 (simulate by passing large off and small len)
            oos = new ObjectOutputStream(baos);
            oos.write(buffer, Integer.MAX_VALUE, 1);
            throw new AssertionError("Expected IndexOutOfBoundsException not thrown for large offset");
        } catch (IndexOutOfBoundsException e) {
            // Expected exception
        } catch (IOException e) {
            throw new RuntimeException("Unexpected IOException", e);
        } finally {
            if (oos != null) {
                try {
                    oos.close();
                } catch (IOException e) {
                    // ignore
                }
            }
        }
    }
}
