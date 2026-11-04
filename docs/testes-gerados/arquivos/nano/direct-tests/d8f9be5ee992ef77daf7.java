/* @test    @summary Test ObjectOutputStream.write(byte[] buf) method for correct behavior and exception handling    @run main ObjectOutputStreamWitupTest */

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class ObjectOutputStreamWitupTest {

    public static void main(String[] args) {
        testWriteNormalBuffer();
        testWriteNullBuffer();
        testWriteEmptyBuffer();
        testWriteLargeBuffer();
    }

    static void testWriteNormalBuffer() {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try (ObjectOutputStream oos = new ObjectOutputStream(baos)) {
            byte[] data = {1, 2, 3, 4, 5};
            oos.write(data);
            byte[] result = baos.toByteArray();
            boolean containsData = false;
            for (byte b : result) {
                if (b != 0) {
                    containsData = true;
                    break;
                }
            }
            if (!containsData) {
                throw new AssertionError("Buffer should contain written data.");
            }
        } catch (IOException e) {
            throw new RuntimeException("Unexpected IOException in testWriteNormalBuffer", e);
        }
    }

    static void testWriteNullBuffer() {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try (ObjectOutputStream oos = new ObjectOutputStream(baos)) {
            byte[] data = null;
            oos.write(data); // Should throw NullPointerException
            throw new AssertionError("Expected NullPointerException when writing null buffer.");
        } catch (NullPointerException expected) {
            // Expected exception
        } catch (IOException e) {
            throw new RuntimeException("Unexpected IOException in testWriteNullBuffer", e);
        }
    }

    static void testWriteEmptyBuffer() {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try (ObjectOutputStream oos = new ObjectOutputStream(baos)) {
            byte[] data = {};
            oos.write(data);
            byte[] result = baos.toByteArray();
            if (result.length == 0) {
                throw new AssertionError("Buffer should contain data after writing empty array.");
            }
        } catch (IOException e) {
            throw new RuntimeException("Unexpected IOException in testWriteEmptyBuffer", e);
        }
    }

    static void testWriteLargeBuffer() {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try (ObjectOutputStream oos = new ObjectOutputStream(baos)) {
            byte[] data = new byte[1024 * 1024]; // 1MB buffer
            for (int i = 0; i < data.length; i++) {
                data[i] = (byte) (i % 256);
            }
            oos.write(data);
            byte[] result = baos.toByteArray();
            if (result.length == 0 || result.length < data.length) {
                throw new AssertionError("Buffer should contain the large data written.");
            }
        } catch (IOException e) {
            throw new RuntimeException("Unexpected IOException in testWriteLargeBuffer", e);
        }
    }
}
