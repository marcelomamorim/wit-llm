/* @test    @summary Test readNBytes with normal and edge cases    @run main InputStreamWitupTest_Pcfdd0bb12f59 */

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

public class InputStreamWitupTest_Pcfdd0bb12f59 {

    public static void main(String[] args) throws IOException {
        testReadNBytesNormal();
        testReadNBytesZero();
        testReadNBytesNegative();
        testReadNBytesEOF();
        testReadNBytesLarge();
    }

    static void testReadNBytesNormal() throws IOException {
        byte[] data = {1, 2, 3, 4, 5};
        InputStream is = new ByteArrayInputStream(data);
        byte[] result = is.readNBytes(3);
        if (result.length != 3 || result[0] != 1 || result[1] != 2 || result[2] != 3) {
            throw new AssertionError("Failed: testReadNBytesNormal");
        }
        is.close();
    }

    static void testReadNBytesZero() throws IOException {
        byte[] data = {1, 2, 3};
        InputStream is = new ByteArrayInputStream(data);
        byte[] result = is.readNBytes(0);
        if (result.length != 0) {
            throw new AssertionError("Failed: testReadNBytesZero");
        }
        is.close();
    }

    static void testReadNBytesNegative() {
        byte[] data = {1, 2, 3};
        InputStream is = new ByteArrayInputStream(data);
        try {
            is.readNBytes(-1);
            throw new AssertionError("Failed: testReadNBytesNegative did not throw");
        } catch (IllegalArgumentException e) {
            // expected
        } catch (IOException e) {
            throw new AssertionError("Failed: testReadNBytesNegative with wrong exception");
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                // ignore
            }
        }
    }

    static void testReadNBytesEOF() throws IOException {
        byte[] data = {1, 2};
        InputStream is = new ByteArrayInputStream(data);
        byte[] result = is.readNBytes(10);
        if (result.length != 2 || result[0] != 1 || result[1] != 2) {
            throw new AssertionError("Failed: testReadNBytesEOF");
        }
        is.close();
    }

    static void testReadNBytesLarge() throws IOException {
        byte[] data = new byte[5000];
        for (int i = 0; i < data.length; i++) {
            data[i] = (byte) (i % 256);
        }
        InputStream is = new ByteArrayInputStream(data);
        byte[] result = is.readNBytes(5000);
        if (result.length != 5000) {
            throw new AssertionError("Failed: testReadNBytesLarge");
        }
        for (int i = 0; i < 5000; i++) {
            if (result[i] != (byte) (i % 256)) {
                throw new AssertionError("Failed: testReadNBytesLarge at index " + i);
            }
        }
        is.close();
    }
}
