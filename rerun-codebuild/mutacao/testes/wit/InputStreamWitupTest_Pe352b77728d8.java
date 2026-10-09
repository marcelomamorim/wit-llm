/*
 * @test
 * @summary Tests for InputStream.readNBytes(int)
 * @run main InputStreamWitupTest_Pe352b77728d8
 */

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

public class InputStreamWitupTest_Pe352b77728d8 {

    public static void main(String[] args) throws Exception {
        testReadNBytesNormal();
        testReadNBytesZero();
        testReadNBytesNegative();
        testReadNBytesLarge();
    }

    static void testReadNBytesNormal() throws IOException {
        byte[] data = {1, 2, 3, 4, 5};
        InputStream is = new ByteArrayInputStream(data);
        byte[] result = is.readNBytes(3);
        assert result.length == 3 : "Expected length 3";
        for (int i = 0; i < 3; i++) {
            assert result[i] == data[i] : "Mismatch at index " + i;
        }
    }

    static void testReadNBytesZero() throws IOException {
        byte[] data = {1, 2, 3};
        InputStream is = new ByteArrayInputStream(data);
        byte[] result = is.readNBytes(0);
        assert result.length == 0 : "Expected empty array";
    }

    static void testReadNBytesNegative() {
        byte[] data = {1, 2, 3};
        InputStream is = new ByteArrayInputStream(data);
        try {
            is.readNBytes(-1);
            assert false : "Expected IllegalArgumentException";
        } catch (IllegalArgumentException e) {
            assert e.getMessage().contains("len < 0") : "Unexpected message: " + e.getMessage();
        } catch (Exception e) {
            assert false : "Unexpected exception: " + e;
        }
    }

    static void testReadNBytesLarge() throws IOException {
        byte[] data = {10, 20, 30, 40, 50};
        InputStream is = new ByteArrayInputStream(data);
        byte[] result = is.readNBytes(10);
        assert result.length == data.length : "Expected length " + data.length;
        for (int i = 0; i < data.length; i++) {
            assert result[i] == data[i] : "Mismatch at index " + i;
        }
    }
}
