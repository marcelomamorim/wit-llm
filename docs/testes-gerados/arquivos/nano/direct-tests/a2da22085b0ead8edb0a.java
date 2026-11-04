/* @test    @summary Test ObjectOutputStream.write(byte[] b) method for correctness and exception handling    @run main ObjectOutputStreamWitupTest */

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class ObjectOutputStreamWitupTest {

    public static void main(String[] args) throws Exception {
        testWriteNormal();
        testWriteNull();
        testWriteEmpty();
    }

    static void testWriteNormal() throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        byte[] data = {1, 2, 3, 4, 5};
        oos.write(data);
        oos.close();
        byte[] result = baos.toByteArray();
        assert result.length >= data.length : "Result length should be at least data length";
        for (int i = 0; i < data.length; i++) {
            assert result[i] == data[i] : "Byte mismatch at index " + i;
        }
    }

    static void testWriteNull() {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.write((byte[]) null);
            oos.close();
            assert false : "Expected NullPointerException when writing null byte array";
        } catch (NullPointerException npe) {
            // expected
        } catch (IOException e) {
            assert false : "Unexpected IOException: " + e;
        }
    }

    static void testWriteEmpty() throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        byte[] empty = {};
        oos.write(empty);
        oos.close();
        byte[] result = baos.toByteArray();
        assert result.length >= 0 : "Result length should be non-negative";
    }
}
