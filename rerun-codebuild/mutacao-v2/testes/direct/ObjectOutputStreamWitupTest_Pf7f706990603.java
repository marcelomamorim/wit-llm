/*
 * @test
 * @summary Test java.io.ObjectOutputStream.write(byte[], int, int) method
 * @run main ObjectOutputStreamWitupTest_Pf7f706990603
 */

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class ObjectOutputStreamWitupTest_Pf7f706990603 {
    public static void main(String[] args) throws Exception {
        testWriteValid();
        testWriteNullBuffer();
        testWriteIndexOutOfBounds();
    }

    private static void testWriteValid() throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        byte[] data = new byte[] {1, 2, 3, 4, 5};
        oos.write(data, 1, 3); // should write bytes 2,3,4
        oos.flush();
        byte[] written = baos.toByteArray();
        if (written.length < 3) {
            throw new RuntimeException("Written data length less than expected");
        }
        if (written[0] != 2 || written[1] != 3 || written[2] != 4) {
            throw new RuntimeException("Written data does not match expected slice");
        }
        oos.close();
    }

    private static void testWriteNullBuffer() {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.write(null, 0, 1);
            throw new RuntimeException("Expected NullPointerException not thrown");
        } catch (NullPointerException npe) {
            // expected
        } catch (IOException ioe) {
            throw new RuntimeException("Unexpected IOException", ioe);
        }
    }

    private static void testWriteIndexOutOfBounds() {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            byte[] data = new byte[5];
            // off < 0
            try {
                oos.write(data, -1, 1);
                throw new RuntimeException("Expected IndexOutOfBoundsException for off < 0 not thrown");
            } catch (IndexOutOfBoundsException expected) {}
            // len < 0
            try {
                oos.write(data, 0, -1);
                throw new RuntimeException("Expected IndexOutOfBoundsException for len < 0 not thrown");
            } catch (IndexOutOfBoundsException expected) {}
            // off + len > data.length
            try {
                oos.write(data, 3, 3);
                throw new RuntimeException("Expected IndexOutOfBoundsException for off+len > buf.length not thrown");
            } catch (IndexOutOfBoundsException expected) {}
            // off + len < 0 (overflow)
            try {
                oos.write(data, Integer.MAX_VALUE, 1);
                throw new RuntimeException("Expected IndexOutOfBoundsException for off+len overflow not thrown");
            } catch (IndexOutOfBoundsException expected) {}
            oos.close();
        } catch (IOException ioe) {
            throw new RuntimeException("Unexpected IOException", ioe);
        }
    }
}
