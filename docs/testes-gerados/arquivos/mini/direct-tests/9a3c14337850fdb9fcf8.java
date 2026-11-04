/*
 * @test
 * @summary Test sun.security.util.DerOutputStream.putBoolean(boolean) method
 * @run main sun.security.util.DerOutputStreamWitupTest
 */

import sun.security.util.DerOutputStream;
import java.io.IOException;
import java.util.Arrays;

public class DerOutputStreamWitupTest {
    public static void main(String[] args) throws IOException {
        testPutBooleanTrue();
        testPutBooleanFalse();
    }

    private static void testPutBooleanTrue() throws IOException {
        DerOutputStream dos = new DerOutputStream();
        dos.putBoolean(true);
        byte[] result = dos.toByteArray();
        // Expected encoding: tag_Boolean (0x01), length 1, value 0xff
        byte[] expected = new byte[] { 0x01, 0x01, (byte)0xff };
        if (!Arrays.equals(result, expected)) {
            throw new AssertionError("putBoolean(true) produced incorrect encoding: " + Arrays.toString(result));
        }
    }

    private static void testPutBooleanFalse() throws IOException {
        DerOutputStream dos = new DerOutputStream();
        dos.putBoolean(false);
        byte[] result = dos.toByteArray();
        // Expected encoding: tag_Boolean (0x01), length 1, value 0x00
        byte[] expected = new byte[] { 0x01, 0x01, 0x00 };
        if (!Arrays.equals(result, expected)) {
            throw new AssertionError("putBoolean(false) produced incorrect encoding: " + Arrays.toString(result));
        }
    }
}
