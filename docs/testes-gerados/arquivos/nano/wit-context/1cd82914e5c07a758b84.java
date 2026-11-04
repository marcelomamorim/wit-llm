/*
 * @test
 * @summary Tests for java.io.ObjectOutputStream.write(byte[], int, int)
 * @run main ObjectOutputStreamWitupTest
 */

import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;
import java.io.IOException;

public class ObjectOutputStreamWitupTest {

    public static void main(String[] args) throws IOException {
        testWriteNullBuffer();
        testWriteNegativeOffset();
        testWriteNegativeLength();
        testWriteOffsetPlusLengthExceedsBuffer();
    }

    public static void testWriteNullBuffer() {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = null;
        try {
            oos = new ObjectOutputStream(baos);
            oos.write(null, 0, 0);
            // Expect NullPointerException
            System.err.println("Failed: Expected NullPointerException not thrown");
        } catch (NullPointerException e) {
            // Expected
        } catch (IOException e) {
            System.err.println("Failed: Unexpected IOException: " + e);
        } finally {
            if (oos != null) {
                try {
                    oos.close();
                } catch (IOException e) {
                }
            }
        }
    }

    public static void testWriteNegativeOffset() {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try (ObjectOutputStream oos = new ObjectOutputStream(baos)) {
            byte[] buf = {1, 2, 3};
            oos.write(buf, -1, 1);
            System.err.println("Failed: Expected IndexOutOfBoundsException not thrown");
        } catch (IndexOutOfBoundsException e) {
            // Expected
        } catch (IOException e) {
            System.err.println("Failed: Unexpected IOException: " + e);
        }
    }

    public static void testWriteNegativeLength() {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try (ObjectOutputStream oos = new ObjectOutputStream(baos)) {
            byte[] buf = {1, 2, 3};
            oos.write(buf, 0, -1);
            System.err.println("Failed: Expected IndexOutOfBoundsException not thrown");
        } catch (IndexOutOfBoundsException e) {
            // Expected
        } catch (IOException e) {
            System.err.println("Failed: Unexpected IOException: " + e);
        }
    }

    public static void testWriteOffsetPlusLengthExceedsBuffer() {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try (ObjectOutputStream oos = new ObjectOutputStream(baos)) {
            byte[] buf = {1, 2, 3};
            oos.write(buf, 1, 3);
            System.err.println("Failed: Expected IndexOutOfBoundsException not thrown");
        } catch (IndexOutOfBoundsException e) {
            // Expected
        } catch (IOException e) {
            System.err.println("Failed: Unexpected IOException: " + e);
        }
    }
}
