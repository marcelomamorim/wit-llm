/*
 * @test
 * @summary Tests the readNBytes method of InputStream for correct behavior.
 * @run main InputStreamWitupTest_Pe352b77728d8
 */

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

public class InputStreamWitupTest_Pe352b77728d8 {

    public static void main(String[] args) throws IOException {
        testReadNBytesExact();
        testReadNBytesZero();
        testReadNBytesNegative();
        testReadNBytesMoreThanAvailable();
    }

    static void testReadNBytesExact() throws IOException {
        byte[] data = {1, 2, 3, 4, 5};
        InputStream is = new ByteArrayInputStream(data);
        byte[] result = is.readNBytes(5);
        assert result.length == 5 : "Expected length 5";
        for (int i = 0; i < 5; i++) {
            assert result[i] == data[i] : "Mismatch at index " + i;
        }
    }

    static void testReadNBytesZero() throws IOException {
        byte[] data = {10, 20, 30};
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
            // expected
        } catch (IOException e) {
            assert false : "Unexpected IOException";
        }
    }

    static void testReadNBytesMoreThanAvailable() throws IOException {
        byte[] data = {5, 6};
        InputStream is = new ByteArrayInputStream(data);
        byte[] result = is.readNBytes(10);
        assert result.length == 2 : "Expected length 2";
        assert result[0] == 5 && result[1] == 6 : "Data mismatch";
    }
}
