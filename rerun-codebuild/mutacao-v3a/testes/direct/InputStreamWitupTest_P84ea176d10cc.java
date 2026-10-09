/*
 * @test
 * @summary Test readNBytes method of InputStream for correct behavior.
 * @run main InputStreamWitupTest_P84ea176d10cc
 */

import java.io.ByteArrayInputStream;
import java.io.IOException;

public class InputStreamWitupTest_P84ea176d10cc {
    public static void main(String[] args) throws IOException {
        testReadNBytesExact();
        testReadNBytesZero();
        testReadNBytesNegative();
        testReadNBytesEOF();
        testReadNBytesPartial();
    }

    static void testReadNBytesExact() throws IOException {
        byte[] data = {1, 2, 3, 4, 5};
        ByteArrayInputStream bais = new ByteArrayInputStream(data);
        byte[] result = bais.readNBytes(5);
        assert result.length == 5 : "Expected length 5";
        for (int i = 0; i < 5; i++) {
            assert result[i] == data[i] : "Mismatch at index " + i;
        }
    }

    static void testReadNBytesZero() throws IOException {
        byte[] data = {1, 2, 3};
        ByteArrayInputStream bais = new ByteArrayInputStream(data);
        byte[] result = bais.readNBytes(0);
        assert result.length == 0 : "Expected length 0";
    }

    static void testReadNBytesNegative() {
        byte[] data = {1, 2, 3};
        ByteArrayInputStream bais = new ByteArrayInputStream(data);
        try {
            bais.readNBytes(-1);
            assert false : "Expected IllegalArgumentException";
        } catch (IllegalArgumentException e) {
            // expected
        } catch (IOException e) {
            assert false : "Did not expect IOException";
        }
    }

    static void testReadNBytesEOF() throws IOException {
        byte[] data = {10, 20};
        ByteArrayInputStream bais = new ByteArrayInputStream(data);
        byte[] result = bais.readNBytes(10);
        assert result.length == 2 : "Expected length 2";
        assert result[0] == 10 && result[1] == 20 : "Data mismatch";
    }

    static void testReadNBytesPartial() throws IOException {
        byte[] data = {5, 6, 7};
        ByteArrayInputStream bais = new ByteArrayInputStream(data);
        byte[] result = bais.readNBytes(2);
        assert result.length == 2 : "Expected length 2";
        assert result[0] == 5 && result[1] == 6 : "Data mismatch";
    }
}
