/*
 * @test
 * @summary WITUP-derived checks for ObjectInputStream.read(byte[], int, int) argument validation
 * @run main ObjectInputStreamWitupTest
 */

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class ObjectInputStreamWitupTest {

    public static void main(String[] args) throws Exception {
        testNullBufferThrowsNPE();
        testNegativeOffsetThrowsIOOBE();
        testNegativeLengthThrowsIOOBE();
        testOffsetPlusLengthExceedsBufferThrowsIOOBE();
        testOverflowingEndOffsetThrowsIOOBE();
        testEndOfStreamReturnsMinusOne();
    }

    private static ObjectInputStream newStream() throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try (ObjectOutputStream oos = new ObjectOutputStream(baos)) {
        }
        return new ObjectInputStream(new ByteArrayInputStream(baos.toByteArray()));
    }

    private static void testNullBufferThrowsNPE() throws IOException {
        ObjectInputStream ois = newStream();
        try {
            ois.read(null, 0, 0);
        } catch (NullPointerException e) {
            return;
        }
        throw new RuntimeException("expected NullPointerException for null buffer");
    }

    private static void testNegativeOffsetThrowsIOOBE() throws IOException {
        ObjectInputStream ois = newStream();
        byte[] buf = new byte[4];
        try {
            ois.read(buf, -1, 1);
        } catch (IndexOutOfBoundsException e) {
            return;
        }
        throw new RuntimeException("expected IndexOutOfBoundsException for negative offset");
    }

    private static void testNegativeLengthThrowsIOOBE() throws IOException {
        ObjectInputStream ois = newStream();
        byte[] buf = new byte[4];
        try {
            ois.read(buf, 0, -1);
        } catch (IndexOutOfBoundsException e) {
            return;
        }
        throw new RuntimeException("expected IndexOutOfBoundsException for negative length");
    }

    private static void testOffsetPlusLengthExceedsBufferThrowsIOOBE() throws IOException {
        ObjectInputStream ois = newStream();
        byte[] buf = new byte[4];
        try {
            ois.read(buf, 2, 3);
        } catch (IndexOutOfBoundsException e) {
            return;
        }
        throw new RuntimeException("expected IndexOutOfBoundsException when off+len exceeds buffer");
    }

    private static void testOverflowingEndOffsetThrowsIOOBE() throws IOException {
        ObjectInputStream ois = newStream();
        byte[] buf = new byte[4];
        try {
            ois.read(buf, Integer.MAX_VALUE, 2);
        } catch (IndexOutOfBoundsException e) {
            return;
        }
        throw new RuntimeException("expected IndexOutOfBoundsException for overflowing end offset");
    }

    private static void testEndOfStreamReturnsMinusOne() throws IOException {
        ObjectInputStream ois = newStream();
        byte[] buf = new byte[4];
        int n = ois.read(buf, 0, 4);
        if (n != -1) {
            throw new RuntimeException("expected -1 at end of stream but got " + n);
        }
    }
}
