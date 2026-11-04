/*
 * @test
 * @summary Test ObjectOutputStream.write(byte[], int, int) for null and bounds checks
 * @run main ObjectOutputStreamWitupTest
 */

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class ObjectOutputStreamWitupTest {
    public static void main(String[] args) throws Exception {
        testWriteNullBuffer();
        testWriteIndexOutOfBounds();
        testWriteValidCall();
    }

    private static void testWriteNullBuffer() throws Exception {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        try {
            oos.write(null, 0, 1);
            throw new RuntimeException("Expected NullPointerException not thrown");
        } catch (NullPointerException expected) {
            // expected
        } finally {
            oos.close();
        }
    }

    private static void testWriteIndexOutOfBounds() throws Exception {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        byte[] buf = new byte[10];
        try {
            oos.write(buf, -1, 5);
            throw new RuntimeException("Expected IndexOutOfBoundsException not thrown for negative offset");
        } catch (IndexOutOfBoundsException expected) {
            // expected
        }
        try {
            oos.write(buf, 0, -1);
            throw new RuntimeException("Expected IndexOutOfBoundsException not thrown for negative length");
        } catch (IndexOutOfBoundsException expected) {
            // expected
        }
        try {
            oos.write(buf, 8, 3); // 8 + 3 = 11 > buf.length
            throw new RuntimeException("Expected IndexOutOfBoundsException not thrown for length+offset > buf.length");
        } catch (IndexOutOfBoundsException expected) {
            // expected
        }
        try {
            oos.write(buf, Integer.MAX_VALUE, 1); // endoff overflow check
            throw new RuntimeException("Expected IndexOutOfBoundsException not thrown for offset overflow");
        } catch (IndexOutOfBoundsException expected) {
            // expected
        } finally {
            oos.close();
        }
    }

    private static void testWriteValidCall() throws Exception {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        byte[] buf = new byte[10];
        // Fill buffer with some data
        for (int i = 0; i < buf.length; i++) {
            buf[i] = (byte) i;
        }
        // Should not throw
        oos.write(buf, 0, buf.length);
        oos.flush();
        oos.close();
        // The underlying ByteArrayOutputStream should have data
        if (baos.size() == 0) {
            throw new RuntimeException("No data written to underlying stream");
        }
    }
}
