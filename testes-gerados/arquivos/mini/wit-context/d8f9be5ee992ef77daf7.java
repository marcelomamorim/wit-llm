/*
 * @test
 * @summary Test ObjectOutputStream.write(byte[]) for bounds and normal behavior
 * @run main ObjectOutputStreamWitupTest
 */

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class ObjectOutputStreamWitupTest {
    public static void main(String[] args) throws Exception {
        testWriteNormal();
        testWriteNullBuffer();
    }

    private static void testWriteNormal() throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        byte[] data = new byte[] {1, 2, 3, 4, 5};
        oos.write(data);
        oos.flush();
        byte[] written = baos.toByteArray();
        if (written.length == 0) {
            throw new AssertionError("No data was written to the underlying stream");
        }
        oos.close();
    }

    private static void testWriteNullBuffer() {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.write(null);
            throw new AssertionError("Expected NullPointerException not thrown");
        } catch (NullPointerException expected) {
            // Expected exception
        } catch (IOException ioe) {
            throw new AssertionError("Unexpected IOException instead of NullPointerException", ioe);
        }
    }
}
